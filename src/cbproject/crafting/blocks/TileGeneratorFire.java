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

import cbproject.api.energy.item.ICustomEnItem;
import cbproject.api.energy.item.IEnItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * @author WeAthFolD
 *
 */
public class TileGeneratorFire extends TileGeneratorBase implements IInventory{

	public ItemStack[] slots = new ItemStack[2];
	public boolean isBurning = false;
	public int tickLeft = 0, maxBurnTime = 0;
	public int currentEnergy = 0;
	private int tickSinceNextUpdate = 0;
	
	/**
	 * @param tier
	 * @param store
	 */
	public TileGeneratorFire() {
		super(1, 5000);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(worldObj.isRemote)
			return;
		if(isBurning) {
			tickLeft--;
			currentEnergy += this.sendEnergy(5);
			System.out.println(currentEnergy);
			if(currentEnergy > maxStorage)
				currentEnergy = maxStorage;
			if(tickLeft <= 0)
				isBurning = false;
		} else {
			if(currentEnergy < maxStorage)
				tryBurn();
		}
		if(currentEnergy > 0){
			int all = currentEnergy > 5 ?  5 : currentEnergy;
			int rev = sendEnergy(all);
			currentEnergy -= (all - rev);
			if(slots[1] != null) {
				((ICustomEnItem)slots[1].getItem()).charge(slots[1], currentEnergy, 2, false, false);
			}
		}
	}
	
	private void tryBurn() {
		if(slots[0] != null) {
			this.tickLeft = TileEntityFurnace.getItemBurnTime(slots[0]) / 2;
    		this.maxBurnTime = this.tickLeft;
    		slots[0].splitStack(1);
    		if(slots[0].stackSize <= 1)
    			slots[0] = null;
    		isBurning = true;
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
		return 64;
	}


	@Override
	public void openChest() {}


	@Override
	public void closeChest() {}


	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

}
