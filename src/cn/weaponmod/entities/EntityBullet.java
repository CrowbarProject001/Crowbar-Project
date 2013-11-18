/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cn.liutils.api.util.GenericUtils;
import cn.liutils.api.util.Motion3D;
import cn.weaponmod.api.weapon.WeaponGeneral;

/**
 * @author WeAthFolD
 *
 */
public class EntityBullet extends EntityThrowable {

	protected Motion3D motion;
	protected int damage;
	public boolean renderFromLeft = false;
	
	public EntityBullet(World par1World, EntityLivingBase par2EntityLiving, ItemStack itemStack, boolean left) {

		super(par1World, par2EntityLiving);

		if (!(itemStack.getItem() instanceof WeaponGeneral))
			this.setDead();
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		double f1 = par1World.isRemote ? 0.06 : 0.3;
		motion = new Motion3D(this).setMotionOffset(f1 * item.getOffset(left));
		this.motionX = motion.motionX;
		this.motionY = motion.motionY;
		this.motionZ = motion.motionZ;
		boolean mode = left;
		this.damage = item.getWeaponDamage(mode);
	}
	
	public EntityBullet setRenderFromLeft() {
		renderFromLeft = true;
		return this;
	}
	
	public EntityBullet(World par1World, EntityLivingBase par2EntityLiving, int dmg) {
		super(par1World, par2EntityLiving);
		this.rotationYaw = GenericUtils.wrapYawAngle(-par2EntityLiving.rotationYawHead);
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.4F;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.4F;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * 0.4F;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
		this.motion = new Motion3D(this);
		this.damage = dmg;
	}
	
	public EntityBullet(World par1World, EntityLivingBase ent, Entity target, int dmg) {
		super(par1World, ent);
		motionX = target.posX  - ent.posX;
		motionY = (target.posY + target.height / 2.0) - ent.posY - ent.getEyeHeight();
		motionZ = target.posZ - ent.posZ;	
		this.setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
		damage = dmg;
		motion = new Motion3D(this);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted > 50)
			this.setDead();
	}

	@Override
	protected void entityInit() {
	}

	public EntityBullet(World world) {
		super(world);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition par1) {
		switch (par1.typeOfHit) {
		case TILE:
			doBlockCollision(par1);
			break;
		case ENTITY:
			doEntityCollision(par1);
			break;
		}
	}

	protected void doBlockCollision(MovingObjectPosition result) {
		int blockid = worldObj.getBlockId(result.blockX, result.blockY, result.blockZ);
		if(!worldObj.isRemote && (blockid == Block.glass.blockID || blockid == Block.thinGlass.blockID)) {
			worldObj.destroyBlock(result.blockX, result.blockY, result.blockZ, false);
		}
		this.setDead();
	}

	public void doEntityCollision(MovingObjectPosition result) {
		if (result.entityHit == null)
			return;
		result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damage);
		result.entityHit.hurtResistantTime = -1;

	}

	public int getBulletDamage(int mode) {
		return damage;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	protected float func_70182_d() {
		return worldObj.isRemote ? 3.0F : 15.0F;
	}


}
