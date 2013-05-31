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
	public int tickSinceLastUpdate = 0;
	
	public TileWire() {
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(++tickSinceLastUpdate > 5) {
			tickSinceLastUpdate = 0;
			updateSides();
		}
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

}
