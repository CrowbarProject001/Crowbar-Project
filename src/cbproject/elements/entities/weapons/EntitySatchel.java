package cbproject.elements.entities.weapons;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySatchel extends EntityThrowable {
	
	public static double HEIGHT = 0.083, WIDTH1 = 0.2, WIDTH2 = 0.15;
	public int tickHit = 0;
	public EntitySatchel(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public EntitySatchel(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
		// TODO Auto-generated constructor stub
	}

	public EntitySatchel(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition par1) {
		// TODO Auto-generated method stub

	    int id =  this.worldObj.getBlockId(par1.blockX,par1.blockY,par1.blockZ);
	    Boolean canCollide = !( id == Block.tallGrass.blockID || id == Block.reed.blockID 
	    		|| id == Block.plantRed.blockID
	    		|| id == Block.plantYellow.blockID);
	    
	    //碰撞代码
	    if(par1.typeOfHit == EnumMovingObjectType.TILE && canCollide)
	    //if(par1.typeOfHit == EnumMovingObjectType.TILE)
	    {
	    	
	    	switch(par1.sideHit){
	    	case 1:
	    		if(tickHit == 0)
	    			tickHit = ticksExisted;
	    	case 0:
	    		this.motionY = -motionY;
	    		break;
	    		
	    	case 2:
	    	case 3:
	    		this.motionZ = -motionZ;
	    		break;
	    		
	    	case 4:
	    	case 5:
	    		this.motionX = -motionX;
	    		break;
	    		
	    	default:
	    		break;
	    	}
	    	this.setThrowableHeading(this.motionX , this.motionY, this.motionZ, (float) (0.1*this.func_70182_d()), 1.0F);
	    }
	    if(par1.typeOfHit == EnumMovingObjectType.ENTITY)
	    	this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, (float) (0.3*this.func_70182_d()), 1.0F);
	    
	}
	
	public void Explode(){
		
		float var1=2.0F; //TNT的一半
	    for (int var3 = 0; var3 < 8; ++var3)
	    {
	            this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	    }    
	    
		worldObj.createExplosion( this, this.posX, this.posY, this.posZ, var1, true);
		
		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(posX-4, posY-4, posZ-4, posX+4, posY+4, posZ+4);
		List entitylist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, par2);
		System.out.println("inited list.");
		if(entitylist.size() > 0){
			for(int i=0;i<entitylist.size();i++){
				Entity ent = (Entity)entitylist.get(i);
				if(!ent.isEntityInvulnerable() && ent instanceof EntityLiving){
					int damage = 
							(int) (Math.pow(
						( Math.pow(ent.posX-posX,2) + 
					      Math.pow(ent.posY-posY,2) + 
					      Math.pow(ent.posZ-posZ,2) ) , 0.33) 
					     *0.33 * 25) ;
					
					if( ent instanceof EntityPlayer && ((EntityPlayer)ent).capabilities.isCreativeMode)
						return;
					

					ent.attackEntityFrom(DamageSource.explosion2, damage);
					ent.setFire(20);
							
				}
			}
		}
		this.setDead();
	}
	
    protected float getGravityVelocity()
    {
        return 0.025F;
    }
    
    protected float func_70182_d()
    {
    	return 0.7F;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(-WIDTH1, -HEIGHT, -WIDTH2, WIDTH1, HEIGHT, WIDTH2);
    }
    
}
