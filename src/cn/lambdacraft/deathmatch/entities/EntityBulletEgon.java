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

import java.util.List;

import cn.lambdacraft.api.weapon.InformationWeapon;
import cn.lambdacraft.api.weapon.WeaponGeneral;
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.deathmatch.utils.BulletManager;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 随机破坏方块+范围攻击
 * @author WeAthFolD
 */
public class EntityBulletEgon extends EntityBullet {

	public EntityBulletEgon(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack) {
		super(par1World, par2EntityLiving, par3itemStack);
	}

	public EntityBulletEgon(World world) {
		super(world);
	}
	
	protected void doBlockCollision(MovingObjectPosition result) {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(result.hitVec.xCoord - 2, result.hitVec.yCoord - 2, result.hitVec.zCoord - 2,
				result.hitVec.xCoord + 2, result.hitVec.yCoord + 2, result.hitVec.zCoord + 2);
		List<Entity> ents = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, GenericUtils.selectorLiving);
		for(Entity e : ents) {
			double distance = e.getDistance(result.hitVec.xCoord, result.hitVec.yCoord, result.hitVec.zCoord);
			int damage = (int) (7.0 / distance);
			e.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damage);
		}
		if(Math.random() < 0.1)  {
			int blockID = worldObj.getBlockId(result.blockX, result.blockY, result.blockZ);
			//检查以防破坏基岩等重要方块
			if(Block.blocksList[blockID] != null) {
				float hardness = Block.blocksList[blockID].getBlockHardness(worldObj, result.blockX, result.blockY, result.blockZ);
				if(hardness > 0.0F && hardness < 5.0F)
					worldObj.destroyBlock(result.blockX, result.blockY, result.blockZ, false);
			} else worldObj.setBlock(result.blockX, result.blockY, result.blockZ, 0, 0, 3);
		}
		this.setDead();
	}
	
	public void doEntityCollision(MovingObjectPosition result) {
		if(result != null)
			System.out.println(" " + result.entityHit);
		else System.out.println();
		if (result.entityHit == null)
			return;
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationWeapon inf = item.getInformation(itemStack, worldObj);
		int mode = item.getMode(itemStack);
		double pf = item.getPushForce(mode) * 0.1;
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ
				* pf;
		BulletManager.doEntityAttack(result.entityHit,
				DamageSource.causeMobDamage(getThrower()),
				item.getDamage(mode), dx, dy, dz);

	}

}
