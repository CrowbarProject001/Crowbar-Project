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
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import cn.lambdacraft.mob.ModuleMob;
import cn.lambdacraft.mob.register.CBCMobItems;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 你问我为什么要继承EntityLiving？这货本来可是大光圈基地的产物啊，233
 * @author WeAthFolD
 *
 */
public class EntitySentry extends EntityLiving implements IEntityLink {

	public Entity currentTarget;
	public String placerName = "";
	public boolean isActivated, rotationSet;
	public int activationCounter = 0;
	public int tickSinceLastAttack = 0;
	public float rotationYawSearch;
	public static final float TURNING_SPEED = 5.0F;
	
	protected IEntitySelector selector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity entity) {
			if(entity instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer) entity;
				if(ep.username.equals(placerName))
					return false;
			} else if(entity instanceof EntitySentry)
				return !((EntitySentry)entity).placerName.equals(placerName);
			return GenericUtils.selectorLiving.isEntityApplicable(entity);
		}
		
	};
	
	/**
	 * @param par1World
	 */
	public EntitySentry(World par1World) {
		super(par1World);
	}
	
	public EntitySentry(World par1World, double x, double y, double z, float yaw) {
		super(par1World);
		this.setPosition(x, y, z);
		this.rotationYaw = yaw;
		this.rotationYawSearch = yaw;
	}
	
	public EntitySentry(World par1World, EntityPlayer placer, double x, double y, double z, float yaw) {
		super(par1World);
		this.setPosition(x, y, z);
		placerName = placer.username;
		this.rotationYaw = yaw;
		this.rotationYawSearch = yaw;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#entityInit()
	 */
	@Override
	protected void entityInit() {
		super.entityInit();
		this.setSize(0.4F, 1.575F);
		this.dataWatcher.addObject(15, Integer.valueOf(0));
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	@Override
	public void onUpdate() {
		
		//float lastRotationYaw = this.rotationYaw;
		super.onUpdate();
		//this.rotationYaw = lastRotationYaw;
		if(this.isActivated) {
			++activationCounter;
			if(this.isSearching()) {
				this.rotationYawHead = MathHelper.sin(ticksExisted * 0.06F) * 50 + this.rotationYawSearch;
				this.rotationYawHead = GenericUtils.wrapYawAngle(rotationYawHead);
			}
			if(currentTarget == null){
				if(!worldObj.isRemote && ticksExisted % 10 == 0)
					searchForTarget();
			} else attemptAttack();
		}
		this.sync();
	}
	
	public void sync() {
		if(worldObj.isRemote) {
			int entityid = dataWatcher.getWatchableObjectInt(15);
			Entity e = worldObj.getEntityByID(entityid);
			if(e != null && e.isEntityAlive())
				currentTarget = e;
			
			this.isActivated = dataWatcher.getWatchableObjectByte(16) > 0;
		} else {
			if(currentTarget != null)
				dataWatcher.updateObject(15, currentTarget.entityId);
			dataWatcher.updateObject(16, Byte.valueOf((byte) (isActivated? 0x1 : 0x0)));
		}
	}
	
	protected void searchForTarget() {
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX - 10, posY - 6, posZ - 10, posX + 10, posY + 6, posZ + 10);
		List<EntityLiving> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, this.selector);
		double lastDistance = 10000.0;
		EntityLiving targetEntity = null;
		for(EntityLiving e : list) {
			if(!e.isEntityInvulnerable() && this.canEntityBeSeen(e)) {
				double distance = e.getDistanceSqToEntity(this);
				if(distance > lastDistance)
					continue;
				lastDistance = distance;
				targetEntity = e;
			}
		}
		if(targetEntity != null) {
			currentTarget = targetEntity;
		}
	}
	
	protected boolean isSearching() {
		boolean b = this.activationCounter > 20 && currentTarget == null;
		b = b || (currentTarget != null && !this.canEntityBeSeen(this.currentTarget));
		return b;
	}
	
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
    	boolean b = super.attackEntityFrom(par1DamageSource, par2);
    	if(b) {
    		isActivated = true;
    		if(par1DamageSource.getEntity() != null && selector.isEntityApplicable(par1DamageSource.getEntity()))
    			currentTarget = par1DamageSource.getEntity();
    	}
    	return b;
    }
	
	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z
	 * direction.
	 */
	public void setSentryHeading(double par1, double par3, double par5,
			float par7) {
		float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5
				* par5);
		par1 /= f2;
		par3 /= f2;
		par5 /= f2;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		
		double dx = par1 - motionX, dy = par3 - motionY, dz = par5 - motionZ;
		float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);

		float lastRotationYaw = rotationYawHead;
		this.prevRotationYawHead = this.rotationYawHead = (float) (Math.atan2(par1,
				par5) * 180.0D / Math.PI);
		float dyaw = rotationYawHead - lastRotationYaw;
		if(dyaw > 180.0F || dyaw < -180.0F)
			dyaw = -dyaw;
		if(Math.abs(dyaw) > 20.0F) {
			dyaw = dyaw > 0 ? 20.0F : -20.0F;
			this.rotationYawHead = GenericUtils.wrapYawAngle(lastRotationYaw + dyaw);
		}
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3,
				f3) * 180.0D / Math.PI);
	}
	
	protected void attemptAttack() {
		if(currentTarget == null)
			return;
		double dx = currentTarget.posX - this.posX, dy = currentTarget.posY - this.posY, dz = currentTarget.posZ
				- this.posZ;
		if(!isSearching())
			this.setSentryHeading(dx, dy, dz, 1.0F);
		if(++tickSinceLastAttack > 5) {
			tickSinceLastAttack = 0;
			if(!canEntityBeSeen(currentTarget)) {
				if(!rotationSet)
					rotationYawSearch = rotationYawHead;
				rotationSet = true;
			} else { 
				rotationSet = false;
				BulletManager.Shoot(3, this, worldObj);
			}
			if(currentTarget.getDistanceSqToEntity(this) > 400) {
				this.currentTarget = null;
				return;
			}
		}
		if(currentTarget.isDead || currentTarget.isEntityInvulnerable())
			this.currentTarget = null;
	}
	
	@Override
	protected boolean isMovementBlocked()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#readEntityFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		int entityid = nbt.getInteger("targetId");
		Entity e = worldObj.getEntityByID(entityid);
		if(e != null)
			currentTarget = e;
		isActivated = nbt.getBoolean("isActivated");
		activationCounter = nbt.getInteger("activationTime");
		rotationYawSearch = nbt.getFloat("searchYaw");
		placerName = nbt.getString("placer");
	}
	
	public void activate() {
		isActivated = true;
	}
	
	@Override
	public boolean interact(EntityPlayer player)
    {
		ItemStack currentItem = player.getCurrentEquippedItem();
		if(currentItem != null && currentItem.itemID == CBCMobItems.sentrySyncer.itemID) {
			ModuleMob.syncMap.put(player, this);
			if(worldObj.isRemote)
				return true;
			StringBuilder b = new StringBuilder("---------HECU Marine Force Automatic Sentry---------\n");
			b.append(EnumChatFormatting.RED).append("Sentry ID : ").append(EnumChatFormatting.WHITE).append(this.entityId).append("\n");
			b.append(EnumChatFormatting.DARK_RED).append("The Sentry has successfully linked to your syncer.");
			player.sendChatToPlayer(b.toString());
			return true;
		}
		return false;
    }

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#writeEntityToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if(currentTarget != null)
			nbt.setInteger("targetId", currentTarget.entityId);
		nbt.setBoolean("isActivated", isActivated);
		nbt.setInteger("activationTime", activationCounter);
		nbt.setFloat("searchYaw", rotationYawSearch);
		nbt.setString("placer", placerName);
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public Entity getLinkedEntity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLinkedEntity(Entity entity) {
		if(!(entity instanceof EntityPlayer))return;
		this.placerName = ((EntityPlayer)entity).username;
	}

}
