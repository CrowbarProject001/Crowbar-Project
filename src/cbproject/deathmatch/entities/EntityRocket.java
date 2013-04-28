package cbproject.deathmatch.entities;

import java.util.List;

import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
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
		BulletManager.Explode(worldObj, 6.0F, 7.0F, posX, posY, posZ, 40);
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
