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

import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * 喜闻乐见的藤壶怪哟，因为基本不需要用到生物的特性所以 直接继承的Entity类
 * @author WeAthFolD
 */
public class EntityBarnacle extends Entity {

	public int health;
	public double tentacleLength;
	public Entity pullingEntity;
	private int tickBeforeLastAttack = 0, timesEaten = 0;
	
	public EntityBarnacle(World world, int blockX, int blockY, int blockZ) {
		super(world);
		this.setSize(0.7F, 0.8F);
		this.setPosition(blockX + 0.5, blockY - 1.0, blockZ + 0.5);
		this.health = 20;
	}
	
	/**
	 * @param par1World
	 */
	public EntityBarnacle(World par1World) {
		super(par1World);
		this.setSize(0.7F, 0.8F);
		this.health = 20;
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	public void onUpdate() {
		
		++this.ticksExisted;
		
		System.out.println(pullingEntity + " " + tentacleLength);
		if(pullingEntity == null) {
			//Calculate tracking range
			MotionXYZ begin = new MotionXYZ(posX, posY, posZ, 0.0, -1.0, 0.0);
			Vec3 vec1 = begin.asVec3(worldObj), vec2 = begin.updateMotion(30.0).asVec3(worldObj);
			MovingObjectPosition trace = worldObj.rayTraceBlocks(vec1, vec2);
			double distance = trace != null ? (posY - trace.hitVec.yCoord) : 30;
			if(tentacleLength < distance)
				tentacleLength += 0.05;
			else tentacleLength = distance;
			
			begin = new MotionXYZ(posX, posY, posZ, 0.0, -1.0, 0.0);
			vec2 = begin.updateMotion(tentacleLength).asVec3(worldObj);
			MovingObjectPosition result = BulletManager.rayTraceEntities(GenericUtils.selectorLiving, worldObj, vec1, vec2, this);
			if(result != null) {
				pullingEntity = result.entityHit;
				tentacleLength = posY - pullingEntity.posY;
			}
		} else {
			pullingEntity.posY = posY + tentacleLength;
			if(tentacleLength >= 0.8) {
				tentacleLength -= 0.1;
			} else {
				if(++tickBeforeLastAttack >= 13) {
					tickBeforeLastAttack = 0;
					if(!(pullingEntity instanceof EntityLiving)) {
						pullingEntity = null;
					} else {
						pullingEntity.attackEntityFrom(DamageSource.causeThrownDamage(this, this), 15);
						if(++timesEaten > 3)
							pullingEntity = null;
					}
				}
			}
		}
		//Check if barnacle can still exist
		if(!worldObj.isBlockSolidOnSide((int)posX - 1, (int)posY + 1, (int)posZ, ForgeDirection.DOWN)) {
			this.setDead();
			return;
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#entityInit()
	 */
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}
	
	@Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
    	this.setDead();
    	return true;
    }

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#readEntityFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		posX = nbt.getDouble("posX");
		posY = nbt.getDouble("posY");
		posZ = nbt.getDouble("posZ");
		health = nbt.getInteger("health");
		tentacleLength = nbt.getDouble("tentacle");
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#writeEntityToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("health", health);
		nbt.setDouble("posX", posX);
		nbt.setDouble("posY", posY);
		nbt.setDouble("posZ", posZ);
		nbt.setDouble("tentacle", tentacleLength);
	}
	
    public boolean isEntityInvulnerable()
    {
    	return false;
    }

}
