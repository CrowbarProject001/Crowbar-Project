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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import cbproject.crafting.blocks.TileElCrafter;
import cbproject.crafting.blocks.TileWeaponCrafter;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;

/**
 * @author WeAthFolD
 *
 */
public class ContainerElCrafter extends ContainerWeaponCrafter {

	protected TileElCrafter tileEntity;
	
	/**
	 * @param inventoryPlayer
	 * @param te
	 */
	public ContainerElCrafter(InventoryPlayer inventoryPlayer,
			TileElCrafter te) {
		super(inventoryPlayer, te);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			icrafting.sendProgressBarUpdate(this, 0, tileEntity.scrollFactor);
			icrafting.sendProgressBarUpdate(this, 1, tileEntity.iconType.ordinal());
			icrafting.sendProgressBarUpdate(this, 2, tileEntity.currentEnergy * Short.MAX_VALUE / tileEntity.MAX_STORAGE);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			scrollFactor = Math.abs(par2);
			writeRecipeInfoToSlot();
		} else if(par1 == 1) {
			tileEntity.iconType = CrafterIconType.values()[par2];
		} else if(par1 == 2) {
			tileEntity.currentEnergy = (int) (par2 * ((long)tileEntity.MAX_STORAGE) / Short.MAX_VALUE);
		}
	}

}
