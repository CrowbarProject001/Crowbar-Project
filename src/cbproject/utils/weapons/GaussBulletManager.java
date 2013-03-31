package cbproject.utils.weapons;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityGauss;
import cbproject.utils.BlockPos;

public class GaussBulletManager extends BulletManager {

	public static final double BB_SIZE = 1.0D;

	public GaussBulletManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void Shoot(EntityLiving entityPlayer, World worldObj, int damage, int offset, double par5Vel){
		System.out.println("called shoot2," + "IsRemote in Shoot: " +worldObj.isRemote);
		MotionXYZ motion;
		Boolean isHit;
		AxisAlignedBB bBox;
		
		motion = new MotionXYZ(entityPlayer, offset);
		isHit = false;
	
		bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
				motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
		
		//worldObj.spawnParticle("smoke", motion.posX, motion.posY, motion.posZ, motion.motionX, motion.motionY, motion.motionZ);
		int  times = 0;
		while(!isHit && times < 200){
			
			times++;
			if(doEntityCollision(motion, bBox, worldObj , entityPlayer, damage, par5Vel) ||
					doBlockCollision(entityPlayer, motion, bBox, worldObj, damage))
				break;
			motion.updateMotion(1.0);
			bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
					motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
			
		}

		return;
			
	}
	
	public static void doWallPenetrate(EntityLiving player ,BlockPos blockHit ,
			AxisAlignedBB bBox, World worldObj, int par5dmg, MotionXYZ motion) {
		double range = 0.2*par5dmg; //最大穿透距离: 8
		int blockFront = 1 + getFrontBlockCount(blockHit, worldObj, player, true);

		if(blockFront == 3)
			return;
		int damage = (int) Math.round(par5dmg * (1.0 - 0.33 * blockFront));
		motion.updateMotion(-1.0);
		Vec3 pre = worldObj.getWorldVec3Pool().getVecFromPool(motion.posX, motion.posY, motion.posZ);
		motion.updateMotion(1.0);
		Vec3 after = worldObj.getWorldVec3Pool().getVecFromPool(motion.posX, motion.posY, motion.posZ);
	    MovingObjectPosition var3 = worldObj.rayTraceBlocks(pre, after);
	    double var0 = doReflection(var3, motion, worldObj, player, damage);
	    
	    damage *= var0;
	    int radius = (int) Math.round(0.2 * damage); //Max: 5.28
	    int minX = blockHit.x, minY = blockHit.y, minZ = blockHit.z;
	    int maxX = blockHit.x, maxY = blockHit.y, maxZ = blockHit.z;
	    int[] s = getSideByPlayer(player);
	    int x = s[0], y = s[1], z = s[2];
	    
	    minY += -0.29D * radius *y;
	    maxY += radius * y;
	    minX += -0.29D * radius *x;
	    maxX += radius * x;
	    minZ += -0.29D * radius *z;
	    maxZ += radius * z;
	    
	    AxisAlignedBB box = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);   
	    List var1 = worldObj.getEntitiesWithinAABBExcludingEntity(player, box);
	    Entity var2;
	    
	    MotionXYZ var4 = new MotionXYZ(motion.posX, motion.posY, motion.posZ, motion.motionX, motion.motionY, motion.motionZ);
	    
	    BulletManager.Shoot(var4, player, worldObj, damage, 0 , CBCMod.cbcItems.weapon_gauss.pushForce[1]);
	    worldObj.spawnEntityInWorld(new EntityGauss(var4, worldObj));
	    System.out.println("Size found: " + var1.size());
	    for(int i = 0; i < var1.size(); i++){
	    	var2 = (Entity) var1.get(i);
	    	if(!(var2 instanceof EntityLiving))
	    		return;
	    	System.out.println("Entity name: " + var2.entityId);
	    	double distance = Math.pow((blockHit.x - var2.posX),2) + Math.pow((blockHit.y - var2.posY),2) + 
	    			Math.pow((blockHit.z - var2.posZ),2);
	    	distance = Math.pow(distance, 0.5);
	    	((EntityLiving)var2).attackEntityFrom(DamageSource.causeMobDamage(player), 
	    			(int) Math.round(damage * (1.0 - distance/(range * 1.414))));
	    }
		
	}

	/*
	 * do the gauss reflection when shooting angle towards the wall < 45°.
	 * returns: Convension Radius for WallPenetration
	 */
	private static double doReflection(MovingObjectPosition hit, MotionXYZ motion, World worldObj, EntityLiving player, int damage){
		return 1.0;
	}
	
	private static int getFrontBlockCount(BlockPos pos, World worldObj, EntityLiving player, Boolean recall) {
		
		int[] side = getSideByPlayer(player);
		int x = pos.x + side[0], y = pos.y + side[1], z = pos.z + side[2];
		int id = worldObj.getBlockId(x, y, z);
		if(id <= 1){
			return 0;
		}
		if(!recall)
			return 1;
		return 1 + getFrontBlockCount(new BlockPos(x,y,z,0), worldObj, player, false);
		
	}
	
	private static int[] getSideByPlayer(EntityLiving player){
		
		int dx = 0, dy, dz = 0;
		if(player.rotationPitch > -45 && player.rotationPitch < 45){
			dy = 0;
		} else if(player.rotationPitch > -65 && player.rotationPitch <65){
			dy = player.rotationPitch > 0 ? -1 : 1;
		} else {
			dy = player.rotationPitch > 0 ? -2 : 2;
		}
		if(2 != Math.abs(dy)){
			if(player.rotationYawHead > 315 || player.rotationYawHead <45)
				dz = 1;
			else if (player.rotationYawHead < 225 || player.rotationYawHead > 135)
				dz = -1;
			else dx = player.rotationYawHead < 180 ? -1 : 1;
		} else dy = (dy >0? 1 : -1);
		
		int[] side = {dx, dy, dz};
		return side;
			
	}
	
	private static double getConvensionRadius(BlockPos hit, MotionXYZ motion){
		return 1.0D;
	}

	public static Boolean doBlockCollision(EntityLiving player, MotionXYZ par1Motion, AxisAlignedBB par3BBox, World par4World, int par5Damage){
		
		BlockPos block;
		int x = MathHelper.floor_double(par1Motion.posX);
		int y = MathHelper.floor_double(par1Motion.posY);
		int z = MathHelper.floor_double(par1Motion.posZ);
		int id = par4World.getBlockId(x, y, z );
		if( id > 1){
			System.out.println("Collided. 1");
			doWallPenetrate( player,new BlockPos(x, y, z, id), par3BBox, par4World, par5Damage, par1Motion);
			return true;
		}

		return false;
	}
	

}
