package cn.lambdacraft.api.energy.event;

import cn.lambdacraft.api.energy.tile.IEnergyTile;

public class EnergyTileSourceEvent extends EnergyTileEvent{
	
	public int amount;
	

	public EnergyTileSourceEvent (IEnergyTile energyTile,int amount){
		super(energyTile);
		this.amount = amount;
	}
}