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
package cbproject.deathmatch.entities;

import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.items.wpns.Weapon_RPG;
import cbproject.deathmatch.register.DMItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * RPG制导红点。
 * 
 * @author WeAthFolD
 * 
 */
public class EntityRPGDot extends EntityThrowable {

	public static final double DOT_MAX_RANGE = 100.0;
	private EntityPlayer shooter;

	// -1:Render facing the player. else : Render on block surface.
	private int side;

	public EntityRPGDot(World par1World, EntityPlayer player) {
		super(par1World, player);
		System.out.println(player);
		shooter = player;
		updateDotPosition();
	}

	public EntityRPGDot(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void onUpdate() {
		if (worldObj.isRemote || getThrower() == null)
			return;
		ItemStack currentItem = getThrower().getCurrentItemOrArmor(0);
		if (currentItem == null
				|| !Weapon_RPG.class.isInstance(currentItem.getItem())) {
			setDead();
			return;
		} else {
			int mode = DMItems.weapon_RPG.getMode(currentItem);
			if (mode == 0)
				this.setDead();
		}
		updateDotPosition();
	}

	private void updateDotPosition() {
		MotionXYZ begin = new MotionXYZ(shooter);
		MotionXYZ end = new MotionXYZ(begin).updateMotion(DOT_MAX_RANGE);
		MovingObjectPosition result = worldObj.rayTraceBlocks(
				begin.asVec3(worldObj), end.asVec3(worldObj));
		if (result != null) {
			posX = result.hitVec.xCoord;
			posY = result.hitVec.yCoord;
			posZ = result.hitVec.zCoord;
			side = result.sideHit;
			ForgeDirection[] v = ForgeDirection.values();
			ForgeDirection d = v[side].getOpposite();
			double dx = d.offsetX, dy = d.offsetY, dz = d.offsetZ;
			this.setPosition(posX + 0.03 * dx, posY + 0.03 * dy, posZ + 0.03 * dz);
		} else {
			posX = end.posX;
			posY = end.posY;
			posZ = end.posZ;
			side = -1;
		}
	}

	public int getDotSide() {
		return side;
	}

	@Override
	protected float func_70182_d() {
		return 0.0F;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {

	}

}
