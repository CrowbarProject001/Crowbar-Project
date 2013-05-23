package cbproject.api.energy.events;

import net.minecraft.world.World;
import cbproject.api.energy.tile.IEnergyTile;

public class EnergyTileSourceEvent extends EnergyTileEvent{
	public int amout;
	public EnergyTileSourceEvent (World world , IEnergyTile energyTile,int amout){
		super(world, energyTile);
		this.amout = amout;
	}

}