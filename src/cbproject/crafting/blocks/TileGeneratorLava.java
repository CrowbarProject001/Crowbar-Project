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
package cbproject.crafting.blocks;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import cbproject.api.energy.item.IEnItem;

/**
 * @author WeAthFolD
 *
 */
public class TileGeneratorLava extends TileGeneratorBase implements IInventory{

	public ItemStack[] slots = new ItemStack[2];
	public int currentEnergy = 0;
	
	/**
	 * @param tier
	 * @param store
	 */
	public TileGeneratorLava() {
		super(1, 400000);
	}
	

	@Override
	public void updateEntity() {
		super.updateEntity();
		tryBurn();
		if(currentEnergy > 0) {
			int toSend = (currentEnergy > 10 ? 10 : currentEnergy);
			int left = this.sendEnergy(toSend);
			currentEnergy -= (toSend - left);
		}
	}
	
	private void tryBurn() {
		int energyReq = maxStorage - currentEnergy;
		
		if(energyReq > 20000 && slots[0] != null) {
			if(slots[0].itemID == Item.bucketLava.itemID) {
				slots[0] = new ItemStack(Item.bucketEmpty);
				currentEnergy += 20000;
			}
		}
	}
	
	@Override
	public int getMaxEnergyOutput() {
		return 5;
	}


	@Override
	public int getSizeInventory() {
		return 2;
	}


	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}


	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(--slots[i].stackSize <= 0)
			return null;
		return slots[i];
	}


	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return slots[i];
	}


	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i] = itemstack;
	}


	@Override
	public String getInvName() {
		return "cbc.tile.genlava";
	}


	@Override
	public boolean isInvNameLocalized() {
		return false;
	}


	@Override
	public int getInventoryStackLimit() {
		return 2;
	}


	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if(i == 0)
			return true;
		else return(itemstack.getItem() instanceof IEnItem);
	}
}
