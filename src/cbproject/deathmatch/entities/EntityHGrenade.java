package cbproject.deathmatch.entities;


import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Hand grenade entity class.
 * @author Administrator
 * 
 */
public class EntityHGrenade extends EntityProjectile {

	int delay;
	int time;
	
    public EntityHGrenade(World par1World, EntityLiving par2EntityLiving,int par3Fuse)
    {
        super(par1World,par2EntityLiving);
        delay = 60 - par3Fuse;
        time = 0;
        
    }

	@Override
	protected void onCollide(MovingObjectPosition par1)
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
	    		this.motionZ = 0.6*-motionZ;
	    		break;
	    		
	    	case 4:
	    	case 5:
	    		this.motionX = 0.6*-motionX;
	    		break;
	    		
	    	default:
	    		break;
	    	}
	    }
	}
	
	private void Explode(){
		BulletManager.Explode(worldObj,this, 3.0F, 3.5F, posX, posY, posZ, 35);
		this.setDead();
	}
	
	@Override
    public void onUpdate()
    {
        super.onUpdate();
        if(this.ticksExisted >= delay || this.isBurning()) //Time to explode >)
        		Explode();
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

	@Override
    protected float getGravityVelocity()
    {
        return 0.025F;
    }
    
	@Override
    protected float getHeadingVelocity()
    {
    	return 0.7F;
    }
    
    @Override
	protected float getMotionYOffset(){
    	return 0.0F;
    }

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {}
	
}
