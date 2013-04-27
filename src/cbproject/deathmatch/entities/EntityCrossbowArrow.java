package cbproject.deathmatch.entities;

import java.util.List;

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
 * Crossbow arrow entity class.
 * @author WeAthFolD
 *
 */
public class EntityCrossbowArrow extends EntityThrowable {

	public EntityCrossbowArrow(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if(ticksExisted % 3 == 0)
			worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0, 0.0, 0.0);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		Explode(var1);	
	}
	
	private void Explode(MovingObjectPosition pos){
		
		float var1=0.75F;
		double dmg = 20.0F;
	    for (int var3 = 0; var3 < 8; ++var3)
	    {
	            this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	    }    
	    
		Explosion ex = worldObj.createExplosion(this, pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord, var1, true);
		
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
					
				
					ent.attackEntityFrom(DamageSource.setExplosionSource(ex), damage);
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
    	return 5.0F;
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

}
