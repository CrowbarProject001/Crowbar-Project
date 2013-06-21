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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开原协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.entities.fx;

import cn.lambdacraft.api.weapon.InformationEnergy;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_egon;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 进行Egon光束渲染的实用实体。
 * 
 * @author WeAthFolD
 * 
 */
public class EntityEgonRay extends Entity {

	public ItemStack item;
	public boolean renderThirdPerson;
	public boolean draw = true;
	public boolean isClient = false;
	private EntityLiving thrower;

	public EntityEgonRay(World par1World, EntityLiving ent, ItemStack itemStack) {
		super(par1World);
		this.posX = ent.posX;
		this.posY = ent.posY;
		this.posZ = ent.posZ;
		ignoreFrustumCheck = true;
		thrower = ent;
		item = itemStack;
		renderThirdPerson = true;
		isClient = true;
	}

	public EntityEgonRay(World world) {
		super(world);
		thrower = Minecraft.getMinecraft().thePlayer;
		isClient = false;
	}

	public EntityLiving getThrower() {
		return thrower;
	}

	@Override
	public void onUpdate() {

		if (worldObj.isRemote && !isClient && draw) {
			if (this.getDistanceSqToEntity(thrower) < 4.5)
				draw = false;
			return;
		}

		if (item == null)
			return;

		InformationEnergy inf = ((Weapon_egon) item.getItem()).getInformation(
				item, worldObj);
		if (inf == null
				|| !(inf.isShooting && ((Weapon_egon) item.getItem()).canShoot(
						(EntityPlayer) thrower, item))) {
			this.setDead();
			return;
		}

		EntityLiving ent = thrower;
		this.setLocationAndAngles(ent.posX, ent.posY + ent.getEyeHeight(),
				ent.posZ, ent.rotationYawHead, ent.rotationPitch);

		float var3 = 0.4F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
				* var3;
		this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F
				* (float) Math.PI)
				* MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
				* var3;
		this.motionY = -MathHelper.sin((this.rotationPitch) / 180.0F
				* (float) Math.PI)
				* var3;
	}

	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

}