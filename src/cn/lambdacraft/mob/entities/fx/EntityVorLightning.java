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
package cn.lambdacraft.mob.entities.fx;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cn.lambdacraft.api.weapon.InformationEnergy;
import cn.lambdacraft.deathmatch.entities.EntityBullet;
import cn.lambdacraft.deathmatch.entities.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Egon;

/**
 * 腹地刚！的绿色Bilibili！
 * @author WeAthFolD
 */
public class EntityVorLightning extends EntityEgonRay {

	public double startX, startY, startZ;
	
	/**
	 * @param par1World
	 * @param par2EntityLiving
	 * @param par3itemStack
	 */
	public EntityVorLightning(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving, null);
		this.ignoreFrustumCheck = true;
	}
	
	public EntityVorLightning(World world) {
		super(world);
	}
	
	@Override
	public void onUpdate() {

		if (worldObj.isRemote && !isClient && draw) {
			if (this.getDistanceSqToEntity(thrower) < 4.5)
				draw = false;
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
	public void entityInit() {
		super.entityInit();
	}
}
