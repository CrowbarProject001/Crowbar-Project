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
package cbproject.core.block;

import net.minecraftforge.common.MinecraftForge;
import cbproject.api.energy.events.EnergyTileLoadEvent;
import cbproject.api.energy.events.EnergyTileSourceEvent;
import cbproject.api.energy.events.EnergyTileUnloadEvent;
import cbproject.api.energy.tile.IEnergyTile;

/**
 * 通用电力TileEntity。
 *
 */
public abstract class TileElectrical extends CBCTileEntity implements IEnergyTile {

	public boolean addedToNet = false;

	public TileElectrical() {
		
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!this.addedToNet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(worldObj, this));
			this.addedToNet = true;
		}
	}

	@Override
	public void onTileUnload() {
		super.onTileUnload();
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(worldObj, this));
		this.addedToNet = false;
	}
	
	@Override
	public boolean isAddToEnergyNet() {
		return addedToNet;
	}
	
	public int sendEnergy(int amm) {
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(worldObj, this, amm);
		MinecraftForge.EVENT_BUS.post(event);
		return event.amount;
	}

}
