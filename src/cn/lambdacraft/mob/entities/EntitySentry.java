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
import cn.lambdacraft.mob.ModuleMob;
import cn.lambdacraft.mob.register.CBCMobItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 你问我为什么要继承EntityLiving？这货本来可是大光圈基地的产物啊，233
 * @author WeAthFolD
 *
 */
public class EntitySentry extends EntityLiving {

	public Entity currentTarget;
	public boolean isActivated;
	
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
	}

	/* (non-Javadoc)
	 * @see net.minecraft.entity.Entity#entityInit()
	 */
	@Override
	protected void entityInit() {
		super.entityInit();
		this.setSize(0.4F, 1.575F);
		this.dataWatcher.addObject(3, Integer.valueOf(0));
	}
	
	@Override
	public void onUpdate() {
		++this.ticksExisted;
		if(hurtResistantTime > 0)
			--hurtResistantTime;
		if(this.health <= 0)
			this.onDeathUpdate();
		this.onGround = true;
		this.moveEntity(0.0, 0.0, 0.0);
		this.rotationYawHead = MathHelper.sin(ticksExisted * 0.06F) * 50;
		//super.onUpdate(); <-去掉注释会有神奇的效果
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
	}
	
	public void activate() {
		
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
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

}
