package cn.lambdacraft.api.energy.event;

import cn.lambdacraft.api.energy.tile.IEnergyTile;

public class EnergyTileLoadEvent extends EnergyTileEvent {
	public EnergyTileLoadEvent(IEnergyTile energyTile) {
		super(energyTile);
	}
}