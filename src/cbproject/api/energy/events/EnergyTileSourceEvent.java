package cbproject.api.energy.events;

import net.minecraft.world.World;
import cbproject.api.energy.tile.IEnergyTile;

public class EnergyTileSourceEvent extends EnergyTileEvent{
	public int amount;
	public static World world;
	
	public EnergyTileSourceEvent (IEnergyTile energyTile,int amount){
		super(world, energyTile);
		this.amount = amount;
	}
	public EnergyTileSourceEvent (World world , IEnergyTile energyTile,int amount){
		super(world, energyTile);
		this.amount = amount;
	}

}