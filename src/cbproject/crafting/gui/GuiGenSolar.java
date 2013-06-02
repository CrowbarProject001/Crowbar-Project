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

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cbproject.core.gui.CBCGuiButton;
import cbproject.core.gui.CBCGuiContainer;
import cbproject.core.gui.CBCGuiPart;
import cbproject.core.gui.IGuiTip;
import cbproject.core.props.ClientProps;
import cbproject.crafting.blocks.TileGeneratorFire;
import cbproject.crafting.blocks.TileGeneratorSolar;
import cbproject.crafting.register.CBCBlocks;

/**
 * @author WeAthFolD
 *
 */
public class GuiGenSolar extends CBCGuiContainer{

	TileGeneratorSolar te;

	private class TipEnergy implements IGuiTip {

		@Override
		public String getHeadText() {
			return EnumChatFormatting.RED + "curenergy.name";
		}

		@Override
		public String getTip() {
			return te.currentEnergy + "/" + te.maxStorage + " EU";
		}
		
	}
	
	public GuiGenSolar(TileGeneratorSolar gen, InventoryPlayer inv) {
		super(new ContainerGeneratorSolar(gen, inv));
		te = gen;
		this.xSize = 173;
		this.ySize = 178;
	}
	
	@Override
    public void initGui()
    {
		super.initGui();
		CBCGuiPart energy = new CBCGuiPart("energy", 25, 52, 48, 7);
		this.addElement(energy);
		this.setElementTip("energy", new TipEnergy());
    }

	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		super.drawGuiContainerForegroundLayer(par1, par2);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	String guiName = StatCollector.translateToLocal(CBCBlocks.genFire.getUnlocalizedName());
    	this.fontRenderer.drawString(guiName, 7, 7, 0xdadada);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ClientProps.GUI_GENSOLAR_PATH);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        int len = 0;
        len = te.currentEnergy * 48 / te.maxStorage;
        if(len > 0)
        	this.drawTexturedModalRect(x + 25, y + 52, 173, 75, len, 7);
        if(te.worldObj.isDaytime()) {
        	this.drawTexturedModalRect(x + 14, y + 19, 173, 0, 60, 30);
        	this.drawTexturedModalRect(x + 87, y + 44, 178, 68, 5, 5);
        } else {
        	this.drawTexturedModalRect(x + 14, y + 19, 173, 34, 60, 30);
        	this.drawTexturedModalRect(x + 87, y + 44, 173, 68, 5, 5);
        }
        this.drawElements();
	}

	@Override
	public void onButtonClicked(CBCGuiButton button) {
	}

}
