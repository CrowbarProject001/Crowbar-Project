package cbproject.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * 自定义的方向Enum。
 * @author WeAthFolD
 *
 */
public enum LCDirection {
	
	XN(0),
	
	XP(1),
	
	YN(2),
	
	YP(3),
	
	ZN(4),
	
	ZP(5);

	private int dir;
	
	private static final LCDirection[] lcdirections;
	
	static{
		lcdirections = values();
	}

	private LCDirection(int dir) {
		this.dir = dir;
	}

	public TileEntity applyToTileEntity(TileEntity tileEntity) {
		int[] coords = { tileEntity.xCoord, tileEntity.yCoord,
				tileEntity.zCoord };

		coords[(this.dir / 2)] += getSign();

		if ((tileEntity.worldObj != null)
				&& (tileEntity.worldObj.blockExists(coords[0], coords[1],
						coords[2]))) {
			return tileEntity.worldObj.getBlockTileEntity(coords[0], coords[1],
					coords[2]);
		}
		return null;
	}

	public LCDirection getInverse() {
		int inverseDir = this.dir - getSign();

		for (LCDirection lcdirection : lcdirections) {
			if (lcdirection.dir == inverseDir)
				return lcdirection;
		}
		return this;
	}
	
	private int toSideValue(){
		
		return (this.dir + 4) % 6;
	}
	
	private int getSign() {
		
		return this.dir % 2 *2 - 1;
		
	}
	
	public ForgeDirection toForgeDirection(){
		return ForgeDirection.getOrientation(toSideValue());
	}

}
