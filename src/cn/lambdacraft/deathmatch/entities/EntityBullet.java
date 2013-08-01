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

import cn.lambdacraft.api.weapon.InformationWeapon;
import cn.lambdacraft.api.weapon.WeaponGeneral;
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.deathmatch.utils.BulletManager;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Bullet entity class which handles all bullet weapons.
 * 
 * @author WeAthFolD
 * 
 */
public class EntityBullet extends EntityThrowable {

	protected MotionXYZ motion;
	protected double pushForce;
	protected int damage;
	
	public EntityBullet(World par1World, EntityLiving par2EntityLiving,
			ItemStack itemStack) {

		super(par1World, par2EntityLiving);

		if (!(itemStack.getItem() instanceof WeaponGeneral))
			this.setDead();
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		double f1 = par1World.isRemote ? 0.06 : 0.3;
		motion = new MotionXYZ(this).setMotionOffset(f1 * item.getOffset(item
				.getMode(itemStack)));
		this.motionX = motion.motionX;
		this.motionY = motion.motionY;
		this.motionZ = motion.motionZ;
		int mode = item.getMode(itemStack);
		this.damage = item.getDamage(mode);
		this.pushForce = item.getPushForce(mode);
	}
	
	public EntityBullet(World par1World, EntityLiving par2EntityLiving,
			int dmg) {
		super(par1World, par2EntityLiving);
		this.rotationYaw = GenericUtils.wrapYawAngle(-par2EntityLiving.rotationYawHead);
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.4F);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.4F);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * 0.4F);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
		this.motion = new MotionXYZ(this);
		this.damage = dmg;
	}
	
	public EntityBullet(World par1World, EntityLiving ent, Entity target, int dmg) {
		super(par1World, ent);
		motionX = target.posX  - ent.posX;
		motionY = (target.posY + target.height / 2.0) - ent.posY - ent.getEyeHeight();
		motionZ = target.posZ - ent.posZ;	
		this.setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
		damage = dmg;
		motion = new MotionXYZ(this);
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
		double pf = pushForce * 0.1;
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ
				* pf;
		BulletManager.doEntityAttack(result.entityHit,
				DamageSource.causeMobDamage(getThrower()),
				damage, dx, dy, dz);

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
