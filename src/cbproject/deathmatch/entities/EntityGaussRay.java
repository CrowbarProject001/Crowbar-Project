package cbproject.deathmatch.entities;

import cbproject.core.utils.MotionXYZ;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Gauss ray rendering entity.
 * @author WeAthFolD
 *
 */
public class EntityGaussRay extends EntityThrowable {
	
	public EntityGaussRay(World par1World,EntityLiving ent, EntityBulletGauss gauss){
		
		super(par1World, ent);
		this.setLocationAndAngles(gauss.posX, gauss.posY, gauss.posZ, ent.rotationYawHead, gauss.rotationPitch);
		this.setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
		ignoreFrustumCheck = true;
		
	}
	
	public EntityGaussRay(MotionXYZ begin, World par1World, EntityLiving ent){
		
		super(par1World, ent);
		this.posX = begin.posX;
		this.posY = begin.posY;
		this.posZ = begin.posZ;
		this.motionX = begin.motionX;
		this.motionY = begin.motionY;
		this.motionZ = begin.motionZ;
		this.setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
		ignoreFrustumCheck = true;
		
	}

	public EntityGaussRay(World world, EntityLiving ent){
		super(world, ent);
		ignoreFrustumCheck = true;
	}
	
	@Override
	public void onUpdate(){
		if(this.ticksExisted > 4) //存在0.5秒
			this.setDead();
	}
	
	@Override
	protected void entityInit() {

	}

	@Override
	protected float func_70182_d(){
		return 1.0F;
	}

	@Override
	protected float getGravityVelocity(){
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {

	}

}
