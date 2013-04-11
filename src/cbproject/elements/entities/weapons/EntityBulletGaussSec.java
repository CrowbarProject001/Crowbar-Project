package cbproject.elements.entities.weapons;

import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.weapons.InformationWeapon;
import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * @description Gauss sencondary ray used to do penetration and reflection.
 */
public class EntityBulletGaussSec extends EntityBullet {

	public int damage;
	public enum EnumGaussRayType {
		PENETRATION, REFLECTION;
	}
	
	public EntityBulletGaussSec(EnumGaussRayType typeOfRay, World worldObj, EntityLiving entityPlayer,
			ItemStack itemStack, MovingObjectPosition result, MotionXYZ motion, int dmg) {
		
		super(worldObj, entityPlayer, itemStack, "");
		
		MotionXYZ real = new MotionXYZ(result.hitVec, motion.motionX, motion.motionY, motion.motionZ);
		posX = real.posX;
		posY = real.posY;
		posZ = real.posZ;
		motionX = real.motionX;
		motionY = real.motionY;
		motionZ = real.motionZ;
		damage = dmg;
		/*
		 * sideHit: Which side was hit. 
		 * If its -1 then it went the full length of the ray trace. 
		 * Bottom = 0, Top = 1, East = 2, West = 3, North = 4, South = 5.
		 * 
		 * EAST: +Z, WEST: -Z, NORTH: -X SOUTH: +X
		 */
		if(typeOfRay == EnumGaussRayType.PENETRATION){
			double du = 0.0;
			//Only effective on 1block penetration.
			System.out.println("Hit Side : " + result.sideHit);
			du = getMiniumUpdate(result);
			real.updateMotion(du); //Should now be another side of block
			this.setPosition(real.posX, real.posY, real.posZ);
			System.out.println("du " + du);
			System.out.println("Penetration ray. Start : " + real);
		} else {
			
			switch(result.sideHit){
			case 0:
			case 1:
				motionY = -motionY;
				break;
			case 2:
			case 3:
			    motionZ = -motionZ;
				break;
			case 4:
			case 5:
				motionX = -motionX;
				break;
			default:
				return;
			}
			
			System.out.println("Reflection ray. Start : " + real);
			
		}
		
		this.setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
		motion = new MotionXYZ(this);
		worldObj.spawnEntityInWorld( new EntityGaussRay(motion, worldObj, getThrower()) );
		
	}
	
	/**
	 * Get the minium updateMotion radius for penetrating an block.
	 * @return du = updateMotionRadius
	 */
	private double getMiniumUpdate(MovingObjectPosition result){
		
		double du = 0.0;
		double dx = result.hitVec.xCoord - result.blockX
				, dy = result.hitVec.yCoord - result.blockY
				, dz = result.hitVec.zCoord - result.blockZ;
		if(motionX > 0) dx = 1-dx;
		if(motionY > 0) dy = 1-dy;
		if(motionZ > 0) dz = 1-dz;
		
		dx =  Math.abs(dx / motionX);
		dy =  Math.abs(dy / motionY);
		dz =  Math.abs(dz / motionZ);
		return Math.min(Math.min(dx, dy), dz);
		
	}
	
	@Override
	protected void doBlockCollision(MovingObjectPosition result){	
		this.setDead();
	}
	
	@Override
	public void doEntityCollision(MovingObjectPosition result){
		
		if( result.entityHit == null || (!(result.entityHit instanceof EntityLiving)))
			return;

		double var0 = damage/20;
		double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ * var0;
		
		EntityLiving mob = (EntityLiving) result.entityHit;
		mob.attackEntityFrom(DamageSource.causeMobDamage(player), damage);
		mob.addVelocity(dx, dy, dz);
		
		
	}
	
	@Override
	public float func_70182_d(){
		return 50.0F;
	}
	

}
