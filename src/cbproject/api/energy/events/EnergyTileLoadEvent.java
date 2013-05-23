package cbproject.api.energy.events;

import net.minecraft.world.World;
import cbproject.api.energy.tile.IEnergyTile;

public class EnergyTileLoadEvent extends EnergyTileEvent{

	public EnergyTileLoadEvent (World world , IEnergyTile energyTile){
		super(world, energyTile);
	}

}