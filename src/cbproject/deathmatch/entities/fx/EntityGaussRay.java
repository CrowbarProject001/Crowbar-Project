/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开原协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.deathmatch.entities.fx;

import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.entities.EntityBulletGauss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 高斯枪光束渲染的实用类。
 * @author WeAthFolD
 */
public class EntityGaussRay extends Entity {
	
	public EntityGaussRay(World par1World,EntityLiving ent, EntityBulletGauss gauss){
		super(par1World);
		this.setLocationAndAngles(gauss.posX, gauss.posY, gauss.posZ, ent.rotationYawHead, gauss.rotationPitch);
		this.setRayHeading(motionX, motionY, motionZ, 1.0F, 1.0F);
		ignoreFrustumCheck = true;
		
	}
	
	public EntityGaussRay(MotionXYZ begin, World par1World){
		
		super(par1World);
		this.posX = begin.posX;
		this.posY = begin.posY;
		this.posZ = begin.posZ;
		this.motionX = begin.motionX;
		this.motionY = begin.motionY;
		this.motionZ = begin.motionZ;
		this.setRayHeading(motionX, motionY, motionZ, 1.0F, 1.0F);
		ignoreFrustumCheck = true;
		
	}

	public EntityGaussRay(World world){
		super(world);
	}
	
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setRayHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)f2;
        par3 /= (double)f2;
        par5 /= (double)f2;
        par1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f3) * 180.0D / Math.PI);
    }
	@Override
	public void onUpdate(){
		if(this.ticksExisted > 4)
			this.setDead();
	}
	
	@Override
	protected void entityInit() {
		ignoreFrustumCheck = true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

}