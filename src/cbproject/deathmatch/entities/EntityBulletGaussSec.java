package cbproject.deathmatch.entities;

import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.utils.BulletManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * @description Gauss secondary ray used to do penetration and reflection.
 */
public class EntityBulletGaussSec extends EntityBullet {

	public int damage;
	public enum EnumGaussRayType {
		PENETRATION, REFLECTION, NORMAL;
	}
	
	public EntityBulletGaussSec(EnumGaussRayType typeOfRay, World worldObj, EntityLiving entityPlayer,
			ItemStack itemStack, MovingObjectPosition result, MotionXYZ motion, int dmg) {
		
		super(worldObj, entityPlayer, itemStack, "");
		if(typeOfRay == EnumGaussRayType.NORMAL){
			damage = dmg;
			worldObj.spawnEntityInWorld( new EntityGaussRay(new MotionXYZ(this), worldObj, getThrower()) );
			return;
		}
		
		MotionXYZ real = new MotionXYZ(result.hitVec, motion.motionX, motion.motionY, motion.motionZ);
		damage = dmg;
		
		if(typeOfRay == EnumGaussRayType.PENETRATION){
			double du = 0.0;
			du = getMiniumUpdate(result, real);
			real.updateMotion(du);
		} else if(typeOfRay == EnumGaussRayType.REFLECTION) {
			
			switch(result.sideHit){
			case 0:
			case 1:
				real.motionY = -real.motionY;
				break;
			case 2:
			case 3:
				real.motionZ = -real.motionZ;
				break;
			case 4:
			case 5:
				real.motionX = -real.motionX;
				break;
			default:
				this.setDead();
			}
			
		}
		
		motionX = real.motionX;
		motionY = real.motionY;
		motionZ = real.motionZ;
		real.updateMotion(0.001);
		setPosition(real.posX, real.posY, real.posZ);
		this.setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
		worldObj.spawnEntityInWorld( new EntityGaussRay(real, worldObj, getThrower()) );
		
	}
	
	/**
	 * Get the minium updateMotion radius for penetrating an block.
	 * @return du = updateMotionRadius
	 */
	private double getMiniumUpdate(MovingObjectPosition result, MotionXYZ real){
		
		double du = 0.0;
		double dx = -result.hitVec.xCoord + result.blockX
				, dy = -result.hitVec.yCoord + result.blockY
				, dz = -result.hitVec.zCoord + result.blockZ;
		if(real.motionX > 0) dx = 1+dx;
		if(real.motionY > 0) dy = 1+dy;
		if(real.motionZ > 0) dz = 1+dz;
		
		dx =  dx / real.motionX;
		dy =  dy / real.motionY;
		dz =  dz / real.motionZ;
		if(dx < 0)
			dx = 10000;
		if(dy < 0)
			dy = 10000;
		if(dz < 0)
			dz = 10000;
		return Math.min(Math.min(dx, dy), dz);
		
	}
	
	@Override
	public void doEntityCollision(MovingObjectPosition result){
		
		if(result.entityHit == null)
			return;
		if(!(result.entityHit instanceof EntityLiving || result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		
		double var0 = damage/20;
		double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ * var0;
		
		BulletManager.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), damage, dx, dy, dz);
		
	}

}
