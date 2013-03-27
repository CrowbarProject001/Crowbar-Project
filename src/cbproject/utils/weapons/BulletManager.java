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
import net.minecraft.world.World;

public class BulletManager {


	private static final double BB_SIZE = 1.0D;
	
	public BulletManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void Shoot(EntityLiving par1EntityPlayer, World par2World, int par3damage, int par4offset, double par5Vel){
		
		MotionXYZ motion;
		Boolean isHit;
		AxisAlignedBB bBox;
		World worldObj;
		EntityLiving entityPlayer;
		int damage, offset;
		
		if(!par2World.isRemote){
			//System.out.println("World isnt remote,return.");
			return;
		}
		
		motion = new MotionXYZ(par1EntityPlayer, par4offset);
		worldObj = par2World;
		damage = par3damage;
		offset = par4offset;
		entityPlayer = par1EntityPlayer;
		isHit = false;
		
		//System.out.println("Motion init.");
		par2World.spawnParticle("smoke", motion.posX, motion.posY, motion.posZ, motion.motionX, motion.motionY, motion.motionZ);

		double var1 = 1.0D,var2 = 1.0D,var3 = 1.0D;
		bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
				motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
		
		int  times = 0;
		while(!isHit && times < 200){
			
			times++;
			this.doEntityCollision(motion, isHit, bBox, worldObj, par1EntityPlayer, times, par5Vel);
			this.doBlockCollision(motion, isHit, bBox, worldObj);
			
			motion.updateMotion(2.0);
			bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
					motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
			
		}
			
	}
	
	private Boolean isInBlock(double x,double y, double z, BlockPos block){
		return (block.x < x && x <block.x+1 && block.y < y && y <block.y+1 && block.z < z && z <block.z+1);
	}
	
	private void doBlockCollision(MotionXYZ par1Motion, Boolean par2IsHit, AxisAlignedBB par3BBox, World par4World){
		
		if(par2IsHit)
			return;
        
		List list =	par1Motion.getBlocksWithinAABB(par3BBox, par4World);
		if( list.size() > 0){
			
			BlockPos block;
			for(int i=0; i<list.size(); i++){
				block = (BlockPos)list.get(i);
				if(isInBlock(par1Motion.posX, par1Motion.posY, par1Motion.posZ, block)){
					if(block.blockID != 1)
						par2IsHit = true;
					System.out.println("Hitted a block at : " + block.x + " " + block.y + " " + block.z + ", ID : " + block.blockID);
				}
			}
			//Particle,sound effect
		}

	}
	
	private void doEntityCollision(MotionXYZ par1Motion, Boolean par2IsHit, AxisAlignedBB par3BBox, World par4World,
			EntityLiving par5EntityPlayer, int damage, double rad){
		
        int var1 = MathHelper.floor_double(par3BBox.minX + 0.001D);
        int var2 = MathHelper.floor_double(par3BBox.minY + 0.001D);
        int var3 = MathHelper.floor_double(par3BBox.minZ + 0.001D);
        int var4 = MathHelper.floor_double(par3BBox.maxX - 0.001D);
        int var5 = MathHelper.floor_double(par3BBox.maxY - 0.001D);
        int var6 = MathHelper.floor_double(par3BBox.maxZ - 0.001D);
        if (!par4World.checkChunksExist(var1, var2, var3, var4, var5, var6)){
        	par2IsHit = true;
        	return;
        }
        
        List list = par4World.getEntitiesWithinAABBExcludingEntity(par5EntityPlayer, par3BBox);
		if( list.size() > 0){
			
			EntityLiving ent ;
			for(int i=0; i<list.size(); i++){
				if(list.get(i) instanceof EntityLiving)
					ent = (EntityLiving)list.get(i);
				else continue;
				if(!ent.canBeCollidedWith())
					continue;
				par2IsHit = true;
					double var7 = par1Motion.motionX * rad;
					double var8 = par1Motion.motionY * rad;
					double var9 = par1Motion.motionZ * rad;
					System.out.println("Bullet hitted a entity.At : " + ent.posX + " " + ent.posY + " " + ent.posZ);
					ent.addVelocity(var7,var8,var9);
					if(par5EntityPlayer instanceof EntityPlayer)
						ent.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)par5EntityPlayer), damage);
					else ent.attackEntityFrom(DamageSource.causeMobDamage(par5EntityPlayer), damage);
					if(ent.getHealth() <= 0)
						ent.setEntityHealth(0);

			}
			return;
			
		}
		
		
	}
	
	
}
