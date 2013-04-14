package cbproject.elements.entities.weapons;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * RPG rocket entity.
 * @author WeAthFolD
 *
 */
public class EntityRocket extends EntityThrowable {

	public EntityRocket(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
		rotationPitch = par2EntityLiving.rotationPitch;
		rotationYaw = par2EntityLiving.rotationYaw;
		worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.5F, 1.0F);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if(ticksExisted % 2 == 0)
			worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0, 0.0, 0.0);
		if(ticksExisted % 45 == 0)
			worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.5F, 1.0F);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		Explode(var1);
	}
	
	private void Explode(MovingObjectPosition pos){
		
		float var1=6.0F; //手雷的3倍
		double dmg = 40.0F;
	    for (int var3 = 0; var3 < 8; ++var3)
	    {
	            this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	    }    
	    
		worldObj.createExplosion(this, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, var1, true);
		
		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(posX-4, posY-4, posZ-4, posX+4, posY+4, posZ+4);
		List entitylist = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, par2);
		if(entitylist.size() > 0){
			for(int i=0;i<entitylist.size();i++){
				Entity ent = (Entity)entitylist.get(i);
				if(!ent.isEntityInvulnerable() && ent instanceof EntityLiving){
					int damage = 
							(int) (Math.pow(
						( Math.pow(ent.posX-posX,2) + 
					      Math.pow(ent.posY-posY,2) + 
					      Math.pow(ent.posZ-posZ,2) ) , 0.5) 
					     /4 * dmg) ;
					
					if( ent instanceof EntityPlayer && ((EntityPlayer)ent).capabilities.isCreativeMode)
						return;
					
				
					ent.attackEntityFrom(DamageSource.explosion2, damage);
					ent.setFire(20);
							
				}
			}
		}
		this.setDead();
	}
	
	@Override
    protected float getGravityVelocity()
    {
        return 0.0F;
    }
    
	@Override
    protected float func_70182_d()
    {
    	return 2F;
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

}
