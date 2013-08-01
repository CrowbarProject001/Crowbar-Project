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

import java.util.HashMap;
import java.util.List;

import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import cn.lambdacraft.mob.register.CBCMobItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * 喜闻乐见的藤壶怪哟，因为基本不需要用到生物的特性所以 直接继承的Entity类
 * @author WeAthFolD
 */
public class EntityBarnacle extends EntityLiving {

	public static HashMap<Entity, EntityBarnacle> pullingEntityMap = new HashMap();
	
	public double tentacleLength;

	public boolean detach = false;
	public Entity pullingEntity;
	private int tickBeforeLastAttack = 0, timesEaten = 0;
	private int tickBreaking = 0;
	
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
	public void entityInit() {
		super.entityInit();
		//11 tentacle length
		this.dataWatcher.addObject(11, Byte.valueOf((byte)0));
		//12 pulling entity id
		this.dataWatcher.addObject(12, Integer.valueOf((byte)0));
		//13 breaking time left
		this.dataWatcher.addObject(13, Byte.valueOf((byte)0));
	}
	
	@Override
	public void onUpdate() {
		
		++this.ticksExisted;
		if(this.hurtResistantTime > 0)
			--this.hurtResistantTime;
		
		if(detach) {
			super.onUpdate();
			return;
		}
		this.updateTentacle();
		this.updatePullingEntity();
		if(pullingEntity == null) {
			
			//Calculate tracking range
			if(--tickBreaking <= 0) {
				MotionXYZ begin = new MotionXYZ(posX, posY, posZ, 0.0, -1.0, 0.0);
				Vec3 vec1 = begin.asVec3(worldObj), vec2 = begin.updateMotion(30.0).asVec3(worldObj);
				MovingObjectPosition trace = worldObj.rayTraceBlocks(vec1, vec2);
				double distance = trace != null ? (posY - trace.hitVec.yCoord) : 30;
				if(tentacleLength < distance)
					tentacleLength += 0.05;
				else tentacleLength = distance;
				//Bounding box calc
				if(!worldObj.isRemote) {
					AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - 0.3, posY - tentacleLength, posZ - 0.3, posX + 0.3, posY + 1, posZ + 0.3);
					List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, GenericUtils.selectorLiving);
					if(list != null && list.size() == 1) {
						Entity e = list.get(0);
						if(GenericUtils.getEntitySize(e) <= 6.0 && !(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)) {
							this.playSound("cbc.mobs.bcl_alert", 0.5F, 1.0F);
							startPullingEntity(e);
						}
					}
					if(ticksExisted % 80 == 0 && this.rand.nextFloat() < 0.4) {
						this.playSound("cbc.mobs.bcl_tongue", 0.5F, 1.0F);
					}
				}
			} else
			if(ticksExisted % 45 == 0) {
				this.playSound(GenericUtils.getRandomSound("cbc.mobs.bcl_chew", 3), 0.5F, 1.0F);
			}
		} else {
			//Moving pulling entity
			pullingEntity.moveEntity(0.0, -pullingEntity.motionY, 0.0);
			pullingEntity.motionY = 0.07;
			pullingEntity.setPosition(posX, posY - tentacleLength, posZ);
			pullingEntity.motionX = pullingEntity.motionZ = 0.0;
			if(tentacleLength >= 0.8) {
				tentacleLength -= 0.05;
			} else {
				if(++tickBeforeLastAttack >= 20) {
					tickBeforeLastAttack = 0;
					if(!(pullingEntity instanceof EntityLiving)) {
						stopPullingEntity();
					} else {
						pullingEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 15);
						this.playSound("cbc.mobs.bcl_bite", 0.5F, 1.0F);
						if(++timesEaten > 5) {
							pullingEntity.fallDistance = 0.0F;
							stopPullingEntity();
						}
					}
				}
			}
			if(pullingEntity != null && pullingEntity.isDead)
				stopPullingEntity();
		}
		//Check if barnacle could still exist
		if(worldObj.getBlockId(MathHelper.floor_double(posX), (int)posY + 1, MathHelper.floor_double(posZ)) == 0) {
			if(ticksExisted < 10) {
				MotionXYZ mo = new MotionXYZ(this);
				MovingObjectPosition result = worldObj.rayTraceBlocks(mo.asVec3(worldObj), mo.asVec3(worldObj).addVector(0.0, 40.0, 0.0));
				if(result != null) {
					this.setPosition(result.blockX + 0.5, result.blockY - 1.0, result.blockZ + 0.5);
				} else this.setDead();
			} else {
				health = 0;
				detach = true;
				motionY = 0.0;
				motionX = (rand.nextDouble() - 0.5) * 0.2F;
				motionZ = (rand.nextDouble() - 0.5) * 0.2F;
			}
		} else detach = false;
		if(worldObj.difficultySetting == 0)
			this.setDead();
	}
	
	private void updateTentacle() {
		if(worldObj.isRemote) {
			if(ticksExisted % 20 == 0) {
				tentacleLength = dataWatcher.getWatchableObjectByte(11);
				tickBreaking = dataWatcher.getWatchableObjectByte(13);
				if(tickBreaking < 0)
					tickBreaking += 510;
			}
		} else {
			dataWatcher.updateObject(11, Byte.valueOf((byte)tentacleLength));
			dataWatcher.updateObject(13, Byte.valueOf((byte)tickBreaking));
		}
	}
	
	private void updatePullingEntity() {
		if(worldObj.isRemote) {
			int id = dataWatcher.getWatchableObjectInt(12);
			Entity e = worldObj.getEntityByID(id);
			pullingEntity = e;
		}
	}
	
	protected void startPullingEntity(Entity e) {
		if(pullingEntityMap.containsKey(e))
			return;
		pullingEntity = e;
		tentacleLength = posY - e.posY;
		e.moveEntity(0.0, 0.1, 0.0);
		e.onGround = false;
		tickBreaking = 300;
		pullingEntityMap.put(e, this);
		int entityid = pullingEntity == null ? 0 : pullingEntity.entityId;
		dataWatcher.updateObject(12, Integer.valueOf(entityid));
		timesEaten = tickBeforeLastAttack = 0;
	}
	
	protected void stopPullingEntity() {
		pullingEntity = null;
		dataWatcher.updateObject(12, Integer.valueOf(0));
	}
	
    public EntityItem dropItemWithOffset(int par1, int par2, float par3)
    {
        return this.entityDropItem(new ItemStack(par1, par2, 1), par3);
    }
    
    @Override
    public int getDropItemId() {
    	return CBCMobItems.bioTissue.itemID;
    }

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#readEntityFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		tentacleLength = nbt.getDouble("tentacle");
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#writeEntityToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setDouble("tentacle", tentacleLength);
	}
	
    public boolean canBeCollidedWith()
    {
        return true;
    }
	
    public boolean isEntityInvulnerable()
    {
    	return false;
    }

	@Override
	public int getMaxHealth() {
		return 20;
	}
	
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }

}
