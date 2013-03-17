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

	public BulletManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void Shoot(EntityLiving par1EntityPlayer, World par2World, int damage, int offset){

		MotionXYZ motion = new MotionXYZ(par1EntityPlayer, offset);
		System.out.println(motion.toString());
		Boolean isHit = false;
		
		double var1 = 0.5;
		AxisAlignedBB bBox = AxisAlignedBB.getBoundingBox( motion.posX -var1, motion.posY - var1, motion.posZ -var1, motion.posX + var1, motion.posY +var1, motion.posZ + var1);
		int  times = 0;
		while(!isHit && times < 100){
			times++;
			
			List list =	motion.getBlocksWithinAABB(bBox, par2World);
			if( list.size() > 0){
				isHit = true;
				System.out.println("Bullet hitted a block.");
				BlockPos block;
				for(int i=0; i<list.size(); i++){
					block = (BlockPos)list.get(i);
					System.out.println("At : " + block.x + " " + block.y + " " + block.z + ", ID : " + block.blockID);
				}
				//Particle,sound effect
			}
					
			bBox.expand(1,1,1);
			list = par2World.getEntitiesWithinAABBExcludingEntity(par1EntityPlayer, bBox);
			if( list.size() > 0){
				isHit = true;
				System.out.println("Bullet hitted a entity .");
				
				Entity ent;
				for(int i=0; i<list.size(); i++){
					ent = (Entity)list.get(i);
					System.out.println("At : " + ent.posX + " " + ent.posY + " " + ent.posZ);
					if(!(ent instanceof EntityLiving))
						continue;
					if(par2World.isRemote){	
						World world = ent.worldObj;
						ent.worldObj = par2World;
						ent.attackEntityFrom(DamageSource.generic, damage);
						ent.worldObj = world;
					}
				}
				//Particle, sound effect
				return;
			}
			
			motion.updateMotion(1.0);
			bBox = AxisAlignedBB.getBoundingBox( motion.posX - var1, motion.posY - var1, motion.posZ -var1, motion.posX + var1, motion.posY + var1, motion.posZ + var1);
		}
	}
	
}
