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

import cbproject.api.energy.item.ICustomEnItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

/**
 * 盔甲充能机方块。
 * TODO:添加对工业2的支持。
 * @author WeAthFolD
 *
 */
public class TileEntityArmorCharger extends TileEntity implements IInventory {

	public static int ENERGY_MAX = 400000; //10 BatBox, 4HEV Armor
	public boolean isCharging = false;
	public int currentEnergy = 0;
	
	/**
	 * slot 0-3: HEV Armor slot, accept ICustomEnItem(LC) or ICustomElectricItem(IC2) only.
	 * slot 4-6: Battery slot. 
	 */
	public ItemStack slots[] = new ItemStack[7];
	public EnumChargerRSBehavior currentBehavior;
	
	enum EnumChargerRSBehavior {
		CHARGEONLY, DECHARGE, NONE
	}
	
	public TileEntityArmorCharger() {}
	
	@Override
	public void updateEntity() {
		//this.onInventoryChanged();
		int energyReq = ENERGY_MAX - currentEnergy;
		if(currentEnergy < ENERGY_MAX){
			for(int i = 4; i < 7; i++){
				ItemStack sl = slots[i];
				if(sl == null)
					continue;
				if(sl.itemID == Item.redstone.itemID){
					if(energyReq > 500){
						this.decrStackSize(i, 1);
					}
					currentEnergy += 500;
				} else if(sl.getItem() instanceof ICustomEnItem){
					ICustomEnItem item =  (ICustomEnItem)sl.getItem();
					if(!item.canProvideEnergy(sl))
						continue;
					int cn = energyReq < 128 ? energyReq : 128;
					cn = item.discharge(sl, cn, 2, false, false);
					currentEnergy += cn;
				}
			}
		}
		
		if(currentEnergy > 0){
			boolean flag = false;
			for(int i = 0; i < 4; i++){
				ItemStack arm = slots[i];
				if(arm == null)
					continue;
				ICustomEnItem item = (ICustomEnItem) arm.getItem();
				int e = item.charge(arm, currentEnergy > 128? 128 : currentEnergy, 2, false, false);
				currentEnergy -= e;
				flag = flag || e > 0;
			}
			isCharging = flag;
		} else isCharging = false;
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
	public void openChest() { }

	@Override
	public void closeChest() { }

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if(i <= 3 && !(itemstack.getItem() instanceof ICustomEnItem))
			return false;
		return true;
	}
	
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
    	super.readFromNBT(nbt);
    	for(int i = 0; i < 7; i++){
        	short id = nbt.getShort("id" + i), damage = nbt.getShort("damage" + i);
        	byte count = nbt.getByte("count" + i);
        	if(id == 0)
        		continue;
        	ItemStack is = new ItemStack(id, count, damage);
        	slots[i] = is;
        }
    	currentEnergy = nbt.getInteger("energy");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        for(int i = 0; i < 7; i++){
        	if(slots[i] == null)
        		continue;
        	nbt.setShort("id"+i, (short) slots[i].itemID);
        	nbt.setByte("count"+i, (byte) slots[i].stackSize);
        	nbt.setShort("damage"+i, (short)slots[i].getItemDamage());
        }
        nbt.setInteger("energy", currentEnergy);
    }

}
