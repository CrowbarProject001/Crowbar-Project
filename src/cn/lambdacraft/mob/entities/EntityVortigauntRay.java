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

import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.lambdacraft.api.weapon.InformationEnergy;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.deathmatch.entities.EntityBullet;
import cn.lambdacraft.deathmatch.entities.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Egon;

/**
 * 腹地刚的绿色Bilibili！
 * 
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class EntityVortigauntRay extends Entity {

	public double startX, startY, startZ;
	public double destX, destY, destZ;

	/**
	 * @param par1World
	 * @param par2EntityLiving
	 * @param par3itemStack
	 */
	public EntityVortigauntRay(World par1World, EntityLiving ent, Entity target) {
		super(par1World);
		setSize(1.0F, 1.0F);
		setPosition(ent.posX, ent.posY + ent.height, ent.posZ);
		startX = ent.posX;
		startY = ent.posY + ent.height;
		startZ = ent.posZ;
		destX = target.posX;
		destY = target.posY + target.height - ent.height;
		destZ = target.posZ;
		this.ignoreFrustumCheck = true;
	}

	@Override
	public void entityInit() {
	}
	
	@Override
	public void onUpdate() {
		if(++ticksExisted > 15)
			setDead();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		setDead();
	}
}
