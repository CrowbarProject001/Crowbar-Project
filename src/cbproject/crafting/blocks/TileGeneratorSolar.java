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
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.api.energy.item.IEnItem;
 
/**
 * @author WeAthFolD
 *
 */
public class TileGeneratorSolar extends TileGeneratorBase implements IInventory{

	public ItemStack[] slots = new ItemStack[1];
	public int currentEnergy = 0;
	public boolean isEmitting;
	
	/**
	 * @param tier
	 * @param store
	 */
	public TileGeneratorSolar() {
		super(1, 10000);
	}
	

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(worldObj.isRemote)
			return;
		
		if(this.worldObj.isDaytime() && worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
				currentEnergy += 4;
				int amt = currentEnergy > 16 ? 16 : currentEnergy;
				amt -= this.sendEnergy(amt);
				currentEnergy -= amt;
				if(currentEnergy > maxStorage)
					currentEnergy = maxStorage;
				isEmitting = true;
		}
		isEmitting = false;
		
		if(this.slots[0] != null && slots[0].getItem() instanceof ICustomEnItem) {
			currentEnergy -= ((ICustomEnItem)slots[0].getItem()).charge(slots[0], currentEnergy, 1, false, false);
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
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
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
		return "cbc.tile.genfire";
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
