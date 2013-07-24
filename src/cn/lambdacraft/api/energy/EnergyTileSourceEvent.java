package cn.lambdacraft.api.energy;

public class EnergyTileSourceEvent extends EnergyTileEvent{
	
	public int amount;
	

	public EnergyTileSourceEvent (IEnergyTile energyTile,int amount){
		super(energyTile);
		this.amount = amount;
	}
}