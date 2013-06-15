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
package cbproject.core.block;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import cbproject.api.LCDirection;
import cbproject.api.energy.tile.IEnergySink;

/**
 * @author WeAthFolD
 *
 */
public abstract class TileElectricStorage extends TileElectrical implements IEnergySink,ic2.api.energy.tile.IEnergySink{

	public int maxEnergy;
	public int currentEnergy;
	protected int tier;
	protected int lastTick;
	
	/**
	 * 
	 */
	public TileElectricStorage(int tier, int max) {
		this.setMaxEnergy(max);
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.currentEnergy = nbt.getInteger("energy");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("energy", getCurrentEnergy());
	}
	
	@Override
	public int demandsEnergy() {
		return getMaxEnergy() - getCurrentEnergy();
	}

	/**
	 * @return the currentEnergy
	 */
	public int getCurrentEnergy() {
		return currentEnergy;
	}

	/**
	 * @return the maxEnergy
	 */
	public int getMaxEnergy() {
		return maxEnergy;
	}

	/**
	 * @param maxEnergy the maxEnergy to set
	 */
	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
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
	
	
	//IC2
	@Override
	public int injectEnergy(ic2.api.Direction directionFrom, int amount) {
        //if (amount > this.maxStorage)
        //{
        	//this.worldObj.createExplosion(null, this.xCoord,  this.yCoord,  this.zCoord, 2F, true);
        	//invalidate();
            //return 0;
        //}
        //else
        {
            this.currentEnergy += amount;
            int var3 = 0;
            if (this.currentEnergy > this.maxEnergy)
            {
                var3 = this.currentEnergy - this.maxEnergy;
                this.currentEnergy = this.maxEnergy;
            }
            return var3;
        }
	}
}
