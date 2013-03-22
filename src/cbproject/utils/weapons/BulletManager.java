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

	MotionXYZ motion;
	Boolean isHit;
	AxisAlignedBB bBox;
	World worldObj;
	EntityLiving entityPlayer;
	int damage,offset;
	private static final double BB_SIZE = 1.0D;
	
	public BulletManager() {
		motion = null;
		isHit = false;
		bBox = null;
		// TODO Auto-generated constructor stub
	}
	
	public void Shoot(EntityLiving par1EntityPlayer, World par2World, int par3damage, int par4offset){
		
		if(!par2World.isRemote){
			//System.out.println("World isnt remote,return.");
			return;
		}
		motion = new MotionXYZ(par1EntityPlayer, offset);
		worldObj = par2World;
		damage = par3damage;
		offset = par4offset;
		entityPlayer = par1EntityPlayer;
		isHit = false;
		
		//System.out.println("Motion init.");
		par2World.spawnParticle("smoke", motion.posX, motion.posY, motion.posZ, motion.motionX, motion.motionY, motion.motionZ);

		//double var1 = motion.motionX +0.1,var2 = motion.motionY + 0.1,var3 = motion.motionZ + 0.1;
		double var1 = 1.0D,var2 = 1.0D,var3 = 1.0D;
		bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
				motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
		
		int  times = 0;
		while(!isHit && times < 200){
			
			times++;
			this.doEntityCollision();
			this.doBlockCollision();
			
			motion.updateMotion(1.0);
			bBox = AxisAlignedBB.getBoundingBox( motion.posX -BB_SIZE , motion.posY - BB_SIZE,
					motion.posZ -BB_SIZE, motion.posX + BB_SIZE, motion.posY +BB_SIZE, motion.posZ + BB_SIZE);
			
		}
		//System.out.println("quitting from here.");
			
	}
	
	private Boolean isInBlock(double x,double y, double z, BlockPos block){
		return (block.x < x && x <block.x+1 && block.y < y && y <block.y+1 && block.z < z && z <block.z+1);
	}
	
	private void doBlockCollision(){
		
		if(isHit)
			return;
        
		List list =	motion.getBlocksWithinAABB(bBox, worldObj);
		if( list.size() > 0){
			
			BlockPos block;
			for(int i=0; i<list.size(); i++){
				block = (BlockPos)list.get(i);
				if(isInBlock(motion.posX, motion.posY, motion.posZ, block)){
					isHit = true;
					System.out.println("Hitted a block at : " + block.x + " " + block.y + " " + block.z + ", ID : " + block.blockID);
				}
			}
			//Particle,sound effect
		}

	}
	
	private void doEntityCollision(){
		
        int var1 = MathHelper.floor_double(bBox.minX + 0.001D);
        int var2 = MathHelper.floor_double(bBox.minY + 0.001D);
        int var3 = MathHelper.floor_double(bBox.minZ + 0.001D);
        int var4 = MathHelper.floor_double(bBox.maxX - 0.001D);
        int var5 = MathHelper.floor_double(bBox.maxY - 0.001D);
        int var6 = MathHelper.floor_double(bBox.maxZ - 0.001D);
        if (!this.worldObj.checkChunksExist(var1, var2, var3, var4, var5, var6)){
        	isHit = true;
        	return;
        }
        
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entityPlayer, bBox);
		if( list.size() > 0){
			
			Entity ent;
			for(int i=0; i<list.size(); i++){
				ent = (Entity)list.get(i);
				if(!ent.canBeCollidedWith())
					continue;
				isHit = true;
				
					System.out.println("Bullet hitted a entity.At : " + ent.posX + " " + ent.posY + " " + ent.posZ);
					//World world = ent.worldObj;
					//ent.worldObj = worldObj;
					ent.attackEntityFrom(DamageSource.explosion2, damage);
					//ent.worldObj = world;
			}
			return;
			
		}
		
		
	}
	
	
}
