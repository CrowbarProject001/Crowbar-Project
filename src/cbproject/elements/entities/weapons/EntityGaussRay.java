package cbproject.elements.entities.weapons;

import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGaussRay extends EntityThrowable {
	
	public EntityGaussRay(World par1World,EntityLiving ent, double moX, double moY, double moZ){
		
		super(par1World, ent);
		motionX = moX;
		motionY = moY;
		motionZ = moZ;
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
		ignoreFrustumCheck = true;
		
	}

	@Override
	public void onUpdate(){
		if(this.ticksExisted > 100) //存在0.5秒
			this.setDead();
	}
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected float func_70182_d(){
		return 0.0F;
	}

	@Override
	protected float getGravityVelocity(){
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {

	}

}
