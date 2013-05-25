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
package cbproject.core.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * LambdaCraft通用物品类。
<<<<<<< HEAD
 * 
 * @author WeAthFolD
 * 
=======
 * @author WeAthFolD
 *
>>>>>>> origin/weathfold
 */
public abstract class CBCGenericItem extends Item {

	protected String description;
<<<<<<< HEAD
	protected boolean useDescription = false;
	private String iconName = "";

=======
	protected boolean useDescription = false;;
	private String iconName = "";
	
>>>>>>> origin/weathfold
	/**
	 * @param par1
	 */
	public CBCGenericItem(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
	}
<<<<<<< HEAD

	public CBCGenericItem setDescription(String d) {
=======
	
	public CBCGenericItem setDescription(String d){
>>>>>>> origin/weathfold
		this.description = d;
		useDescription = true;
		return this;
	}
<<<<<<< HEAD

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if (useDescription)
			par3List.add(description);
	}

	public void setIconName(String name) {
		this.iconName = name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister
				.registerIcon("lambdacraft:" + iconName);
	}
=======
	
	@SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		if(useDescription)
			par3List.add(description);
	}
	
	public void setIconName(String name){
		this.iconName = name;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:" + iconName);
    }
>>>>>>> origin/weathfold

}
