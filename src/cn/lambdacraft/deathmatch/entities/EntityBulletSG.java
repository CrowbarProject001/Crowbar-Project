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

import cn.lambdacraft.api.weapon.WeaponGeneral;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 
 * @author Administrator
 * 
 */
public class EntityBulletSG extends EntityBullet {


	public EntityBulletSG(World par1World, EntityPlayer par2EntityLiving,
			ItemStack par3itemStack) {
		super(par1World, par2EntityLiving, par3itemStack);
	}

	public EntityBulletSG(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void doEntityCollision(MovingObjectPosition result) {

		if (result.entityHit == null)
			return;
		if (!(result.entityHit instanceof EntityLiving
				|| result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		doEntityCollision(result.entityHit);
	}

	protected void doEntityCollision(Entity ent) {
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		int damage = item.getDamage(item.getMode(itemStack));
		ent.hurtResistantTime = -1;
		BulletManager.doEntityAttack(ent, DamageSource.causeMobDamage(getThrower()), damage);
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

}
