package cbproject.utils.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.utils.BlockPos;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MotionXYZ {
	public double motionX,motionY,motionZ;
	public double posX, posY, posZ;
	
	
	public MotionXYZ(double par1,double par2,double par3, double par4, double par5, double par6) {
		// TODO Auto-generated constructor stub
		posX = par1;
		posY = par2;
		posZ = par3;
		
		motionX = par4;
		motionY = par5;
		motionZ = par6;
	}

	public MotionXYZ(EntityLiving par1Player, int par2){
		
		getPosByPlayer(par1Player);
		setOffset(par2);
	}
	
	public String toString(){
		
		return "Motion Information : \n" +
				"posX : " + posX + "\n" +
				"posY : " + posY + "\n" +
				"posZ : " + posZ + "\n" +
				"motionX : " +motionX + "\n" +
				"motionY : " +motionY + "\n" +
				"motionZ : " + motionZ + "\n" ;
		
	}
	
	public void setOffset(double par1){
		
		this.motionX += (Math.random() - 1) * 2 * par1 ;
		this.motionY += (Math.random() - 1) * 2 * par1 ;
		this.motionZ += (Math.random() - 1) * 2 * par1 ;
		
	}
	
	/*
	 * Returns Blocks ID along with their coords.
	 * Returns:List containg BlockPos
	 */
	public List getBlocksWithinAABB(AxisAlignedBB bound, World world){
		
		List list = new ArrayList();
		for(int i = (int) bound.minX ; i< Math.round(bound.maxX) ; i++){
			for(int a = (int)bound.minY; a < Math.round(bound.maxY); a++){
				for(int b= (int)bound.minZ; b< Math.round(bound.maxZ); b++){
					System.out.println("Attempting at " + i + " " + a + " " + b);
					int id = world.getBlockId(i, a, b);
					if(id != 0){
						list.add(new BlockPos(i, a, b, id));
						System.out.println("Block Get!> <");
					}
					
				}
			}
		}
		return list;
		
	}
		
	public void updateMotion(double scale){
		//System.out.println("Updated motion once.");
		posX += motionX * scale;
		posY += motionY * scale;
		posZ += motionZ * scale;
	}
	
	
	private void getPosByPlayer(EntityLiving par1Player){
		
		posX = par1Player.posX;
		posY = par1Player.posY;
		posZ = par1Player.posZ;
		
		float var3 = 1.0F, var4 = 0.0F;
		
        this.motionX = (double)(-MathHelper.sin(par1Player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(par1Player.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionZ = (double)( MathHelper.cos(par1Player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(par1Player.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionY = (double)( -MathHelper.sin((par1Player.rotationPitch + var4)/180.0F *(float)Math.PI) * var3 );
	}
	
	
	
}
