package cbproject.api.energy.events;

import net.minecraft.world.World;
import cbproject.api.energy.tile.IEnergyTile;

public class EnergyTileLoadEvent extends EnergyTileEvent{
	public static World world;
	public EnergyTileLoadEvent (IEnergyTile energyTile){
		super(world, energyTile);
	}

	public EnergyTileLoadEvent (World world , IEnergyTile energyTile){
		super(world, energyTile);
	}

}