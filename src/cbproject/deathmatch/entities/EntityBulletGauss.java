package cbproject.deathmatch.entities;

import java.util.List;

import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.entities.EntityBulletGaussSec.EnumGaussRayType;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.items.wpns.Weapon_gauss;
import cbproject.deathmatch.utils.BulletManager;
import cbproject.deathmatch.utils.GaussBulletManager;
import cbproject.deathmatch.utils.InformationEnergy;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * @description Gauss ray collision entity(used in charge mode only).
 */
public class EntityBulletGauss extends EntityBullet {
	
	private static final float CHARGE_DAMAGE_SCALE = 2.0F;

	private Weapon_gauss item;
	private InformationEnergy inf;
	
	public enum EnumSubPlain{
		PLAIN_X, PLAIN_Y, PLAIN_Z;
	}
	
	public EntityBulletGauss(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack, String particle) {
		super(par1World, par2EntityLiving, par3itemStack, particle);
		motion = new MotionXYZ(par2EntityLiving);
		item = (Weapon_gauss) itemStack.getItem();
		inf = item.getInformation(itemStack, worldObj);
		worldObj.spawnEntityInWorld(new EntityGaussRay(par1World, par2EntityLiving, this));	
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
		if( result.entityHit == null )
			return;		
		
		doChargeAttack(result, inf, item);
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
		
		int blockFront = getFrontBlockCount(result);
		
		if(blockFront >= 3) //Too thick,cannot penetrate
			return;
		GaussBulletManager.Shoot2(EnumGaussRayType.PENETRATION, worldObj, getThrower(), itemStack, result, motion, damage);
		
	    double radius = Math.round(0.3 * damage); //Max: 5.28
		damage = (int) Math.round(damage * (1.0 - 0.33 * blockFront)); //Decay based on blockFront
	    
	    AxisAlignedBB box = getPenetratingBox(radius, result);
	    if(box == null)
	    	return;
	    
	    System.out.println(box);
	    List var1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, box);
	    Entity var2;

	    for(int i = 0; i < var1.size(); i++){
	    	var2 = (Entity) var1.get(i);
	    	if(result.entityHit == null)
				continue;
	    	if(!(result.entityHit instanceof EntityLiving || result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
				continue;
	    	//Calculate distance & damage, damage entities.
	    	double distance =Math.sqrt(Math.pow((result.hitVec.xCoord - var2.posX),2) + Math.pow((result.hitVec.yCoord - var2.posY),2) + 
	    			Math.pow((result.hitVec.zCoord - var2.posZ),2));
	    	int dmg = (int) Math.round((damage * Math.pow(1.0 - distance/ (radius * 1.732), 2) * CHARGE_DAMAGE_SCALE));
	    	double var0 = dmg/20;
			double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ * var0;
	    	BulletManager.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), dmg, dx, dy, dz);
	    }
		
	}
	
	/**
	 * @param result - RayTrace result
	 * @return Decayed charge damage
	 */
	private int doReflection(MovingObjectPosition result) {
			
		double sin = getSinBySideAndMotion(result.sideHit);
		if(sin == -2)
			return getChargeDamage();
		double sin45 = 0.7071067812;
		int damage = 0;
		if( -sin45 < sin && sin < sin45 ){
			damage = (int) Math.round( Math.sqrt(1 - sin * sin ) * getChargeDamage() );
			GaussBulletManager.Shoot2(EnumGaussRayType.REFLECTION, worldObj, getThrower(), itemStack, result, motion, damage);
		}
		return getChargeDamage() - damage;
		
	}
	
	private void doChargeAttack(MovingObjectPosition result, InformationEnergy information, WeaponGeneral item) {

		int damage = getChargeDamage();
		double var0 = damage/20;
		double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ * var0;
		if(result.entityHit == null)
			return;
		if(!(result.entityHit instanceof EntityLiving || result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		BulletManager.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), damage, dx, dy, dz);
		
	}
	
	/**
	 * Get blockCount in front of player direction by judging with getSidebyMotion().
	 */
	private int getFrontBlockCount(MovingObjectPosition result) {
		
		Vec3 side = getSideByMotion();
		return 1 + (worldObj.getBlockId(result.blockX + (int)side.xCoord,
				result.blockY + (int)side.yCoord, result.blockZ + (int)side.zCoord) > 0 ? 1 : 0);
		
	}
	
	/**
	 * Get player rough viewing side by calculating its rotationPitch and rotationYaw.
	 * returns: int[0]:X direction facing;
	 * 	int[1] : Y direction facing;
	 *  int[2] : Z direction facing;
	 *  value : +1, -1, 0
	 */
	private Vec3 getSideByMotion(){
		
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
		
		return Vec3.createVectorHelper(dx, dy, dz);
	}
	
	private AxisAlignedBB getPenetratingBox(double radius, MovingObjectPosition result){
		if(radius <= 0.0)
			return null;
		
		double dx, dy, dz;
		//center is inside the hitting block.
		if(radius > 5) radius = 5;
		switch(result.sideHit){
		case -1:
			return null;
		case 0:
		case 1:
			dy = (motionY > 0) ?radius +1 : -radius - 1;
			dx = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_X) * radius * (motionY > 0 ? 1 : -1);
			dz = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Z) * radius * (motionY > 0 ? 1 : -1); 
			break;
		case 2:
		case 3:
			dz = (motionZ > 0) ?radius+1 : -radius - 1;
			dx = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_X) * radius * (motionZ > 0 ? 1 : -1);
			dy = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Y) * radius * (motionZ > 0 ? 1 : -1);
			break;
		case 4:
		case 5:
			dx = (motionX > 0) ?radius+1 : -radius - 1;
			dy = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Y) * radius * (motionX > 0 ? 1 : -1);
			dz = getTanBySideAndMotion(result.sideHit, EnumSubPlain.PLAIN_Z) * radius * (motionX > 0 ? 1 : -1);
			break;
		default:
			return null;
		}
		Vec3 center = result.hitVec.addVector(dx, dy, dz);
		return AxisAlignedBB.getBoundingBox(center.xCoord - radius, center.yCoord - radius, center.zCoord - radius,
				center.xCoord + radius, center.yCoord + radius, center.zCoord + radius);
		
	}
	
	/**
	 * get the tangent value of the motion in a specific subplain.
	 * @param sideHit HitVec sidehit
	 * @param subPlain with sideHit determines a single plain,such as XoZ
	 * @return tangent value, could be lower than 0
	 */
	private double getTanBySideAndMotion(int sideHit, EnumSubPlain subPlain){
		double a = 0.0;
		if(subPlain == EnumSubPlain.PLAIN_X)
			a = motionX;
		if(subPlain == EnumSubPlain.PLAIN_Y)
			a = motionY;
		if(subPlain == EnumSubPlain.PLAIN_Z)
			a = motionZ;
		
		double b = 0.0;
		switch(sideHit){
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
			return -2;
		}
		
		double tan = a/b;
		return tan;
	}
	
	private double getSinBySideAndMotion(int sideHit){
		double a = Math.sqrt(motionX * motionX + 
				motionY * motionY +  motionZ * motionZ);
		double sin = 0.0; //入射角正弦值
		double b = 0.0;

		switch(sideHit){
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
			return -2;
		}
		
		sin = b / a;
		return sin;
	}
	
	private int getChargeDamage(){
		return Math.round(inf.charge) ;
	}
	
}
