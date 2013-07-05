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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.entities;

import cn.lambdacraft.deathmatch.utils.BulletManager;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 十字弩的爆炸弩箭实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntityCrossbowArrow extends EntityThrowable {

	public boolean isExplosive;
	public boolean still = false;
	public int damage;
	
	public EntityCrossbowArrow(World par1World, EntityLiving par2EntityLiving, boolean explode) {
		super(par1World, par2EntityLiving);
		isExplosive = explode;
		damage = 20;
	}
	
	public EntityCrossbowArrow(World world, double posX, double posY, double posZ, float yaw, float pitch) {
		super(world);
		this.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		still = true;
	}

	public EntityCrossbowArrow(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {
		if(still)
			return;
		super.onUpdate();
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		if(still)
			return;
		if(!worldObj.isRemote) {
			if(isExplosive)
				Explode();
			else {
				if(var1.typeOfHit == EnumMovingObjectType.ENTITY) {
					if(damage <= 3)
						setDead();
					BulletManager.doEntityAttack(var1.entityHit, DamageSource.causeMobDamage(getThrower()), damage);
					damage *= 0.6;
				}
				else {
					worldObj.spawnEntityInWorld(
							new EntityCrossbowArrow(worldObj, var1.hitVec.xCoord, var1.hitVec.yCoord, var1.hitVec.zCoord,
									this.rotationYaw, this.rotationPitch));
				}
			}
		}
	}

	private void Explode() {
		BulletManager.Explode(worldObj, this, 1.0F, 3.0F, posX, posY, posZ, 30);
		this.setDead();
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected float func_70182_d() {
		return still ? 0.0F : 5.0F;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

}
