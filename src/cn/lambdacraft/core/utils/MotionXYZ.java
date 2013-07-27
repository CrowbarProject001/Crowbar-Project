package cn.lambdacraft.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MotionXYZ {

	private static final Random RNG = new Random();
	public double motionX, motionY, motionZ;
	public double posX, posY, posZ;
	public static final double SCALE = 0.5D;

	public MotionXYZ(double pX, double pY, double pZ, double moX, double moY,
			double moZ) {
		posX = pX;
		posY = pY;
		posZ = pZ;

		motionX = moX;
		motionY = moY;
		motionZ = moZ;
	}

	public MotionXYZ(Vec3 par0, double moX, double moY, double moZ) {
		posX = par0.xCoord;
		posY = par0.yCoord;
		posZ = par0.zCoord;

		motionX = moX;
		motionY = moY;
		motionZ = moZ;
	}

	public MotionXYZ(MotionXYZ a) {

		posX = a.posX;
		posY = a.posY;
		posZ = a.posZ;
		motionX = a.motionX;
		motionY = a.motionY;
		motionZ = a.motionZ;

	}

	public MotionXYZ(Entity ent) {
		this(ent, 0);
	}

	public MotionXYZ(Entity par1Player, int par2) {

		if (par1Player instanceof EntityLiving)
			getPosByPlayer((EntityLiving) par1Player);
		else {

			posX = par1Player.posX;
			posY = par1Player.posY;
			posZ = par1Player.posZ;
			motionX = par1Player.motionX;
			motionY = par1Player.motionY;
			motionZ = par1Player.motionZ;

		}

		setMotionOffset(par2);

	}

	public Vec3 asVec3(World world) {
		return world.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
	}

	public MotionXYZ setMotionOffset(double par1) {

		this.motionX += (RNG.nextDouble() - 1) * par1 * SCALE;
		this.motionY += (RNG.nextDouble() - 1) * par1 * SCALE;
		this.motionZ += (RNG.nextDouble() - 1) * par1 * SCALE;
		return this;

	}

	/*
	 * Returns Blocks ID along with their coords. Returns:List containg BlockPos
	 */
	public List getBlocksWithinAABB(AxisAlignedBB bound, World world) {

		List list = new ArrayList();
		for (int i = (int) bound.minX; i < Math.round(bound.maxX); i++) {
			for (int a = (int) bound.minY; a < Math.round(bound.maxY); a++) {
				for (int b = (int) bound.minZ; b < Math.round(bound.maxZ); b++) {
					// System.out.println("Attempting at " + i + " " + a + " " +
					// b);
					int id = world.getBlockId(i, a, b);
					if (id != 0) {
						list.add(new BlockPos(i, a, b, id));
						// System.out.println("Block Get!> <");
					}

				}
			}
		}
		return list;

	}

	public MotionXYZ updateMotion(double scale) {

		posX += motionX * scale;
		posY += motionY * scale;
		posZ += motionZ * scale;
		return this;

	}

	/**
	 * 获取以本身和另外一个MotionXYZ为顶点的碰撞箱。
	 * 
	 * @param another
	 * @return
	 */
	public final AxisAlignedBB getBoundingBox(MotionXYZ another) {
		double minX, minY, minZ, maxX, maxY, maxZ;
		if (another.posX > this.posX) {
			minX = this.posX;
			maxX = another.posX;
		} else {
			minX = another.posX;
			maxX = this.posX;
		}
		if (another.posY > this.posY) {
			minY = this.posY;
			maxY = another.posY;
		} else {
			minY = another.posY;
			maxY = this.posY;
		}
		if (another.posZ > this.posZ) {
			minZ = this.posZ;
			maxZ = another.posZ;
		} else {
			minZ = another.posZ;
			maxZ = this.posZ;
		}
		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	private void getPosByPlayer(EntityLiving par1Player) {

		posX = par1Player.posX;
		posY = par1Player.posY + par1Player.getEyeHeight();
		posZ = par1Player.posZ;

		float var3 = 1.0F, var4 = 0.0F;

		this.motionX = -MathHelper.sin(par1Player.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(par1Player.rotationPitch / 180.0F
						* (float) Math.PI) * var3;
		this.motionZ = MathHelper.cos(par1Player.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(par1Player.rotationPitch / 180.0F
						* (float) Math.PI) * var3;
		this.motionY = -MathHelper.sin((par1Player.rotationPitch + var4)
				/ 180.0F * (float) Math.PI)
				* var3;
	}

	public static final MotionXYZ getPosByPlayer2(EntityLiving par1Player) {
		return new MotionXYZ(par1Player, 0);
	}

	@Override
	public String toString() {
		return "motion[ " + motionX + ", " + motionY + ", " + motionZ + " ]";
	}
	
	public static void setMotionToEntity(MotionXYZ mo, Entity e) {
		e.setPosition(mo.posX, mo.posY, mo.posZ);
		e.motionX = mo.motionX;
		e.motionY = mo.motionY;
		e.motionZ = mo.motionZ;
	}

}
