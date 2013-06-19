package cn.lambdacraft.deathmatch.blocks;

import java.util.List;

import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.deathmatch.register.DMBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileTripmine extends TileEntity {

	public int endX, endY, endZ;

	public TileTripmine() {
	}

	public void setEndCoords(int x, int y, int z) {
		endX = x;
		endY = y;
		endZ = z;
	}

	@Override
	public void updateEntity() {

		BlockTripmine blockType = (BlockTripmine) DMBlocks.blockTripmine;
		MotionXYZ begin = new MotionXYZ(xCoord, yCoord, zCoord, 0, 0, 0);
		int meta = this.blockMetadata;
		MotionXYZ end = new MotionXYZ(endX, endY, endZ, 0, 0, 0);
		double minX, minY, minZ, maxX, maxY, maxZ;
		if (meta == 0)
			return;
		if (end.posX > begin.posX) {
			minX = begin.posX + 0.5;
			maxX = end.posX + 0.5;
		} else {
			minX = end.posX + 0.5;
			maxX = begin.posX + 0.5;
		}
		if (end.posY > begin.posY) {
			minY = begin.posY + 0.5;
			maxY = end.posY + 0.5;
		} else {
			minY = end.posY + 0.5;
			maxY = begin.posY + 0.5;
		}
		if (end.posZ > begin.posZ) {
			minZ = begin.posZ + 0.5;
			maxZ = end.posZ + 0.5;
		} else {
			minZ = end.posZ + 0.5;
			maxZ = begin.posZ + 0.5;
		}

		float RAY_RAD = BlockTripmine.RAY_RAD;
		minY = minY - RAY_RAD;
		maxY = maxY + RAY_RAD;
		if (meta == 5 || meta == 4) { // X
			minZ = minZ - RAY_RAD;
			maxZ = maxZ + RAY_RAD;
		} else {
			minX = minX - RAY_RAD;
			maxX = maxX + RAY_RAD;
		}
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minX, minY, minZ,
				maxX, maxY, maxZ);
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(null, box,
				GenericUtils.selectorLiving);
		if (list != null && list.size() > 0 && list.size() < 3) {
			blockType.breakBlock(worldObj, xCoord, yCoord, zCoord, meta, 0);
		}
		if (worldObj.getWorldTime() % 5 == 0)
			blockType.updateRayRange(worldObj, xCoord, yCoord, zCoord);
	}

	public double getRayDistance() {
		double dx = xCoord - endX, dz = zCoord - endZ;
		return Math.sqrt(dx * dx + dz * dz);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public double getMaxRenderDistanceSquared() {
		return 4096.0D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}
}
