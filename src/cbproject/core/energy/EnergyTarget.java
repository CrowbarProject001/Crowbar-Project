package cbproject.core.energy;

import net.minecraft.tileentity.TileEntity;
import cbproject.api.LCDirection;

public class EnergyTarget {
	TileEntity tileEntity;
	LCDirection direction;

	EnergyTarget(TileEntity tileEntity, LCDirection direction) {
		this.tileEntity = tileEntity;
		this.direction = direction;
	}
}
