package cbproject.api.energy.events;

import net.minecraft.world.World;
import cbproject.api.energy.tile.IEnergyTile;
import net.minecraftforge.event.world.WorldEvent;

public class EnergyTileEvent extends WorldEvent{

	public final IEnergyTile energyTile;

	public EnergyTileEvent(World world, IEnergyTile energyTile) {
		super(world);
		this.energyTile = energyTile;
	}


}
