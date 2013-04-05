package cbproject.elements.entities.weapons;

import java.util.List;

import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.BlockPos;
import cbproject.utils.weapons.GaussBulletManager;
import cbproject.utils.weapons.InformationEnergy;
import cbproject.utils.weapons.InformationWeapon;
import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class EntityBulletGauss extends EntityBullet {

	public Vec3 start;
	public Boolean doPenetrate;
	private MotionXYZ motion;
	private static final float CHARGE_DAMAGE_SCALE = 10.0F;
	
	public EntityBulletGauss(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack, String particle) {
		
		super(par1World, par2EntityLiving, par3itemStack, particle);
		start = Vec3.createVectorHelper(par2EntityLiving.posX, par2EntityLiving.posY, par2EntityLiving.posZ);
		motion = new MotionXYZ(par2EntityLiving);
		worldObj.spawnEntityInWorld(new EntityGauss(par1World, par2EntityLiving, motion.motionX, motion.motionY, motion.motionZ));
		doPenetrate = true;
	}
	
	public EntityBulletGauss(MotionXYZ begin,float pitch, float yaw, ItemStack itemStack, EntityLiving entityPlayer, World worldObj){
		
		super(worldObj, entityPlayer, itemStack, "");
		
		doPenetrate = false;
		motionX = begin.motionX;
		motionY = begin.motionY;
		motionZ = begin.motionZ;
		this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
		this.setLocationAndAngles(begin.posX, begin.posY, begin.posZ, pitch, yaw);
		
	}
	
	/*
	 * Gauss wall penetrate func.
	 */
	protected void doBlockCollision(MovingObjectPosition result){	
		if(doPenetrate)
			doWallPenetrate(result);
	}
	
	private void doWallPenetrate(MovingObjectPosition result) {
		
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationEnergy inf = item.getInformation(itemStack).getProperEnergy(worldObj);
		int damage = getChargeDamage(inf);
		doReflection(result, motion, worldObj); //反射函数 同时进行damage的衰减
		
		double range = 0.2*damage; //最大穿透距离: 8
		int blockFront = 1 + getFrontBlockCount(new BlockPos(result.blockX, result.blockY, result.blockZ, 0)
			, worldObj, true);
		if(blockFront >= 3) //太厚了穿透不能QAQ
			return;

	    double radius = Math.round(0.2 * damage); //Max: 5.28
		damage = (int) Math.round(damage * (1.0 - 0.33 * blockFront)); //这里是衰减
		int[] s = getSideByMotion();
	    double cx = result.blockX, cy = result.blockY, cz = result.blockZ;
	    
	    //接下来是实际的碰撞函数
	    cx += s[0] * (radius - 1);
	    cy += s[1] * (radius - 1);
	    cz += s[2] * (radius - 1);
	    
	    AxisAlignedBB box = AxisAlignedBB.getBoundingBox(cx - radius, cy - radius, cz - radius,
	    		cx + radius, cy + radius, cz + radius);   //穿墙伤害到的范围
	    List var1 = worldObj.getEntitiesWithinAABBExcludingEntity(player, box);
	    Entity var2;
	    MotionXYZ var4 = new MotionXYZ(result.blockX, result.blockY, result.blockZ,
	    		motion.motionX, motion.motionY, motion.motionZ).updateMotion(2.0);
	    GaussBulletManager.Shoot(var4,this.rotationPitch,this.rotationYaw, itemStack, getThrower(), worldObj); //穿墙后直射的光线

	    System.out.println(box);
	    for(int i = 0; i < var1.size(); i++){
	    	var2 = (Entity) var1.get(i);
	    	if(!(var2 instanceof EntityLiving))
	    		continue;
	    	//距离计算，伤害计算，伤害实体
	    	double distance =Math.pow(Math.pow((result.blockX - var2.posX),2) + Math.pow((result.blockY - var2.posY),2) + 
	    			Math.pow((result.blockZ - var2.posZ),2), 0.5);
	    	int dmg = (int) Math.round((damage * (1.0 - distance/ (range * 1.414)) * CHARGE_DAMAGE_SCALE)) ;
	    	System.out.println("Damage: " + dmg);
	    	distance = Math.pow(distance, 0.5);
	    	((EntityLiving)var2).attackEntityFrom(DamageSource.causeMobDamage(player), dmg);
	    }
		
	}
	
	/*
	 * 穿墙射击的反射计算函数.
	 */
	private void doReflection(MovingObjectPosition result, MotionXYZ motion2,
			World worldObj) {
		
		MotionXYZ start = new MotionXYZ(motion2);
		start.posX = result.blockX;
		start.posY = result.blockY;
		start.posZ = result.blockZ;
		double a = Math.pow(Math.pow(start.motionX, 2) + 
				Math.pow(start.motionY, 2) +  Math.pow(start.motionZ, 2), 0.5);
		double sin = 0.0; //入射角正弦值
		float pitch = rotationPitch, yaw = rotationYaw;
		/*
		 * sideHit: Which side was hit. 
		 * If its -1 then it went the full length of the ray trace. 
		 * Bottom = 0, Top = 1, East = 2, West = 3, North = 4, South = 5.
		 * 
		 * EAST: +X, WEST: -X, NORTH: -Z SOUTH: +Z
		 */
		
		switch(result.sideHit){
		case 0:
		case 1:
			sin = - motionY / a;
			start.motionY = -start.motionY;
			break;
		case 2: 
		case 3:
			sin = - motionX /a;
			start.motionX = -start.motionX;
			break;
		case 4:
		case 5:
			sin = - motionZ /a;
			start.motionZ = -start.motionZ;
			break;
		default:
			return;
		}
		System.out.println("Shoot angle sin: " + sin);
		if(sin < -45 || sin > 45){
			motion.updateMotion(0.5F);
			GaussBulletManager.Shoot(start, pitch, yaw, itemStack, getThrower(), worldObj);
		}
		
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
	
	public void doEntityCollision(MovingObjectPosition result){
	
		if( result.entityHit == null || (!(result.entityHit instanceof EntityLiving)))
			return;		
		
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationEnergy inf = item.getInformation(itemStack).getProperEnergy(worldObj);
		int mode = inf.mode;
		if(mode == 0){
			doNormalAttack(result, inf, item);
		} else doEntityCharge(result, inf, item);
		
	}
	
	private void doNormalAttack(MovingObjectPosition result, InformationEnergy information, WeaponGeneral item){
		
		int mode = information.mode;
		double pf = item.pushForce[mode];
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
		EntityLiving mob = (EntityLiving) result.entityHit;

		mob.attackEntityFrom(DamageSource.causeMobDamage(player), item.damage[mode]);
		mob.addVelocity(dx, dy, dz);
	}
	
	private void doEntityCharge(MovingObjectPosition result, InformationEnergy information, WeaponGeneral item) {

		int mode = information.mode;
		int damage = getChargeDamage(information);
		double var0 = damage/20;
		double dx = motion.motionX * var0, dy = motion.motionY * var0, dz = motion.motionZ * var0;
		
		EntityLiving mob = (EntityLiving) result.entityHit;

		mob.attackEntityFrom(DamageSource.causeMobDamage(player), damage); //伤害实体
		mob.addVelocity(dx, dy, dz); //击飞效果
		
	}
	
	/*
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
	
	/*
	 * Get player rough viewing side by calculating its rotationPitch and rotationYaw.
	 * returns: int[0]:X direction facing
	 * 	int[1] : Y direction facing
	 *  int[2] : Z direction facing
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
	
	private int getChargeDamage(InformationEnergy inf){
		return (int) Math.round(inf.charge * 0.66) ;
	}
	
	@Override
	protected float func_70182_d(){
		return 50.0F;
	}

}
