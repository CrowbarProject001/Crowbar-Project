package cbproject.elements.entities.weapons;

import java.util.List;

import cbproject.elements.entities.weapons.EntityBulletGaussSec.EnumGaussRayType;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.BlockPos;
import cbproject.utils.weapons.GaussBulletManager;
import cbproject.utils.weapons.InformationEnergy;
import cbproject.utils.weapons.MotionXYZ;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * @description Gauss ray collision entity.
 */
public class EntityBulletGauss extends EntityBullet {
	
	private static final float CHARGE_DAMAGE_SCALE = 10.0F;

	private WeaponGeneral item;
	private InformationEnergy inf;
	
	public EntityBulletGauss(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack, String particle) {
		super(par1World, par2EntityLiving, par3itemStack, particle);
		motion = new MotionXYZ(par2EntityLiving);
		item = (WeaponGeneral) itemStack.getItem();
		inf = item.getInformation(itemStack).getProperEnergy(worldObj);
		worldObj.spawnEntityInWorld(new EntityGaussRay(par1World, par2EntityLiving, motionX, motionY, motionZ));	
	}
	
	@Override
	protected void onImpact(MovingObjectPosition par1)
	{    
	    switch(par1.typeOfHit){
	    case TILE:
	    	doBlockCollision(par1);
	    	this.setDead();
	    	break;
	    	
	    case ENTITY:
	    	doEntityCollision(par1);
	    	break;
	    }
	   
	}
	
	@Override
	protected void doBlockCollision(MovingObjectPosition result){	
		doWallPenetrate(result);
	}
	
	/**
	 * Damages entity base on gauss mode.
	 */
	@Override
	public void doEntityCollision(MovingObjectPosition result){
		if( result.entityHit == null || (!(result.entityHit instanceof EntityLiving)))
			return;		
		
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		int mode = inf.mode;
		if(mode == 0){
			doNormalAttack(result, inf, item);
		} else doChargeAttack(result, inf, item);
	}
	
	/**
	 * @description Gauss wall penetrating func.
	 * @param result - RayTrace result.
	 */
	private void doWallPenetrate(MovingObjectPosition result) {
		
		if(result.sideHit == -1)
			return;
		
		//do reflection and decay damage
		int damage = doReflection(result);
		
		int blockFront = 1 + getFrontBlockCount(new BlockPos(result.blockX, result.blockY, result.blockZ, 0)
			, worldObj, true);
		
		if(blockFront >= 3) //Too thick,cannot penetrate
			return;
		GaussBulletManager.Shoot2(EnumGaussRayType.PENETRATION, worldObj, getThrower(), itemStack, result, motion, damage);
		
	    double radius = Math.round(0.3 * damage); //Max: 5.28
		damage = (int) Math.round(damage * (1.0 - 0.33 * blockFront)); //Decay based on blockFront
		
		int[] s = getSideByMotion();
	    double cx = result.blockX, cy = result.blockY, cz = result.blockZ;
	    cx += s[0] * (radius - 1);
	    cy += s[1] * (radius - 1);
	    cz += s[2] * (radius - 1);
	    AxisAlignedBB box = AxisAlignedBB.getBoundingBox(cx - radius, cy - radius, cz - radius,
	    		cx + radius, cy + radius, cz + radius);   //穿墙伤害到的范围
	    List var1 = worldObj.getEntitiesWithinAABBExcludingEntity(null, box);
	    System.out.println("list size : " + var1.size() + "; radius :" + radius);
	    Entity var2;

	    for(int i = 0; i < var1.size(); i++){
	    	var2 = (Entity) var1.get(i);
	    	if(!(var2 instanceof EntityLiving))
	    		continue;
	    	//Calculate distance & damage, damage entities.
	    	double distance =Math.pow(Math.pow((result.blockX - var2.posX),2) + Math.pow((result.blockY - var2.posY),2) + 
	    			Math.pow((result.blockZ - var2.posZ),2), 0.5);
	    	int dmg = (int) Math.round((damage * (1.0 - distance/ (radius * 1.414)) * CHARGE_DAMAGE_SCALE)) ;
	    	System.out.println("Damage: " + dmg);
	    	distance = Math.pow(distance, 0.5);
	    	((EntityLiving)var2).attackEntityFrom(DamageSource.causeMobDamage(getThrower()), dmg);
	    }
		
	}
	
