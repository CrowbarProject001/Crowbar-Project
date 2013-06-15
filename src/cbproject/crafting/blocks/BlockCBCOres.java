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
package cbproject.crafting.blocks;

import java.util.Random;

import cbproject.core.CBCMod;
import cbproject.core.register.CBCAchievements;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author mkpoli
 *
 */
public class BlockCBCOres  extends BlockOre {
	private int type;
	
	public BlockCBCOres(int par1, int type) {
		super(par1);
		this.type = type;
		setCreativeTab(CBCMod.cctMisc);
		setHardness(type == 0 ? 5.0F : 2.0F);
		setUnlocalizedName(type == 0 ? "uranium" : type == 1 ? "tin" : "copper");
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("lambdacraft:" + (type == 0 ? "uranium" : type == 1 ? "tin" : "copper"));
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return this.blockID;
	}
	
	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	@Override
	public void onBlockHarvested(World par1World, int par2, int par3, int par4,
			int par5, EntityPlayer par6EntityPlayer) {
		CBCAchievements.getAchievement(par6EntityPlayer, CBCAchievements.oreAchievements[this.type]);
	}
}
