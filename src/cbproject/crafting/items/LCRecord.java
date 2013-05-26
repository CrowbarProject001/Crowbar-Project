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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cbproject.core.CBCMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/*喜闻乐见的彩蛋*/

/**
 * @author Administrator
 *
 */
public class LCRecord extends ItemRecord {

	
	int recID;
	
	/**
	 * @param par1
	 * @param par2Str
	 */
	public LCRecord(int par1, String par2Str, int subID) {
		super(par1, par2Str);
		setCreativeTab(CBCMod.cct);
		recID = subID;
	}
    
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
        return this.itemIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
    	return EnumRarity.rare;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(this.recordName + " by Valve");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordTitle() {
		// TODO Auto-generated method stub
		return "Valve - " + this.recordName;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		// TODO Auto-generated method stub
		super.getSubItems(par1, par2CreativeTabs, par3List);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:record" + recID);
    }
	
	
}
