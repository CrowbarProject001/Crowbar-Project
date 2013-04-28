package cbproject.deathmatch.entities;

import java.util.List;

import cbproject.deathmatch.utils.BulletManager;

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
		BulletManager.Explode(worldObj, 1.0F, 3.0F, posX, posY, posZ, 30);
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
