package cbproject.utils.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.utils.BlockPos;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MotionXYZ {
	public double motionX,motionY,motionZ;
	public double posX, posY, posZ;
	public static final double SCALE = 0.01D;
	
	public MotionXYZ(double par1,double par2,double par3, double par4, double par5, double par6) {
		// TODO Auto-generated constructor stub
		posX = par1;
		posY = par2;
		posZ = par3;
		
		motionX = par4;
		motionY = par5;
		motionZ = par6;
	}
	
	public MotionXYZ(Vec3 par0, double par4, double par5, double par6) {
		// TODO Auto-generated constructor stub
		posX = par0.xCoord;
		posY = par0.yCoord;
		posZ = par0.zCoord;
		
		motionX = par4;
		motionY = par5;
		motionZ = par6;
	}
	
	public MotionXYZ(MotionXYZ a){
		
		posX = a.posX;
		posY = a.posY;
		posZ = a.posZ;
		motionX = a.motionX;
		motionY = a.motionY;
		motionZ = a.motionZ;
		
	}
	
	public MotionXYZ(Entity ent){
		this(ent, 0);
	}

	public MotionXYZ(Entity par1Player, int par2){
		if(par1Player instanceof EntityLiving)
			getPosByPlayer((EntityLiving) par1Player);
		else { 
			
			posX = par1Player.posX;
			posY = par1Player.posY;
			posZ = par1Player.posZ;
			motionX = par1Player.motionX;
			motionY = par1Player.motionY;
			motionZ = par1Player.motionZ;
			
		}
		
		setOffset(par2);
	
	}
	
	public Vec3 asVec3(World world){
		return world.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
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
		
		this.motionX += (Math.random() - 1) * 2 * par1 * SCALE ;
		this.motionY += (Math.random() - 1) * 2 * par1 * SCALE;
		this.motionZ += (Math.random() - 1) * 2 * par1 * SCALE;
		
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
					//System.out.println("Attempting at " + i + " " + a + " " + b);
					int id = world.getBlockId(i, a, b);
					if(id != 0){
						list.add(new BlockPos(i, a, b, id));
						//System.out.println("Block Get!> <");
					}
					
				}
			}
		}
		return list;
		
	}
		
	public MotionXYZ updateMotion(double scale){
		
		posX += motionX * scale;
		posY += motionY * scale;
		posZ += motionZ * scale;
		return this;
		
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
	
	public static final MotionXYZ getPosByPlayer2(EntityLiving par1Player){
		return new MotionXYZ(par1Player,0);
	}
	

	
	
	
}
