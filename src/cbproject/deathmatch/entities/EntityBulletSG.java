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
package cbproject.deathmatch.entities;

import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.utils.BulletManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * TODO:未完成
 * @author Administrator
 *
 */
public class EntityBulletSG extends EntityBullet {

	double startX, startY, startZ;
	
	public EntityBulletSG(World par1World, EntityLiving par2EntityLiving, ItemStack par3itemStack) {
		super(par1World, par2EntityLiving, par3itemStack);
		startX = par2EntityLiving.posX;
		startY = par2EntityLiving.posY;
		startZ = par2EntityLiving.posZ;
		this.motionX = motion.motionX;
		this.motionY = motion.motionY;
		this.motionZ = motion.motionZ;
	}

	public EntityBulletSG(World world) {
		super(world);
	}
	
	@Override
	protected void entityInit(){
	}
	
	@Override
	public void doBlockCollision(MovingObjectPosition result){
		System.out.println("hit block");
	}
	
	@Override
	public void doEntityCollision(MovingObjectPosition result){
		
		if( result.entityHit == null)
			return;
		if(!(result.entityHit instanceof EntityLiving || result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		//Distance related, max 22/44 in 0 distance, min 1 in distance 30, 0.1 chance of a critical hit( 100% damage)
		int damage = item.getDamage(item.getMode(itemStack));
		double critical = Math.random();
		if(critical > 0.1) {
			double distance = result.hitVec.distanceTo(Vec3.createVectorHelper(startX, startY, startZ));
			if(distance > 3.0D)
				damage *= 1/Math.sqrt(distance);
		}
		BulletManager.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), damage);
		
	}
	
	@Override
	public boolean canBeCollidedWith(){
		return false;
	}

}
