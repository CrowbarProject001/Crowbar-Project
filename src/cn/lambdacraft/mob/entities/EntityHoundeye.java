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
import cn.lambdacraft.mob.register.CBCMobItems;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * TODO:待坑
 * @author WeAt3hFolD
 *
 */
public class EntityHoundeye extends EntityMob implements IEntityLink<EntityLiving> {

	protected String throwerName;
	public boolean isCharging = false;
	public int chargeTick = 0;
	protected int lastShockTick = 0;
	
	public IEntitySelector selector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			return GenericUtils.selectorLiving.isEntityApplicable(entity) && !(entity instanceof EntityHoundeye) && !throwerName.equals(entity.getEntityName());
		}
		
	};
	
	/**
	 * @param par1World
	 */
	public EntityHoundeye(World par1World) {
		super(par1World);
		this.texture = ClientProps.HOUNDEYE_PATH;
		this.moveSpeed = 3.0F;
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(20, Byte.valueOf((byte) 0));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote && isCharging) {
			if(++chargeTick > 50) {
				worldObj.spawnEntityInWorld(new EntityShockwave(worldObj, this, posX, posY, posZ));
				this.playSound(GenericUtils.getRandomSound("cbc.mobs.he_blast", 3), 0.5F, 1.0F);
				lastShockTick = ticksExisted;
				isCharging = false;
			}
		}
		
		if(worldObj.isRemote) {
			isCharging = dataWatcher.getWatchableObjectByte(20) > 0;
			chargeTick = dataWatcher.getWatchableObjectByte(20) - 1;
		} else {
			dataWatcher.updateObject(20, Byte.valueOf((byte)(isCharging? chargeTick + 1 : 0)));
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#getMaxHealth()
	 */
	@Override
	public int getMaxHealth() {
		return 14;
	}
	
	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		if (par2 < 4.0F && this.rand.nextInt(6) == 0 && !isCharging) {
			if (this.onGround && ticksExisted - lastShockTick > 40) {
				double d0 = par1Entity.posX - this.posX;
				double d1 = par1Entity.posZ - this.posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				this.motionX = -d0 / f2 * 0.2 + this.motionX;
				this.motionZ = -d1 / f2 * 0.2 + this.motionZ;
				this.motionY = 0.30;
				isCharging = true;
				chargeTick = 0;
				this.playSound(GenericUtils.getRandomSound("cbc.mobs.he_attack", 3), 0.5F, 1.0F);
			}
		}
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
    	isCharging = false;
    	return super.attackEntityFrom(par1DamageSource, par2);
    }
	
	@Override
    protected boolean isMovementCeased()
    {
        return isCharging;
    }
	
	/**
	 * 自定义的寻路函数，
	 */
	@Override
	protected Entity findPlayerToAttack() {
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(posX - 8.0,
				posY - 8.0, posZ - 8.0, posX + 8.0, posY + 8.0, posZ + 8.0);
		List<EntityLiving> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox,
						selector);
		EntityLiving entity = null;
		double distance = 10000.0F;
		for (EntityLiving s : list) {
			if(s instanceof EntityPlayer && throwerName.equals(s.getEntityName()))
				continue;
			double dx = s.posX - posX, dy = s.posY - posY, dz = s.posZ - posZ;
			double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
			if (d < distance) {
				entity = s;
				distance = d;
			}
		}
		if (entity == null)
			return null;
		this.playSound(GenericUtils.getRandomSound("cbc.mobs.he_alert", 3), 0.5F, 1.0F);
		return entity;
	}
	
	
	
	
    public EntityItem dropItemWithOffset(int par1, int par2, float par3)
    {
        return this.entityDropItem(new ItemStack(par1, par2, 2), par3);
    }
    
    @Override
    public int getDropItemId() {
    	return CBCMobItems.bioTissue.itemID;
    }
	
	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		return GenericUtils.getRandomSound("cbc.mobs.he_idle", 4);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return GenericUtils.getRandomSound("cbc.mobs.he_pain", 5);
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return GenericUtils.getRandomSound("cbc.mobs.he_die", 3);
	}

	@Override
	public EntityLiving getLinkedEntity() {
		return null;
	}

	@Override
	public void setLinkedEntity(EntityLiving entity) {
		throwerName = entity.getEntityName();
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
    	super.writeEntityToNBT(nbt);
    	nbt.setString("thrower", throwerName);
    }
	
	@Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	super.readEntityFromNBT(nbt);
    	throwerName = nbt.getString("thrower");
    }

}
