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
package cn.lambdacraft.mob.entities;

import cn.lambdacraft.deathmatch.utils.BulletManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class EntityLightBall extends EntityThrowable {

	protected int damage = 5;
	
	/**
	 * @param par1World
	 */
	public EntityLightBall(World par1World) {
		super(par1World);
	}

	/**
	 * @param par1World
	 * @param par2EntityLiving
	 */
	public EntityLightBall(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.setSize(0.5F, 0.5F);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.projectile.EntityThrowable#onImpact(net.minecraft.util.MovingObjectPosition)
	 */
	@Override
	protected void onImpact(MovingObjectPosition result) {
		if(result.typeOfHit == EnumMovingObjectType.ENTITY) {
			BulletManager.doEntityAttack(result.entityHit, DamageSource.causeThornsDamage(this), damage);
		}
		this.setDead();
	}
	
	@Override
	protected float func_70182_d() {
		return 3.0F;
	}

}
