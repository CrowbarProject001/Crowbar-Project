package cbproject.utils.weapons;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityBullet;
import cbproject.elements.entities.weapons.EntityBulletGauss;
import cbproject.elements.entities.weapons.EntityBulletGaussSec;
import cbproject.elements.entities.weapons.EntityGauss;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.BlockPos;

public class GaussBulletManager extends BulletManager {


	public GaussBulletManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void Shoot(ItemStack itemStack, EntityLiving entityPlayer, World worldObj, String particle){
		worldObj.spawnEntityInWorld(new EntityBulletGauss(worldObj, entityPlayer, itemStack, particle));
	}
	
	/*
	 * Provide beginning point for shoot rather than use Player's coords.
	 * used in GaussWallPenetration
	 * begin : origin Shooter coords
	 * end : blockHit coords & new motion
	 * damage : damage to apply
	 */
	public static void Shoot2(MotionXYZ begin, MotionXYZ end, 
			ItemStack itemStack, EntityLiving entityPlayer, World worldObj, int damage, MovingObjectPosition result, int typeOfRay){
		
		worldObj.spawnEntityInWorld(new EntityBulletGaussSec(begin, end, itemStack, 
				entityPlayer, worldObj, damage, result, typeOfRay));
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
	
	public static void doWallPenetrate(EntityLiving player ,BlockPos blockHit ,
			AxisAlignedBB bBox, World worldObj, int par5dmg, MotionXYZ motion) {
		/*
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
	    
	   // BulletManager.Shoot(var4, player, worldObj, damage, 0 , CBCMod.cbcItems.weapon_gauss.pushForce[1]);
	    //worldObj.spawnEntityInWorld(new EntityGauss(var4, worldObj));
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
		*/
	}

	/*
	 * do the gauss reflection when shooting angle towards the wall < 45°.
	 * returns: Convension Radius for WallPenetration
	 */
	private static double doReflection(MovingObjectPosition hit, MotionXYZ motion, World worldObj, EntityLiving player, int damage){
		return 1.0;
	}
	

	
	private static double getConvensionRadius(BlockPos hit, MotionXYZ motion){
		return 1.0D;
	}

}
