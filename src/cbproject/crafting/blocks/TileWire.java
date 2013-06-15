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
package cbproject.crafting.blocks;

import ic2.api.Direction;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cbproject.api.LCDirection;
import cbproject.api.energy.events.EnergyTileLoadEvent;
import cbproject.api.energy.tile.IEnConductor;
import cbproject.api.energy.tile.IEnergyTile;
import cbproject.core.block.TileElectrical;

/**
 * @author WeAthFolD
 *
 */
public class TileWire extends TileElectrical implements IEnConductor {

	public boolean[] renderSides = new boolean[6];
	
	public TileWire() {}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
	}
	
	@Override
	public void frequentUpdate() {
		updateSides();
	}

	public void updateSides() {
		if(!worldObj.isRemote)
			return;
		ForgeDirection[] dirs = ForgeDirection.values();
		for(int i = 0; i < 6; i++) {
			TileEntity ent = worldObj.getBlockTileEntity(xCoord + dirs[i].offsetX, yCoord + dirs[i].offsetY, zCoord + dirs[i].offsetZ);
			if(ent != null && ent instanceof IEnergyTile) {
				renderSides[i] = true;
			} else renderSides[i] = false;
		}
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, LCDirection direction) {
		return true;
	}

	@Override
	public double getConductionLoss() {
		return 0.1;
	}

	@Override
	public int getInsulationEnergyAbsorption() {
		return 128;
	}

	@Override
	public int getInsulationBreakdownEnergy() {
		return 128;
	}

	@Override
	public int getConductorBreakdownEnergy() {
		return 256;
	}

	@Override
	public void removeInsulation() {
	}

	@Override
	public void removeConductor() {
		this.onTileUnload();
	}

	@Override
	public boolean emitEnergyTo(TileEntity emTileEntity, LCDirection emDirection) {
		return true;
	}

	
	@Override
	public int demandsEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getMaxSafeInput() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}
}
