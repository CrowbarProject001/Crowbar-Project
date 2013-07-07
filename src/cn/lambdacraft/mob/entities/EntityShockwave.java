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

import cn.lambdacraft.core.utils.GenericUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * 狗眼冲击波。
 * @author WeAthFolD
 */
public class EntityShockwave extends Entity {

	public static final float DAMAGE_SCALE = 15.0F;
	
	/**
	 * @param par1World
	 */
	public EntityShockwave(World par1World, double x, double y, double z) {
		super(par1World);
		this.setSize(8.0F, 0.6F);
		this.setPosition(x, y, z);
	}
	
	@Override
	public void onUpdate() {
		++this.ticksExisted;
		if(!worldObj.isRemote) {
			if(ticksExisted > 5) {
				this.attemptEntityAttack();
				this.setDead();
				return;
			}
		}
	}
	
	private void attemptEntityAttack() {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - 4.0, posY - 1.0, posZ - 4.0, posX + 4.0,  posY + 1.0, posZ - 1.0);
		List<EntityLiving> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, GenericUtils.selectorLiving);
		for(EntityLiving e : list) {
			double distanceSq = e.getDistanceSqToEntity(this);
			int dmg = (int) Math.round((33.0 - distanceSq) * DAMAGE_SCALE / 33.0);
			e.attackEntityFrom(DamageSource.generic, dmg);
			e.addPotionEffect(new PotionEffect(Potion.confusion.id,100,0));
		}
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		posX = nbt.getDouble("posX");
		posY = nbt.getDouble("posY");
		posZ = nbt.getDouble("posZ");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("posX", posX);
		nbt.setDouble("posY", posY);
		nbt.setDouble("posZ", posZ);
	}
	
}
