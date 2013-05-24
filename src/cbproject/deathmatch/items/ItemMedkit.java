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

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.core.CBCMod;
import cbproject.core.item.CBCGenericItem;
import cbproject.deathmatch.entities.EntityBattery;
import cbproject.deathmatch.entities.EntityMedkit;

/**
 * @author mkpoli
 */
public class ItemMedkit extends CBCGenericItem {

	
	
	public ItemMedkit(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("hevbattery");
		this.setIconName("medkit");
	}
	

	/**
	 * @param string
	 * @param string2
	 * @param string3
	 */
	private void setPotions(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}


	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		if (!par2World.isRemote) {
			EntityMedkit ent = new EntityMedkit(par2World);
			par2World.spawnEntityInWorld(ent);
			par1ItemStack.splitStack(1);
		}
		return par1ItemStack;
	}
		

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("potion1.name")
				+ " : " + StatCollector.translateToLocal(this.getPotionsName(1, par1ItemStack)));
		par3List.add(StatCollector.translateToLocal("potion2.name")
				+ " : " + StatCollector.translateToLocal(this.getPotionsName(2, par1ItemStack)));
		par3List.add(StatCollector.translateToLocal("potion3.name")
				+ " : " + StatCollector.translateToLocal(this.getPotionsName(3, par1ItemStack)));
	}
	
	/**
	 * @return
	 */
	private String getPotionsName(int index, ItemStack medkitStack) {
		loadCompound(medkitStack).getString("potion" + index + "Name");
		return null;
	}

	private NBTTagCompound loadCompound(ItemStack stack) {
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound;
	}
	
	public void setPotions(ItemStack stack, ItemStack potion1Stack, ItemStack potion2Stack, ItemStack potion3Stack) {
		if (!(potion1Stack.getItem() == Item.potion) || !(potion1Stack.getItem() == Item.potion) || !(potion1Stack.getItem() == Item.potion)) 
			return;
		if (potion1Stack.getTagCompound().getBoolean("isBadEffect") == true)
			return;
		
	}
}