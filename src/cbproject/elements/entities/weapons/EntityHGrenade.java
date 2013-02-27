package cbproject.elements.entities.weapons;


import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Mod.Item;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
        System.out.println("Entity has been inited.");
    }

    protected float getGravityVelocity()
    {
        return 0.025F;
    }
    
    protected float func_70182_d()
    {
    	return 0.7F;
    }
    
	public EntityHGrenade(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition par1)
	{    
	    if (!this.worldObj.isRemote)
	    {
	            this.setDead();
	    }
	    
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
		float var1=2.0F; //TNT的一半
	    for (int var3 = 0; var3 < 8; ++var3)
	    {
	            this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	    }    
	    System.out.println("Generated explosion");
		worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, var1, true);
		/*
		AxisAlignedBB par2 = null;
		par2.minX = posX-3;
		par2.minY = posY-3;
		par2.minZ = posZ-3;
		par2.maxX = posX+3;
		par2.maxY = posY+3;
		par2.maxZ = posZ+3;
		System.out.println("Inited AABB.");
		List entitylist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, par2);
		System.out.println("inited list.");
		if(entitylist.size() > 0){
			Entity ents[]=(Entity[])entitylist.toArray();
			for(int i=0;i<entitylist.size();i++){
				if(!ents[i].isEntityInvulnerable()){
					int damage = 
							(int) (Math.pow(
						( Math.pow(ents[i].posX-posX,2) + 
					      Math.pow(ents[i].posY-posY,2) + 
					      Math.pow(ents[i].posZ-posZ,2) ) , 0.33) 
					     *0.33 * 20) ;
					System.out.println("Damage is : " + damage);
					ents[i].attackEntityFrom(DamageSource.explosion , damage);
							
				}
			}
		}
		*/
		worldObj.playSound(posX,posY,posZ, "cbc.weapons.explode_a", 0.5F, (float) (Math.random() * 0.4F + 0.8F),true);
		this.setDead();
	}
	
	@Override
    public void onUpdate()
    {
        super.onUpdate();
        if(this.ticksExisted >= delay) //该爆炸了=w=
        {
        		Explode();
        }
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

	

}