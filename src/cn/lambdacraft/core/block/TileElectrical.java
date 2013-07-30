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
package cn.lambdacraft.core.block;

import java.util.Random;

import cn.lambdacraft.api.LCDirection;
import cn.lambdacraft.api.energy.event.EnergyTileLoadEvent;
import cn.lambdacraft.api.energy.event.EnergyTileSourceEvent;
import cn.lambdacraft.api.energy.event.EnergyTileUnLoadEvent;
import cn.lambdacraft.api.energy.tile.IEnergySource;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author WeAthFolD
 * 
 */
public abstract class TileElectrical extends CBCTileEntity implements
		IEnergySource {
	
	public static Random random = new Random();
	
	public boolean addedToNet = false;
	
	public int fuel = 0;
	
	public short storage = 0;
	public final short maxStorage;
	public int production;
	public int ticksSinceLastActiveUpdate;
	

	/**
	 * 
	 */
	public TileElectrical(int production,int maxStorage) {
		this.production = production;
		this.maxStorage = (short)maxStorage;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		
		if (this.storage > this.maxStorage) this.storage = this.maxStorage;
		
		if (this.storage > 0) {
			
			 int output = Math.min(this.production, this.storage);
			 if (output > 0) this.storage = (short)(this.storage + (sendEnergy(output) - output));
		}
		
		if (!this.addedToNet) {
			this.addedToNet = true;
			this.onElectricTileLoad();
		}
	}

	public void setActive(boolean newActive) {
		// TODO 自动生成的方法存根
		
	}

	public boolean delayActiveUpdate() {
		// TODO 自动生成的方法存根
		return false;
	}

	public boolean onElectricTileLoad() {
		return MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
	}

	@Override
	public void onTileUnload() {
		super.onTileUnload();
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnLoadEvent(this));
		this.addedToNet = false;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return addedToNet;
	}

	public boolean emitsEnergyTo(TileEntity paramTileEntity,
			LCDirection paramDirection)
	{
		return true;
	}

	public int getMaxEnergyOutput()
	{
		return this.production;
	}
	
	public int gaugeStorageScaled(int i){
		return this.storage * i / this.maxStorage;
	}
	
	public abstract int gaugeFuelScaled(int paramInt);
	
	public abstract boolean gainFuel();
	
	public boolean gainEnergy() {
		if (isConverting()) {
			this.storage = (short)(this.storage + this.production);
			this.fuel -= 1;
			return true;
		}
		return false;
	}
	
	public boolean isConverting() {
		// TODO 自动生成的方法存根
		return (this.fuel > 0) && (this.storage + this.production <= this.maxStorage);
	}

	public boolean needsFuel(){
		return (this.fuel <= 0) && (this.storage + this.production <= this.maxStorage);
	}

	public int sendEnergy(int amm) {
		int amount = 0;
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, amm);
		MinecraftForge.EVENT_BUS.post(event);
		amount += event.amount;
		return amount;
	}

}
