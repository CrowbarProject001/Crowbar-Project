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

import java.util.ArrayList;
import java.util.List;

import cbproject.api.item.ICustomEnItem;
import cbproject.core.utils.EntitySelectorPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 触碰会自动给HEV充能的电池实体。
 * @author WeAthFolD
 *
 */
public class EntityBattery extends EntityProjectile {

	public static final int EU_PER_BATTERY = 50000;
	
	public EntityBattery(World world) {
		super(world);
		this.setSize(0.3F, 0.6F);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX-0.15, posY-0.3, posZ - 0.15, posX + 0.15, posY + 0.3, posZ + 0.15);
		List<EntityPlayer> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, new EntitySelectorPlayer());
		if(list == null)
			return;
		EntityPlayer player = list.get(0);
		tryChargeArmor(player);
		this.playSound("cbc.entity.battery", 0.5F, 1.0F);
		this.setDead();
	}
	
	/**
	 * TODO:NEEDS INTERGRATION.
	 * Charges the HEV armor wearing on the player with the amount of 1 battery(50000 EU).
	 * @param player the player entity
	 */
	public static void tryChargeArmor(EntityPlayer player){
		int amount = 0;
		List<ICustomEnItem> armor = new ArrayList();
		List<ItemStack> stack = new ArrayList();
		for(ItemStack s : player.inventory.armorInventory){
			if(s != null && (s.getItem() instanceof ICustomEnItem)){
				amount++;
				ICustomEnItem i = (ICustomEnItem)s.getItem();
				armor.add(i);
				stack.add(s);
			}
		}
		for(int i = 0; i < stack.size(); i++){
			armor.get(i).charge(stack.get(i), EU_PER_BATTERY / amount, 2, true, false);
		}
	}
	
	@Override
	protected float getHeadingVelocity() {
		return 0;
	}

	@Override
	protected float getMotionYOffset() {
		return 0;
	}

	@Override
	protected float getGravityVelocity() {
		return (float) 0.4;
	}

	@Override
	protected void onCollide(MovingObjectPosition result) {

	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

}
