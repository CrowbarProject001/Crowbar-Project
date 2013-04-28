package cbproject.deathmatch.entities;


import java.util.List;

import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/**
 * Hand grenade entity class.
 * @author Administrator
 * 
 */
public class EntityHGrenade extends EntityThrowable {

	int delay;
	int time;
	
	public EntityHGrenade(World par1World,int fuse) {
		super(par1World);
		delay = (int) (60 - fuse);
		time = 0;
	}
	
    public EntityHGrenade(World par1World, EntityLiving par2EntityLiving,int par3Fuse)
    {
        super(par1World,par2EntityLiving);
        delay = 60 - par3Fuse;
        time = 0;
        
    }

    
	public EntityHGrenade(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition par1)
	{    

	    if(ticksExisted - time > 5){ //最小时间间隔0.3s
	    	worldObj.playSound(posX,posY,posZ, "cbc.weapons.hgrenadebounce", 0.5F, (float) (Math.random() * 0.4F + 0.8F),true);
	    	time = ticksExisted;
	    }
	    

	    int id =  this.worldObj.getBlockId(par1.blockX,par1.blockY,par1.blockZ);
	    Boolean canCollide = !( id == Block.tallGrass.blockID || id == Block.reed.blockID 
	    		|| id == Block.plantRed.blockID
	    		|| id == Block.plantYellow.blockID);
	    
	    //碰撞代码
	    if(par1.typeOfHit == EnumMovingObjectType.TILE && canCollide)
	    //if(par1.typeOfHit == EnumMovingObjectType.TILE)
	    {
	    	
	    	switch(par1.sideHit){
	    	
	    	case 0:
	    	case 1:
	    		this.motionY = 0.3*-motionY;
	    		this.motionX = 0.6 * motionX;
	    		this.motionZ = 0.6 * motionZ;
	    		break;
	    		
	    	case 2:
	    	case 3:
	    		this.motionZ = 0.7*-motionZ;
	    		break;
	    		
	    	case 4:
	    	case 5:
	    		this.motionX = 0.7*-motionX;
	    		break;
	    		
	    	default:
	    		break;
	    	}
	    	this.setThrowableHeading(this.motionX , this.motionY, this.motionZ, (float) (0.3*this.func_70182_d()), 1.0F);
	    }
	    if(par1.typeOfHit == EnumMovingObjectType.ENTITY)
	    	this.setThrowableHeading(this.motionX, this.motionY *= 0.5, this.motionZ, (float) (0.15*this.func_70182_d()), 1.0F);
	}
	
	private void Explode(){
		
		BulletManager.Explode(worldObj, 2.5F, 3.5F, posX, posY, posZ, 30);
		this.setDead();
		
	}
	
	@Override
    public void onUpdate()
    {
        super.onUpdate();
        if(this.ticksExisted >= delay) //Time to explode >)
        		Explode();
        
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

    protected float getGravityVelocity()
    {
        return 0.025F;
    }
    
    protected float func_70182_d()
    {
    	return 0.7F;
    }
	
}
