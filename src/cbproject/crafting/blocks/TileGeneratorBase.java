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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import cbproject.api.LCDirection;
import cbproject.api.energy.events.EnergyTileLoadEvent;
import cbproject.api.energy.events.EnergyTileSourceEvent;
import cbproject.api.energy.events.EnergyTileUnloadEvent;
import cbproject.api.energy.tile.IEnergySource;
import cbproject.core.block.CBCTileEntity;
import cbproject.core.block.TileElectrical;

/**
 * @author WeAthFolD
 *
 */
public abstract class TileGeneratorBase extends TileElectrical implements IEnergySource,ic2.api.energy.tile.IEnergySource {

	public final int maxStorage, tier;
	public int currentEnergy;
	
	/**
	 * The generator production in this tick
	 */
	public int production;
	
	public TileGeneratorBase(int tier, int store) {
		this.maxStorage = store;
		this.tier = tier;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
	}

	@Override
	public boolean emitEnergyTo(TileEntity emTileEntity,
			LCDirection emDirection) {
		return true;
	}
	
	
    /**
     * Reads a tile entity from NBT.
     */
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.currentEnergy = nbt.getInteger("energy");
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("energy", currentEnergy);
    }
    
	//IC2 
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		// TODO Auto-generated method stub
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
