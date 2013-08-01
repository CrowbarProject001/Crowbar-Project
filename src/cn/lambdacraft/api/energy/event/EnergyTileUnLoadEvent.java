package cn.lambdacraft.api.energy.event;

import cn.lambdacraft.api.energy.tile.IEnergyTile;

public class EnergyTileUnLoadEvent extends EnergyTileEvent{
	public EnergyTileUnLoadEvent(IEnergyTile energyTile){
		super(energyTile);
	}
}