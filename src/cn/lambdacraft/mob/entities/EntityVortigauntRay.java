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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.lambdacraft.api.weapon.InformationEnergy;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.deathmatch.entities.EntityBullet;
import cn.lambdacraft.deathmatch.entities.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Egon;

/**
 * 腹地刚！的绿色Bilibili！
 * 
 * @author WeAthFolD
 */
public class EntityVortigauntRay extends EntityBullet {

	public double startX, startY, startZ;
	public double destX, destY, destZ;
	private int updateTick = 0;

	/**
	 * @param par1World
	 * @param par2EntityLiving
	 * @param par3itemStack
	 */
	public EntityVortigauntRay(World par1World, EntityLiving ent, Entity target) {
		super(par1World, ent, 10);
		setPosition(ent.posX, ent.posY + ent.height, ent.posZ);
		startX = ent.posX;
		startY = ent.posY + ent.height;
		startZ = ent.posZ;
		destX = target.posX;
		destY = target.posY + target.height / 2;
		destZ = target.posZ;
		this.setThrowableHeading(destX - startX, destY - startY, destZ - startZ, this.func_70182_d(), 0.0F);
		motion = new MotionXYZ(this);
		dataWatcher.updateObject(20, String.valueOf((float)startX));
		dataWatcher.updateObject(21, String.valueOf((float)startY));
		dataWatcher.updateObject(22, String.valueOf((float)startZ));

		dataWatcher.updateObject(24, String.valueOf((float)destX));
		dataWatcher.updateObject(25, String.valueOf((float)destY));
		dataWatcher.updateObject(26, String.valueOf((float)destZ));
		this.ignoreFrustumCheck = true;
	}
	
	public EntityVortigauntRay(World par1World){
		super(par1World);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		// Start XYZ
		dataWatcher.addObject(20, String.valueOf(0));
		dataWatcher.addObject(21, String.valueOf(0));
		dataWatcher.addObject(22, String.valueOf(0));

		// Dest XYZ
		dataWatcher.addObject(24, String.valueOf(0));
		dataWatcher.addObject(25, String.valueOf(0));
		dataWatcher.addObject(26, String.valueOf(0));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (--updateTick <= 0) {
			updateTick = 5;
			if (worldObj.isRemote) {
				startX = Float.valueOf(dataWatcher.getWatchableObjectString(20));
				startY = Float.valueOf(dataWatcher.getWatchableObjectString(21));
				startZ = Float.valueOf(dataWatcher.getWatchableObjectString(22));

				destX = Float.valueOf(dataWatcher.getWatchableObjectString(24));
				destY = Float.valueOf(dataWatcher.getWatchableObjectString(25));
				destZ = Float.valueOf(dataWatcher.getWatchableObjectString(26));
			} else {
				dataWatcher.updateObject(20, String.valueOf((float)startX));
				dataWatcher.updateObject(21, String.valueOf((float)startY));
				dataWatcher.updateObject(22, String.valueOf((float)startZ));

				dataWatcher.updateObject(24, String.valueOf((float)destX));
				dataWatcher.updateObject(25, String.valueOf((float)destY));
				dataWatcher.updateObject(26, String.valueOf((float)destZ));
			}
		}
	}
}
