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
import cbproject.core.utils.EnergyUtils;
import cbproject.deathmatch.blocks.tileentities.TileEntityHealthCharger.EnumBehavior;
import cbproject.deathmatch.network.NetChargerServer;

/**
 * 盔甲充能机方块。 TODO:添加对工业2的支持。
 * 
 * @author WeAthFolD
 * 
 */
public class TileEntityHealthCharger extends TileEntity implements IInventory,
		IEnergySink {

	public static final int ENERGY_MAX = 50000, EFFECT_MAX = 240; // 1.25  batbox, 4-6 potions
	public boolean isCharging = false;
	public boolean isUsing = false;
	public HashSet<EntityPlayer> chargers = new HashSet();
	public int currentEnergy = 0;
	public int mainEff = 0, sideEff = 0;
	private int sideEffectId = 0;

	/**
	 * Slot 0:主药水槽。
	 * Slot 1:副药水槽。
	 * Slot 2:放电池槽。
	 */
	public ItemStack slots[] = new ItemStack[3];
	public int currentBehavior;
	public boolean isRSActivated;
	
	public void startUsing(EntityPlayer player) {
		chargers.add(player);
		isUsing = true;
	}
	
	public void stopUsing(EntityPlayer player) {
		chargers.remove(player);
		if(chargers.size() == 0)
			isUsing = false;
	}

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

	public void nextBehavior() {
		currentBehavior = currentBehavior == 4 ? 0 : currentBehavior + 1;
	}

	public TileEntityHealthCharger() {
	}

	@Override
	public void updateEntity() {
		int energyReq = ENERGY_MAX - currentEnergy;

		if(worldObj.isRemote)
			return;
		
		if(currentEnergy < 0)
			currentEnergy = 0;
		
		/**
		 * 
		 * Charge the energy into tileentity
		 */
		if (currentEnergy < ENERGY_MAX
				&& !(!this.isRSActivated && this.getCurrentBehavior() == EnumBehavior.RECEIVEONLY)) {
			ItemStack sl = slots[2];
			if (sl != null && sl.itemID == Item.redstone.itemID) {
				if (energyReq > 500) {
					this.decrStackSize(2, 1);
				}
				currentEnergy += 500;
			} else if (sl!= null && sl.getItem() instanceof ICustomEnItem) {
				ICustomEnItem item = (ICustomEnItem) sl.getItem();
				if (item.canProvideEnergy(sl)){
					int cn = energyReq < 128 ? energyReq : 128;
					cn = item.discharge(sl, cn, 2, false, false);
					currentEnergy += cn;
				}
			}
		}
		
		//处理F键使用行为
		if(this.isUsing) {
			
			for(EntityPlayer charger : chargers) {
				currentEnergy -= 5;//5EU/T per player
				this.doHealing(charger);
				if(worldObj.getWorldTime() % 40 == 0) {
					worldObj.playSoundAtEntity(charger, "cbc.entities.medcharge", 0.3F, 1.0F);
				}
				if(currentEnergy <= 0) {
					this.chargers.clear();
					this.isUsing = false;
					worldObj.playSoundAtEntity(charger, "cbc.entities.medshotno", 0.5F, 1.0F);
				}
			}
		}
	}
	
	/**
	 * TODO:补全
	 * @param charger
	 */
	public void doHealing(EntityPlayer charger) {
	
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
		return "armorcharger";
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
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if (i <= 3 && !(itemstack.getItem() instanceof ICustomEnItem))
			return false;
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
		currentEnergy = nbt.getInteger("energy");

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
		return !(getCurrentBehavior() == EnumBehavior.RECEIVEONLY && !this.isRSActivated);
	}

	@Override
	public int demandsEnergy() {
		return ENERGY_MAX - currentEnergy;
	}

	@Override
	public int injectEnergy(LCDirection paramDirection, int paramInt) {
		if (getCurrentBehavior() == EnumBehavior.RECEIVEONLY
				&& this.isRSActivated)
			return 0;
		int en = ENERGY_MAX - currentEnergy;
		if (paramInt < en) {
			currentEnergy += en;
			return paramInt;
		} else {
			currentEnergy = ENERGY_MAX;
			return en;
		}
	}

	public EnumBehavior getCurrentBehavior() {
		return getBehavior(currentBehavior);
	}

	private EnumBehavior getBehavior(int i) {
		switch (i) {
		case 0:
			return EnumBehavior.NONE;
		case 1:
			return EnumBehavior.RECEIVEONLY;
		case 2:
			return EnumBehavior.EMIT;
		}
		return EnumBehavior.NONE;
	}

	@Override
	public int getMaxSafeInput() {
		return 32;
	}

	@Override
	/**
	 * TODO:添加能源网络支持。
	 */
	public boolean isAddToEnergyNet() {
		return false;
	}

}