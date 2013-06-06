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
package cbproject.crafting.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import cbproject.core.gui.CBCGuiButton;
import cbproject.core.gui.CBCGuiContainer;
import cbproject.core.gui.CBCGuiPart;
import cbproject.core.gui.IGuiTip;
import cbproject.core.props.ClientProps;
import cbproject.crafting.blocks.TileElCrafter;
import cbproject.crafting.blocks.TileWeaponCrafter;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.network.NetCrafterClient;
import cbproject.crafting.recipes.RecipeWeapons;

/**
 * @author WeAthFolD
 *
 */
public class GuiElectricCrafter extends CBCGuiContainer {

	public TileElCrafter tileEntity;
	
	public GuiElectricCrafter(InventoryPlayer inventoryPlayer,
			TileElCrafter tile) {
		super(new ContainerElCrafter(inventoryPlayer, tile));
		this.tileEntity = tile;
		xSize = 173;
		ySize = 192;
	}
	
	protected class TipEnergy implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED + "Current Energy";
		}

		@Override
		public String getTip() {
			return tileEntity.currentEnergy + "/" + tileEntity.maxHeat + " EU";
		}
		
	}
	
	public class TipHeat implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED + "Current Heat";
		}

		@Override
		public String getTip() {
			return tileEntity.heat + "/" + tileEntity.maxHeat + " Heat";
		}
		
	}
	
	@Override
    public void initGui()
    {
        super.initGui();
        CBCGuiPart up = new CBCGuiButton("up", 85, 16, 4, 3),
        		down = new CBCGuiButton("down", 85, 61, 4, 3),
        		left = new CBCGuiButton("left", 6, 6, 3, 4),
        		right = new CBCGuiButton("right", 158, 6, 3, 4),
        		heat = new CBCGuiPart("heat", 138, 17, 6, 46),
        		energy = new CBCGuiPart("energy", 116, 17, 6, 46);
        addElements(up, down, left, right, heat, energy);
        this.setElementTip("heat", new TipHeat());
        this.setElementTip("energy", new TipEnergy());
    }

	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		super.drawGuiContainerForegroundLayer(par1, par2);
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	String currentPage = StatCollector.translateToLocal(RecipeWeapons.getDescription(tileEntity.page));
        fontRenderer.drawString(currentPage, 85 - fontRenderer.getStringWidth(currentPage) / 2, 3, 0xff9843);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ClientProps.GUI_ELCRAFTER_PATH);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        int height = tileEntity.heat * 46 / tileEntity.maxHeat;
        if(height > 0){
        	drawTexturedModalRect(x + 138, y + 63 - height, 181, 0, 6, height);
        }
        height = tileEntity.currentEnergy * 46 / tileEntity.MAX_STORAGE;
        if(height > 0) {
        	drawTexturedModalRect(x + 116, y + 63 - height, 174, 0, 6, height);
        }
        if(tileEntity.isCrafting){
        	if(tileEntity.currentRecipe != null){
        		height = tileEntity.currentRecipe.heatRequired * 46 / tileEntity.maxHeat;
        		drawTexturedModalRect(x + 136, y + 63 - height, 207, 1, 6, 3);
        	}
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
	}
	
	@Override
	public void onButtonClicked(CBCGuiButton button) {
		System.out.println("clicked " + button.name);
		if(button.name == "up" || button.name =="down"){
			boolean isDown = button.name == "down" ? true: false;
			tileEntity.addScrollFactor(isDown);
			NetCrafterClient.sendCrafterPacket(tileEntity, 0, isDown);
			return;
		}
		if(button.name == "left" || button.name == "right"){
			boolean isForward = button.name == "right" ? true: false;
			tileEntity.addPage(isForward);
			NetCrafterClient.sendCrafterPacket(tileEntity, 1, isForward);
			return;
		}
	}
	
}
