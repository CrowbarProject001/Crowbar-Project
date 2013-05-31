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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cbproject.api.LCDirection;
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.core.block.TileElectricStorage;
import cbproject.deathmatch.items.ItemMedkit;
import cbproject.deathmatch.items.ItemMedkit.EnumAddingType;

/**
 * @author WeAthFolD
 * 
 */
public class TileMedkitFiller extends TileElectricStorage implements IInventory {

	public TileMedkitFiller() {
		super(1, 40000);
	}
	
	public static final int CRAFT_LIMIT = 120;
	public int progresses[] = new int[3];

	/**
	 * Slot 0, 1, 2:药水槽。
	 * Slot 3: Medkit槽
	 * Slot 4: 副材料槽。
	 * Slot 5： 电池槽。
	 */
	public ItemStack slots[] = new ItemStack[6];
	public EnumMedFillerBehavior currentBehavior = EnumMedFillerBehavior.NONE;
	
	public enum EnumMedFillerBehavior {
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if (i == 5 && itemstack.getItem() instanceof ICustomEnItem)
			return true;
		return false;
	}
	

	@Override
	public void updateEntity() {
		super.updateEntity();
		int energyReq = getMaxEnergy() - getCurrentEnergy();

		if(worldObj.isRemote)
			return;
		
		if(getCurrentEnergy() < 0)
			this.currentEnergy = 0;
		
		if(energyReq > 0 && slots[5] != null && slots[5].getItem() instanceof ICustomEnItem) {
			ICustomEnItem item = (ICustomEnItem) slots[5].getItem();
			this.currentEnergy += item.discharge(slots[5], energyReq, 2, false, false);
		}
		
		for(int i = 0; i < 3; i++) {
			if(isCrafting(i)) {
				if(currentEnergy > 3) {
					progresses[i] ++;
					currentEnergy -= 3;
				}
				if(progresses[i] >= getCraftingTimeLimit(i))
					this.addEffect(i);
			} else {
				progresses[i] = 0;
			}
		}
		
	}
	
	private int getCraftingTimeLimit(int slot){
		if(slots[slot] == null || !(slots[slot].getItem() instanceof ItemPotion))
			return -1;
		return TileMedkitFiller.CRAFT_LIMIT;
	}
	
	private void addEffect(int slot) {
		ItemMedkit.tryAddEffectTo(slots[3], slots[slot], EnumAddingType.NONE);
		slots[slot] = null;
		progresses[slot] = 0;
	}
	
	private boolean isCrafting(int slot){
		return slots[slot] != null && isMedkitAvailable();
	}
	
	private boolean isMedkitAvailable() {
		return slots[3] != null && !ItemMedkit.isMedkitFull(slots[3]);
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
		nbt.setInteger("energy", getCurrentEnergy());
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			LCDirection paramDirection) {
		return this.currentEnergy < maxEnergy;
	}

	@Override
	public int getMaxSafeInput() {
		return 32;
	}


	@Override
	public int injectEnergy(LCDirection paramDirection, int paramInt) {
		this.currentEnergy += paramInt;
		if(currentEnergy > maxEnergy) {
			currentEnergy = maxEnergy;
			return currentEnergy - maxEnergy;
		}
		return 0;
	}
}
