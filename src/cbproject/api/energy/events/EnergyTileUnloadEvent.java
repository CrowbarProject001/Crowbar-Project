package cbproject.api.energy.events;

import net.minecraft.world.World;
import cbproject.api.energy.tile.IEnergyTile;

public class EnergyTileUnloadEvent extends EnergyTileEvent{

	public EnergyTileUnloadEvent (World world , IEnergyTile energyTile){
		super(world, energyTile);
	}

}