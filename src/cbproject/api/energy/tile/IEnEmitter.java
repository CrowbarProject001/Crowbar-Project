package cbproject.api.energy.tile;

import net.minecraft.tileentity.TileEntity;
import cbproject.api.LCDirection;

public abstract interface IEnEmitter extends IEnergyTile{
	public abstract boolean emitterEnergyTo(TileEntity emTileEntity,LCDirection emDirection);

}
