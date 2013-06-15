/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 鐗堟潈璁稿彲锛歀ambdaCraft 鍒朵綔灏忕粍锛�2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft鏄畬鍏ㄥ紑婧愮殑銆傚畠鐨勫彂甯冮伒浠庛�LambdaCraft寮�簮鍗忚銆嬨�浣犲厑璁搁槄璇伙紝淇敼浠ュ強璋冭瘯杩愯
 * 婧愪唬鐮侊紝 鐒惰�浣犱笉鍏佽灏嗘簮浠ｇ爜浠ュ彟澶栦换浣曠殑鏂瑰紡鍙戝竷锛岄櫎闈炰綘寰楀埌浜嗙増鏉冩墍鏈夎�鐨勮鍙�
 */
package cbproject.deathmatch.blocks;

import ic2.api.Direction;

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
import cbproject.core.block.TileElectricStorage;
import cbproject.core.utils.EnergyUtils;

/**
 * 鐩旂敳鍏呰兘鏈烘柟鍧椼� TODO:娣诲姞瀵瑰伐涓�鐨勬敮鎸併�
 * 
 * @author WeAthFolD
 * 
 */
public class TileArmorCharger extends TileElectricStorage implements
		IInventory {

	public static int ENERGY_MAX = 400000; // 10 BatBox, 4HEV Armor
	public boolean isCharging = false;
	public boolean isUsing = false;
	public HashSet<EntityPlayer> chargers = new HashSet();

	/**
	 * slot 0-3: HEV Armor slot, accept ICustomEnItem(LC) or
	 * ICustomElectricItem(IC2) only. slot 4-6: Battery slot.
	 */
	public ItemStack slots[] = new ItemStack[7];
	public EnumBehavior currentBehavior = EnumBehavior.NONE;
	public boolean isRSActivated;

	public enum EnumBehavior {
		NONE, CHARGEONLY, RECEIVEONLY, DISCHARGE, EMIT;

		@Override
		public String toString() {
			switch (this) {
			case NONE:
				return "rs.donothing.name";
			case CHARGEONLY:
				return "rs.chargeonly.name";
			case RECEIVEONLY:
				return "rs.reciveonly.name";
			case EMIT:
				return "rs.emit.name";
			case DISCHARGE:
				return "rs.discharge.name";
			default:
				return "rs.donothing.name";
			}
		}

	}

	public void nextBehavior() {
		int cur = currentBehavior.ordinal();
		currentBehavior = EnumBehavior.values()[cur == EnumBehavior.values().length - 1 ? 0
				: cur + 1];
	}

	public TileArmorCharger() {
		super(2, ENERGY_MAX);
	}

	public void startUsing(EntityPlayer player) {
		chargers.add(player);
		isUsing = true;
	}

	public void stopUsing(EntityPlayer player) {
		chargers.remove(player);
		if (chargers.size() == 0)
			isUsing = false;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj.isRemote)
			return;

		int energyReq = ENERGY_MAX - currentEnergy;
		// discharge
		if (this.isRSActivated && currentBehavior == EnumBehavior.DISCHARGE) {
			for (int i = 0; i < 4; i++) {
				ItemStack arm = slots[i];
				if (arm == null)
					continue;
				ICustomEnItem item = (ICustomEnItem) arm.getItem();
				int e = item.discharge(arm, ENERGY_MAX - currentEnergy, 2,
						false, false);
				currentEnergy += e;
			}
		} else // Charge the energy into armor
		if (currentEnergy > 0
				&& !(!this.isRSActivated && currentBehavior == EnumBehavior.CHARGEONLY)) {
			boolean flag = false;
			for (int i = 0; i < 4; i++) {
				ItemStack arm = slots[i];
				if (arm == null)
					continue;
				ICustomEnItem item = (ICustomEnItem) arm.getItem();
				int e = item.charge(arm, currentEnergy > 128 ? 128
						: currentEnergy, 2, false, worldObj.isRemote);
				currentEnergy -= e;
				flag = flag || e > 0;
			}
			isCharging = flag;
		} else
			isCharging = false;

		if (currentEnergy < 0)
			currentEnergy = 0;

		if (this.isUsing) {
			for (EntityPlayer charger : chargers) {
				int received = EnergyUtils.tryChargeArmor(charger,
						this.currentEnergy, 2, false);
				currentEnergy -= received;
				if (received <= 0) {
					worldObj.playSoundAtEntity(charger,
							"cbc.entities.suitchargeno", 0.5F, 1.0F);
					this.stopUsing(charger);
				}
				if (worldObj.getWorldTime() % 40 == 0) {
					worldObj.playSoundAtEntity(charger,
							"cbc.entities.suitcharge", 0.3F, 1.0F);
				}
				if (currentEnergy <= 0) {
					this.chargers.clear();
					this.isUsing = false;
					worldObj.playSoundAtEntity(charger,
							"cbc.entities.suitchargeno", 0.5F, 1.0F);
				}
			}

		}

		/**
		 * 
		 * Charge the energy into tileentity
		 */
		if (currentEnergy < ENERGY_MAX
				&& !(!this.isRSActivated && currentBehavior == EnumBehavior.RECEIVEONLY)) {
			for (int i = 4; i < 7; i++) {
				ItemStack sl = slots[i];
				if (sl == null)
					continue;
				if (sl.itemID == Item.redstone.itemID) {
					if (energyReq > 500) {
						this.decrStackSize(i, 1);
					}
					currentEnergy += 500;
				} else if (sl.getItem() instanceof ICustomEnItem) {
					ICustomEnItem item = (ICustomEnItem) sl.getItem();
					if (!item.canProvideEnergy(sl))
						continue;
					int cn = energyReq < 128 ? energyReq : 128;
					cn = item.discharge(sl, cn, 2, false, false);
					currentEnergy += cn;
				}
			}
		}

	}

	@Override
	public int getSizeInventory() {
		return 8;
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
		for (int i = 0; i < 7; i++) {
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
		for (int i = 0; i < 7; i++) {
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
		return !(currentBehavior == EnumBehavior.RECEIVEONLY && !this.isRSActivated);
	}

	@Override
	public int getMaxSafeInput() {
		return 128;
	}




	//IC2 Compatibility
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return !(currentBehavior == EnumBehavior.RECEIVEONLY && !this.isRSActivated);
	}
}
