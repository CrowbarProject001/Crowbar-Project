/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.client;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.items.ArmorHEV;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

/**
 * @author WeAthFolD
 *
 */
public class HEVRenderingUtils {

	
	@SideOnly(Side.CLIENT)
	public static void drawPlayerHud(EntityPlayer player, ScaledResolution resolution) {
		int k = resolution.getScaledWidth();
        int l = resolution.getScaledHeight();
        int i2 = k / 2 - 91;
        int k2 = l - 32 + 3;
        RenderEngine engine = Minecraft.getMinecraft().renderEngine;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.9F);
        engine.bindTexture(ClientProps.HEV_HUD_PATH);
        
        //Health Section
        int xOffset = -90, yOffset = -45;
        if(ClientProps.HUD_drawInLeftCorner) {
        	xOffset = -k / 2 + 13;
        	yOffset = -25;
        }
        GL11.glColor4f(0.7F, 0.7F, 0.7F, 0.9F);
        drawTexturedModalRect(k / 2 + xOffset, l + yOffset, 0, 16, 16, 16);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.9F);
        int h = player.getHealth() * 16 / 20;
        drawTexturedModalRect(k / 2 + xOffset, l + yOffset + 16 - h, 0, 32 - h, 16, h);
        if(player.getHealth() <= 5)
        	GL11.glColor4f(0.9F, 0.1F, 0.1F, 0.9F);
        drawNumberAt((byte) (player.getHealth() * 5), k / 2 + xOffset + 18, l + yOffset);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.9F);
        
        //Armor Section
        xOffset += 48;
        GL11.glColor4f(0.7F, 0.7F, 0.7F, 0.9F);
        drawTexturedModalRect(k / 2 + xOffset, l + yOffset, 16, 16, 16, 16);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.9F);
        h = player.getTotalArmorValue() * 16 / 20;
        if(h > 16)
        	h = 16;
        drawTexturedModalRect(k / 2 + xOffset, l + yOffset + 16 - h, 16, 32 - h, 16, h);
        
        drawNumberAt(player.getTotalArmorValue() * 5, k / 2 + xOffset + 18, l + yOffset);
        
        //Other section
        drawArmorTip(player, engine, k, l);
        drawWeaponTip(player, engine, k, l);
        
        engine.bindTexture("/gui/icons.png");
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.7F);
	}
	
	@SideOnly(Side.CLIENT)
	private static void drawArmorTip(EntityPlayer player,RenderEngine renderEngine, int k, int l) {
		for(int i = 0; i < 4 ; i ++) {
			ItemStack is = player.inventory.armorInventory[i];
			ArmorHEV hev;
			if(is != null && is.getItem() instanceof ArmorHEV) {
				hev = (ArmorHEV) is.getItem();
				int energy = hev.discharge(is, Integer.MAX_VALUE, 0, true, true);
				int heightToDraw = energy * 16 / 100000;
				int height = l - 50 - i * 16;
				if (is.getItemSpriteNumber() == 0) {
					renderEngine.bindTexture("/terrain.png");
				} else {
					renderEngine.bindTexture("/gui/items.png");
				}
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				drawTexturedModelRectFromIcon(5, height, hev.getIcon(is, 0), 16, 16);
				renderEngine.bindTexture(ClientProps.HEV_HUD_PATH);
				GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.9F);
				drawTexturedModalRect(24, height + 16 - heightToDraw, 32, 32 - heightToDraw, 16, heightToDraw);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void drawWeaponTip(EntityPlayer player,RenderEngine renderEngine, int k, int l) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item == null)
			return;
		if(item.getItem() instanceof IHudTipProvider) {
			IHudTip[] st = ((IHudTipProvider)item.getItem()).getHudTip(item, player);
			drawTips(st, renderEngine, item, player, k, l);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void drawTips(IHudTip[] tips, RenderEngine engine, ItemStack itemStack, EntityPlayer player, int k, int l) {
		int startHeight = l - 18 - 18 * tips.length;
		if(ClientProps.HUD_drawInLeftCorner)
			startHeight += 13;
		for(int i = 0; i < tips.length; i ++) {
			String s = tips[i].getTip(itemStack, player);
			int width = k - 32 - getStringLength(s);
			Icon icon = tips[i].getRenderingIcon(itemStack, player);
			if(icon != null) {
				int sheetIndex = tips[i].getTextureSheet(itemStack);
				if (sheetIndex == 0)
					engine.bindTexture("/terrain.png");
				else if(sheetIndex != 5)
					engine.bindTexture("/gui/items.png");
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				drawTexturedModelRectFromIcon(k - 30, startHeight, icon, 16, 16);
				GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.9F);
				engine.bindTexture(ClientProps.HEV_HUD_PATH);
			}
			drawTipStringAt(s, width, startHeight);
			startHeight += 18;
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static int getStringLength(String s) {
		int count = 0;
		for(char c : s.toCharArray()) {
			if(Character.isDigit(c))
				count += 9;
			else count += 3;
		}
		return count;
	}
	
	@SideOnly(Side.CLIENT)
	private static void drawNumberAt(int number, int x, int y) {
		String s = String.valueOf(number);
		drawTipStringAt(s, x, y);
	}
	
	@SideOnly(Side.CLIENT)
	private static void drawTipStringAt(String s, int x, int y) {
		int lastLength = 0;
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean b = Character.isDigit(c);
			if(b) {
				int number = Integer.valueOf(String.valueOf(c));
				drawSingleNumberAt(number, x + lastLength, y);
			} else {
				drawTexturedModalRect(x + lastLength, y, 48, 16, 3, 16);
			}
			lastLength += b? 9 : 3; 
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void drawSingleNumberAt(int number, int x, int y) {
		drawTexturedModalRect(x, y, 16 * number, 0, 16, 16);
	}
	
    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)-90, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)-90, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)-90, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)-90, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
        tessellator.draw();
    }
    
    public static void drawTexturedModelRectFromIcon(int par1, int par2, Icon par3Icon, int par4, int par5)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par5), (double)-90, (double)par3Icon.getMinU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + par5), (double)-90, (double)par3Icon.getMaxU(), (double)par3Icon.getMaxV());
        tessellator.addVertexWithUV((double)(par1 + par4), (double)(par2 + 0), (double)-90, (double)par3Icon.getMaxU(), (double)par3Icon.getMinV());
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)-90, (double)par3Icon.getMinU(), (double)par3Icon.getMinV());
        tessellator.draw();
    }
	
	public static Icon getHudSheetIcon(final int x, final int y, final String name) {
		return new Icon() {

			private int texU = x, texV = y;
			private String iconName = name;
			
			@Override
			@SideOnly(Side.CLIENT)
			public int getOriginX() {
				return texU;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public int getOriginY() {
				return texV;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public float getMinU() {
				return texU;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public float getMaxU() {
				return texU + 16;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public float getInterpolatedU(double d0) {
				return x + (float) d0;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public float getMinV() {
				return texV;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public float getMaxV() {
				return texV + 16;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public float getInterpolatedV(double d0) {
				return texV + (float) d0;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getIconName() {
				return name;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public int getSheetWidth() {
				return 16;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public int getSheetHeight() {
				return 16;
			}
			
		};
	}

}