	/**
	 * @param result - RayTrace result
	 * @return Decayed charge damage
	 */
	private int doReflection(MovingObjectPosition result) {
			
		double a = Math.sqrt(motionX * motionX + 
				motionY * motionY +  motionZ * motionZ);
		double sin = 0.0; //入射角正弦值
		double b = 0.0;

		switch(result.sideHit){
		case 0:
		case 1:
			b = motionY;
			break;
		case 2:
		case 3:
			b = motionZ;
			break;
		case 4:
		case 5:
			b = motionX;
			break;
		default:
			return getChargeDamage();
		}
		
		sin = b / a;
		double sin45 = 0.7071067812;
		int damage = 0;
		if( -sin45 < sin && sin < sin45 ){
			damage = (int) Math.round( Math.sqrt(1 - sin * sin ) * getChargeDamage() );
			System.out.println("Refelection dmg : " + damage);
			GaussBulletManager.Shoot2(EnumGaussRayType.REFLECTION, worldObj, getThrower(), itemStack, result, motion, damage);
		}
		return getChargeDamage() - damage;
		
	}
	
	private void doNormalAttack(MovingObjectPosition result, InformationEnergy information, WeaponGeneral item){
		
		int mode = 0;
		double pf = item.getPushForce(0);
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
		EntityLiving mob = (EntityLiving) result.entityHit;

		mob.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), item.getDamage(mode));
		mob.addVelocity(dx, dy, dz);
		
		this.setDead();
		
	}
	
	private void doChargeAttack(MovingObjectPosition result, InformationEnergy information, WeaponGeneral item) {

		int damage = getChargeDamage();
		double var0 = damage/20;
		double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ * var0;
		
		EntityLiving mob = (EntityLiving) result.entityHit;
		mob.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damage);
		mob.addVelocity(dx, dy, dz);
		
	}
	
	/**
	 * Get blockCount in front of player direction by judging with getSidebyMotion().
	 */
	private int getFrontBlockCount(BlockPos pos, World worldObj, Boolean recall) {
		
		int[] side = getSideByMotion();
		int x = pos.x + side[0], y = pos.y + side[1], z = pos.z + side[2];
		int id = worldObj.getBlockId(x, y, z);
		if(id <= 1){
			return 0;
		}
		if(!recall)
			return 1;
		return 1 + getFrontBlockCount(new BlockPos(x,y,z,0), worldObj, false);
		
	}
	
	/**
	 * Get player rough viewing side by calculating its rotationPitch and rotationYaw.
	 * returns: int[0]:X direction facing;
	 * 	int[1] : Y direction facing;
	 *  int[2] : Z direction facing;
	 *  value : +1, -1, 0
	 */
	private int[] getSideByMotion(){
		
		int dx = 0, dy, dz = 0;
		if(rotationPitch > -45 && rotationPitch < 45){
			dy = 0;
		} else if(rotationPitch > -65 && rotationPitch <65){
			dy = rotationPitch > 0 ? -1 : 1;
		} else {
			dy = rotationPitch > 0 ? -2 : 2;
		}
		if(2 != Math.abs(dy)){
			if(rotationYaw > 315 || rotationYaw <45)
				dz = 1;
			else if (rotationYaw < 225 || rotationYaw > 135)
				dz = -1;
			else dx = rotationYaw < 180 ? -1 : 1;
		} else dy = (dy >0? 1 : -1);
		
		int[] side = {dx, dy, dz};
		return side;
			
	}
	
	private int getChargeDamage(){
		return (int) Math.round(inf.charge * 0.66) ;
	}
	
}
