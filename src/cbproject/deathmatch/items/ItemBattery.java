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
package cbproject.deathmatch.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.core.CBCMod;
import cbproject.core.item.ElectricItem;
import cbproject.deathmatch.entities.EntityBattery;

/**
 * @author Administrator
 *
 */
public class ItemBattery extends ElectricItem {
	
	public ItemBattery(int par1) {
		super(par1);
		setUnlocalizedName("hevbattery");
		this.setIconName("battery");
		this.tier = 2;
		this.transferLimit = 128;
		this.maxCharge = EntityBattery.EU_PER_BATTERY;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	
    	if(!par2World.isRemote){
    		EntityBattery ent = new EntityBattery(par2World, par3EntityPlayer, EntityBattery.EU_PER_BATTERY - par1ItemStack.getItemDamage());
    		par2World.spawnEntityInWorld(ent);
    		par1ItemStack.splitStack(1);
    	}
        return par1ItemStack;
    }
	
	@Override
	public boolean canUse(ItemStack itemStack, int amount) {
		return itemStack.getMaxDamage() - itemStack.getItemDamage() > 0;
	}

	@Override
	public boolean canShowChargeToolTip(ItemStack itemStack) {
		return true;
	}

}
