package cbproject.utils.weapons;

import java.util.List;

import cbproject.utils.BlockPos;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BulletManager {


	private static final double BB_SIZE = 1.0D;
	
	public BulletManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void Shoot(EntityLiving entityPlayer, World worldObj, int damage, int offset, double par5Vel){
		System.out.println("IsRemote in Shoot: " +worldObj.isRemote);
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
					doBlockCollision(motion, worldObj))
				break;
			motion.updateMotion(1.0);
			bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
					motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
			
		}

		return;
			
	}
	
	public static void Shoot(MotionXYZ motion, EntityLiving entityPlayer, World worldObj, int damage, int offset, double par5Vel){
		
		System.out.println("called shoot2," + "IsRemote in Shoot: " +worldObj.isRemote);
		Boolean isHit;
		AxisAlignedBB bBox;

		isHit = false;
	
		bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
				motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
		while(doBlockCollision(motion,worldObj))
			motion.updateMotion(1.0F);
		worldObj.spawnParticle("flame", motion.posX, motion.posY, motion.posZ, motion.motionX, motion.motionY, motion.motionZ);
		int  times = 0;
		while(!isHit && times < 200){
			worldObj.spawnParticle("flame", motion.posX, motion.posY, motion.posZ, motion.motionX, motion.motionY, motion.motionZ);
			times++;
			if(doEntityCollision(motion, bBox, worldObj , entityPlayer, damage, par5Vel) ||
					doBlockCollision(motion,worldObj))
				break;
			motion.updateMotion(1.0);
			bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
					motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
			
		}
		System.out.println(times);
		return;
			
	}
	
	protected Boolean isInBlock(double x,double y, double z, BlockPos block){
		return (block.x < x && x <block.x+1 && block.y < y && y <block.y+1 && block.z < z && z <block.z+1);
	}
	
	
	protected static Boolean isEntityIn(double x,double y, double z, Entity ent){
		return ent.boundingBox.isVecInside(Vec3.createVectorHelper(x, y, z));
	}
	
	protected static Boolean doBlockCollision(MotionXYZ par1Motion, World par4World){
		
		BlockPos block;
		int x = MathHelper.floor_double(par1Motion.posX);
		int y = MathHelper.floor_double(par1Motion.posY);
		int z = MathHelper.floor_double(par1Motion.posZ);
		int id = par4World.getBlockId(x, y, z );
		
		if( id > 1){
			System.out.println("Collided. 2");
			return true;
		}

		return false;
		
	}
	
	public static Boolean doEntityCollision(MotionXYZ par1Motion, AxisAlignedBB par3BBox, World par4World,
			EntityLiving par5EntityPlayer, int damage, double rad){
		
        int var1 = MathHelper.floor_double(par3BBox.minX + 0.001D);
        int var2 = MathHelper.floor_double(par3BBox.minY + 0.001D);
        int var3 = MathHelper.floor_double(par3BBox.minZ + 0.001D);
        int var4 = MathHelper.floor_double(par3BBox.maxX - 0.001D);
        int var5 = MathHelper.floor_double(par3BBox.maxY - 0.001D);
        int var6 = MathHelper.floor_double(par3BBox.maxZ - 0.001D);
        if (!par4World.checkChunksExist(var1, var2, var3, var4, var5, var6)){
        	return true;
        }
        
        List list = par4World.getEntitiesWithinAABBExcludingEntity(par5EntityPlayer, par3BBox);

        if( list.size() > 0){
			
			EntityLiving ent ;
			int count = 0;
			for(int i=0; i<list.size(); i++){
				if(list.get(i) instanceof EntityLiving)
					ent = (EntityLiving)list.get(i);
				else continue;
				if(!ent.canBeCollidedWith())
					continue;
				double var7 = par1Motion.motionX * rad;
				double var8 = par1Motion.motionY * rad;
				double var9 = par1Motion.motionZ * rad;
				//if(isEntityIn(par1Motion.posX,par1Motion.posY,par1Motion.posZ,ent)){
					count ++;
					ent.addVelocity(var7,var8,var9);
					ent.attackEntityFrom(DamageSource.causeMobDamage(par5EntityPlayer), damage);
					System.out.println("Damage applied: " + damage + "IsRemote: " + par4World.isRemote);
				//}
			}
			System.out.println("Int: " + count);
			return true;
			
		}
        return false;
		
	}
	
	
}
