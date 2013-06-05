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

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import cbproject.crafting.blocks.TileElCrafter;
import cbproject.crafting.blocks.TileWeaponCrafter;
import cbproject.crafting.recipes.RecipeWeapons;

/**
 * @author WeAthFolD
 *
 */
public class GuiElectricCrafter extends GuiWeaponCrafter{

	public TileElCrafter tileEntity;
	
	public GuiElectricCrafter(InventoryPlayer inventoryPlayer,
			TileElCrafter tile) {
		super(inventoryPlayer, tile);
		this.tileEntity = tile;
	}

	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	String storage = StatCollector.translateToLocal("crafter.storage");
    	String currentPage = StatCollector.translateToLocal(RecipeWeapons.getDescription(te.page));
        this.fontRenderer.drawString(storage, 8, 88, 4210752);
        fontRenderer.drawString(currentPage, 100 - fontRenderer.getStringWidth(currentPage) / 2, 1, 4210752);
    }
	
}
