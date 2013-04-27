package cbproject.deathmatch.utils;

import java.util.List;

import cbproject.core.CBCMod;
import cbproject.core.utils.BlockPos;
import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.entities.EntityBullet;
import cbproject.deathmatch.entities.EntityBulletGauss;
import cbproject.deathmatch.entities.EntityBulletGaussSec;
import cbproject.deathmatch.entities.EntityGaussRay;
import cbproject.deathmatch.entities.EntityBulletGaussSec.EnumGaussRayType;
import cbproject.deathmatch.items.wpns.WeaponGeneral;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class GaussBulletManager{

	public static void Shoot(ItemStack itemStack, EntityLiving entityPlayer, World worldObj){
		worldObj.spawnEntityInWorld(new EntityBulletGauss(worldObj, entityPlayer, itemStack, ""));
	}
	
	/**
	 * @description Used for penetration & reflection rays.
	 * @param motion : give motionX, Y, Z for velocity calculation.
	 * @param result : give beginning point.
	 */
	public static void Shoot2(EnumGaussRayType typeOfRay, World worldObj, EntityLiving entityPlayer, ItemStack itemStack,
			MovingObjectPosition result, MotionXYZ motion, int damage){
		
		worldObj.spawnEntityInWorld(new EntityBulletGaussSec(typeOfRay, worldObj, entityPlayer, 
				itemStack, result, motion, damage));
	}
	
	public static Boolean doBlockCollision(EntityLiving player, MotionXYZ par1Motion, AxisAlignedBB par3BBox, World par4World, int par5Damage){
		
		BlockPos block;
		int x = MathHelper.floor_double(par1Motion.posX);
		int y = MathHelper.floor_double(par1Motion.posY);
		int z = MathHelper.floor_double(par1Motion.posZ);
		int id = par4World.getBlockId(x, y, z );
		if( id > 1){
			System.out.println("Collided.");
			
			return true;
		}

		return false;
	}
	
}
