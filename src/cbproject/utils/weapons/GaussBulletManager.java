package cbproject.utils.weapons;

import java.util.List;

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
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityBullet;
import cbproject.elements.entities.weapons.EntityBulletGauss;
import cbproject.elements.entities.weapons.EntityBulletGaussSec;
import cbproject.elements.entities.weapons.EntityGaussRay;
import cbproject.elements.entities.weapons.EntityBulletGaussSec.EnumGaussRayType;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.BlockPos;

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
