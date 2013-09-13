package cn.lambdacraft.deathmatch.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cn.lambdacraft.core.util.BlockPos;
import cn.lambdacraft.deathmatch.entity.EntityBulletGauss;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec;
import cn.lambdacraft.deathmatch.entity.EntityBulletGaussSec.EnumGaussRayType;
import cn.weaponmod.util.MotionXYZ;

public class GaussBulletManager {

	public static void Shoot(ItemStack itemStack, EntityPlayer entityPlayer,
			World worldObj) {
		worldObj.spawnEntityInWorld(new EntityBulletGauss(worldObj, entityPlayer, itemStack));
	}

	/**
	 * @description Used for penetration & reflection rays.
	 * @param motion
	 *            : give motionX, Y, Z for velocity calculation.
	 * @param result
	 *            : give beginning point.
	 */
	public static void Shoot2(EnumGaussRayType typeOfRay, World worldObj,
			EntityLivingBase entityLivingBase, ItemStack itemStack,
			MovingObjectPosition result, MotionXYZ motion, int damage) {
		worldObj.spawnEntityInWorld(new EntityBulletGaussSec(typeOfRay, worldObj, entityLivingBase, itemStack, result, motion, damage));
	}

	public static Boolean doBlockCollision(EntityPlayer player,
			MotionXYZ par1Motion, AxisAlignedBB par3BBox, World par4World,
			int par5Damage) {

		BlockPos block;
		int x = MathHelper.floor_double(par1Motion.posX);
		int y = MathHelper.floor_double(par1Motion.posY);
		int z = MathHelper.floor_double(par1Motion.posZ);
		int id = par4World.getBlockId(x, y, z);
		if (id > 1) {
			return true;
		}

		return false;
	}

}
