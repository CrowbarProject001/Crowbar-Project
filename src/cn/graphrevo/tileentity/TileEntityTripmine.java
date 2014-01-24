/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

/**
 * 阔剑地雷的TileEntity类。在这里执行位置判断和爆炸的功能。
 * @author WeAthFolD
 *
 */
public class TileEntityTripmine extends TileEntity {

	/**
	 * 
	 */
	public TileEntityTripmine() {
	}
	
	/**
	 * 每Tick时刻被调用的函数。进行爆炸判断。
	 */
	public void updateEntity() {
		if(!worldObj.isRemote && this.blockMetadata >= 0) {
			ForgeDirection dir = ForgeDirection.values()[this.blockMetadata];

			
			double centX = xCoord + 0.5 - dir.offsetX * 0.2,
			 centY = yCoord + 0.5 - dir.offsetY * 0.2,
			 centZ = zCoord + 0.5 - dir.offsetZ * 0.2;
			double targX = centX + dir.offsetX * 2.2,
					targY = centY + dir.offsetY * 2.2,
					targZ = centZ + dir.offsetZ * 2.2;
			double[] x = calculateBound(centX, targX), y = calculateBound(centY, targY), z = calculateBound(centZ, targZ);
			AxisAlignedBB bBox = AxisAlignedBB.getBoundingBox(x[0], y[0], z[0], x[1], y[1], z[1]);
			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, bBox);
			if(list != null) {
				for(Entity e : list) {
					if(e instanceof EntityLiving) {
						worldObj.setBlockToAir(xCoord, yCoord, zCoord);
						worldObj.createExplosion(e, centX, centY, centZ, 3.0F, true);
						break;
					}
				}
			}
		}
	}
	
	private double[] calculateBound(double a0, double a1) {
		double[] result = new double[2];
		if(a0 == a1) {
			result[0] = a0 - 0.5F;
			result[1] = a0 + 0.5F;
		} else {
			if(a0 > a1) {
				result[0] = a1;
				result[1] = a0;
			} else {
				result[0] = a0;
				result[1] = a1;
			}
		}
		return result;
	}

}
