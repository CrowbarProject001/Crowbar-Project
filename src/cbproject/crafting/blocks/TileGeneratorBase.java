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
package cbproject.crafting.blocks;

import net.minecraft.entity.player.EntityPlayer;
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
public abstract class TileGeneratorBase extends TileElectrical implements IEnergySource {

	public final int maxStorage, tier;
	
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
	
	public int sendEnergy(int amm) {
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(worldObj, this, amm);
		MinecraftForge.EVENT_BUS.post(event);
		return event.amount;
	}

	@Override
	public boolean emitEnergyTo(TileEntity emTileEntity,
			LCDirection emDirection) {
		return true;
	}
	
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

}