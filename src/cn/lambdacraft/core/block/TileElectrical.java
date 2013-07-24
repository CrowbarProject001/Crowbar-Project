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

import cn.lambdacraft.api.energy.EnergyTileLoadEvent;
import cn.lambdacraft.api.energy.EnergyTileSourceEvent;
import cn.lambdacraft.api.energy.EnergyTileUnLoadEvent;
import cn.lambdacraft.api.energy.IEnergyTile;
import cn.lambdacraft.core.CBCMod;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author WeAthFolD
 * 
 */
public abstract class TileElectrical extends CBCTileEntity implements
		IEnergyTile {

	public boolean addedToNet = false;

	/**
	 * 
	 */
	public TileElectrical() {

	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!this.addedToNet) {
			this.addedToNet = true;
			this.onElectricTileLoad();
		}
	}
	
	public boolean onElectricTileLoad() {
		return MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
	}

	@Override
	public void onTileUnload() {
		super.onTileUnload();
		MinecraftForge.EVENT_BUS
				.post(new EnergyTileUnLoadEvent(this));
		this.addedToNet = false;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return addedToNet;
	}

	public int sendEnergy(int amm) {
		int amount = 0;
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(this,
				amm);
		MinecraftForge.EVENT_BUS.post(event);
		amount += event.amount;
		return amount;
	}

}
