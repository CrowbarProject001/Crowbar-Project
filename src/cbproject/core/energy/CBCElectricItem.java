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
package cbproject.core.energy;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.core.CBCMod;

/**
 * @author Administrator
 *
 */
public abstract class CBCElectricItem extends Item implements ICustomEnItem {

	public CBCElectricItem(int par1) {
		super(par1);
		setMaxStackSize(1);
		setCreativeTab(CBCMod.cct);
	}

	@Override
	public int getChargedItemId(ItemStack itemStack) {
		return itemStack.itemID;
	}

	/* (non-Javadoc)
	 * @see cbproject.api.item.IEnItem#getEmptyItemId(net.minecraft.item.ItemStack)
	 */
	@Override
	public int getEmptyItemId(ItemStack itemStack) {
		return itemStack.itemID;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return this.getMaxDamage();
	}

	@Override
	public int charge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		if(itemStack.getItemDamage() == 0)
			return 0;
		int en = itemStack.getItemDamage();
		if(!ignoreTransferLimit)
			amount = this.getTransferLimit(itemStack);
		if(en > amount){
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() - amount);
			return amount;
		} else {
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() - en);
			return en;
		}
	}

	@Override
	public int discharge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		
		int en = itemStack.getMaxDamage() - itemStack.getItemDamage() - 1;
		if(en == 0)
			return 0;
		if(!ignoreTransferLimit)
			amount = this.getTransferLimit(itemStack);
		if(en > amount){
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() + amount);
			return amount;
		} else {
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() + en);
			return en;
		}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	par3List.add(StatCollector.translateToLocal("curenergy.name") + " : " + 
    (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamageForDisplay()) + "/" + par1ItemStack.getMaxDamage() + " EU");
    }

	/* (non-Javadoc)
	 * @see cbproject.api.item.ICustomEnItem#canShowChargeToolTip(net.minecraft.item.ItemStack)
	 */
	@Override
	public boolean canShowChargeToolTip(ItemStack itemStack) {
		// TODO Auto-generated method stub
		return false;
	}

}
