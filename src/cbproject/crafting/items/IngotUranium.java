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
package cbproject.crafting.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cbproject.core.item.CBCGenericItem;

public class IngotUranium extends CBCGenericItem {

	public IngotUranium(int par1) {
		super(par1);
		setIAndU("ingotUranium");
	}
    
	/**
	 * 拿在手中的时候
	 */
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		if (!(par3Entity instanceof EntityPlayer))
			return;
		EntityPlayer p = (EntityPlayer) par3Entity;
		ItemStack currentItem = p.inventory.getCurrentItem();
		if (currentItem != par1ItemStack)
			return;
		if (par2World.getWorldTime() % 20 == 0) {
			p.attackEntityFrom(DamageSource.starve, 1);
		}
	}
}
