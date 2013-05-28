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
package cbproject.deathmatch.blocks.tileentities;

import java.util.HashSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cbproject.api.LCDirection;
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.api.energy.tile.IEnergySink;
import cbproject.core.block.TileElectric;
import cbproject.deathmatch.blocks.tileentities.TileEntityHealthCharger.EnumBehavior;

/**
 * @author WeAthFolD
 * 
 */
public class TileMedkitFiller extends TileElectric implements IInventory {

	public TileMedkitFiller() {
		super(1, 40000);
	}
	
	public static final int EFFECT_MAX = 240; // 1.25  batbox, 4-6 potions
	private int sideEffectId = 0;

	/**
	 * Slot 0:主药水槽。
	 * Slot 1:副药水槽。
	 * Slot 2:放电池槽。
	 */
	public ItemStack slots[] = new ItemStack[3];
	
	public enum EnumBehavior {
		NONE, RECEIVEONLY, EMIT;

		@Override
		public String toString() {
			switch (this) {
			case NONE:
				return "rs.donothing.name";
			case RECEIVEONLY:
				return "rs.reciveonly.name";
			case EMIT:
				return "rs.emit.name";
			default:
				return "rs.donothing.name";
			}
		}
	}



	@Override
	public void updateEntity() {
		int energyReq = maxEnergy - currentEnergy;

		if(worldObj.isRemote)
			return;
		
		if(currentEnergy < 0)
			currentEnergy = 0;
	}

	@Override
	public int getSizeInventory() {
		return 3;
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
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return "medfiller";
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	/**
	 * TODO: NEEDS REWRITE.
	 */
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		for (int i = 0; i < 3; i++) {
			short id = nbt.getShort("id" + i), damage = nbt.getShort("damage"
					+ i);
			byte count = nbt.getByte("count" + i);
			if (id == 0)
				continue;
			ItemStack is = new ItemStack(id, count, damage);
			slots[i] = is;
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for (int i = 0; i < 3; i++) {
			if (slots[i] == null)
				continue;
			nbt.setShort("id" + i, (short) slots[i].itemID);
			nbt.setByte("count" + i, (byte) slots[i].stackSize);
			nbt.setShort("damage" + i, (short) slots[i].getItemDamage());
		}
		nbt.setInteger("energy", currentEnergy);
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			LCDirection paramDirection) {
		return true;
	}

	@Override
	public int getMaxSafeInput() {
		return 32;
	}

}
