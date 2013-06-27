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

import java.util.List;

import cn.lambdacraft.api.entities.IEntityLink;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.core.utils.GenericUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class EntityHeadcrab extends EntityMob {

	public static final float MOVE_SPEED = 0.5F;
	public int lastJumpTick = 0;

	public EntityHeadcrab(World par1World) {
		super(par1World);
		this.texture = ClientProps.HEADCRAB_MOB_PATH;
		this.setSize(0.6F, 0.3F);
		this.moveSpeed = MOVE_SPEED;
		this.experienceValue = 5;
	}

	@Override
	public int getMaxHealth() {
		return 10;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		int i = this.getAttackStrength(par1Entity);
		if(this.onGround || this.hurtResistantTime > 0)
			return false;
		boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
		this.motionX = 0.0;
		this.motionZ = 0.0;
		return flag;
	}

	/**
	 * 头蟹掉落不受伤害=w=
	 */
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (par1DamageSource == DamageSource.fall
				|| par1DamageSource == DamageSource.fallingBlock) {
			return false;
		} else if (super.attackEntityFrom(par1DamageSource, par2)) {
			Entity entity = par1DamageSource.getEntity();
			if (entity != this) {
				this.entityToAttack = entity;
			}
	        this.motionX += (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.2F);
	        this.motionZ += (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.2F);
	        
			return true;
		}
		return false;
	}

	/**
	 * 自定义的寻路函数，
	 */
	@Override
	protected Entity findPlayerToAttack() {
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(posX - 8.0,
				posY - 8.0, posZ - 8.0, posX + 8.0, posY + 8.0, posZ + 8.0);
		List<EntityLiving> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox,
						GenericUtils.selectorLiving);
		EntityLiving entity = null;
		double distance = 10000.0F;
		for (EntityLiving s : list) {
			double dx = s.posX - posX, dy = s.posY - posY, dz = s.posZ - posZ;
			double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
			if (d < distance) {
				entity = s;
				distance = d;
			}
		}
		if (entity == null)
			return null;
		return entity;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return GenericUtils.getRandomSound("cbc.mobs.hc_idle", 5);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return GenericUtils.getRandomSound("cbc.mobs.hc_pain", 3);
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return GenericUtils.getRandomSound("cbc.mobs.hc_die", 2);
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		if (par2 > 2.0F && par2 < 6.0F && this.rand.nextInt(10) == 0) {
			if (this.onGround && ticksExisted - lastJumpTick > 30) {
				double d0 = par1Entity.posX - this.posX;
				double d1 = par1Entity.posZ - this.posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				this.motionX = d0 / f2 * 1.0 + this.motionX * 0.20;
				this.motionZ = d1 / f2 * 1.0 + this.motionZ * 0.20;
				this.motionY = 0.50;
				lastJumpTick = ticksExisted;
				this.playSound(GenericUtils.getRandomSound("cbc.mobs.hc_attack", 3), 0.5F, 1.0F);
			}
		} else {
			super.attackEntity(par1Entity, par2);
		}
	}
	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId() {
		return 0;
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity
	 * has recently been hit by a player. @param par2 - Level of Looting used to
	 * kill this mob.
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2) {
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
		return par1PotionEffect.getPotionID() == Potion.poison.id ? false
				: super.isPotionApplicable(par1PotionEffect);
	}

	/**
	 * Initialize this creature.
	 */
	@Override
	public void initCreature() {
		this.playSound(GenericUtils.getRandomSound("cbc.mobs.hc_idle", 5), 0.5F, 1.0F);
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	@Override
	public int getAttackStrength(Entity par1Entity) {
		return 16;
	}
}
