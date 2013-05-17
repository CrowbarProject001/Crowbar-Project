package cbproject.deathmatch.blocks.tileentities;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;
import cbproject.deathmatch.register.DMBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityTripmine extends TileEntity {
	
	public int endX, endY, endZ;
	
	public TileEntityTripmine() {
	}
	
	public void setEndCoords(int x, int y, int z){
		endX = x;
		endY = y;
		endZ = z;
	}

	@Override
	public void updateEntity() {
		
		BlockTripmine blockType = DMBlocks.blockTripmine;
		MotionXYZ begin = new MotionXYZ(xCoord, yCoord, zCoord, 0, 0, 0);
		int meta = this.blockMetadata;
		MotionXYZ end = new MotionXYZ(endX, endY, endZ, 0, 0, 0);
		double minX, minY, minZ, maxX, maxY, maxZ;
		if(end.posX > begin.posX){
			minX = begin.posX;
			maxX = end.posX;
		} else {
			minX = end.posX;
			maxX = begin.posX;
		}
		if(end.posY > begin.posY){
			minY = begin.posY;
			maxY = end.posY;
		} else {
			minY = end.posY;
			maxY = begin.posY;
		}
		if(end.posZ > begin.posZ){
			minZ = begin.posZ;
			maxZ = end.posZ;
		} else {
			minZ = end.posZ;
			maxZ = begin.posZ;
		}
		
		float RAY_RAD = BlockTripmine.RAY_RAD;
		minY = minY + 0.5 - RAY_RAD;
		maxY = maxY + 0.5 + RAY_RAD;
		if(meta == 3 || meta == 1){ //X
			minZ = minZ + 0.5 - RAY_RAD;
			maxZ = maxZ + 0.5 + RAY_RAD;
		} else {
			minX = minX + 0.5 - RAY_RAD;
			maxX = maxX + 0.5 + RAY_RAD;
		}
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, box);
		if(list != null && list.size() != 0){
			blockType.breakBlock(worldObj, xCoord, yCoord, zCoord, meta, 0);
		}
		if(worldObj.getWorldTime() % 5 == 0)
			blockType.updateRayRange(worldObj, xCoord, yCoord, zCoord);
	}
	
	public double getRayDistance(){
		double dx = xCoord - endX, dz = zCoord - endZ;
		return Math.sqrt(dx * dx + dz * dz);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public double getMaxRenderDistanceSquared()
    {
        return 4096.0D;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }
}
