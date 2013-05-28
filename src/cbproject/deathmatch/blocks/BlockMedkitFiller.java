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
package cbproject.deathmatch.blocks;

import cbproject.core.CBCMod;
import cbproject.deathmatch.blocks.tileentities.TileMedkitFiller;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class BlockMedkitFiller extends BlockContainer{

	private Icon iconTop, iconBottom;
	 /**
	 * @param par1
	 * @param par2Material
	 */
	public BlockMedkitFiller(int par1) {
		super(par1, Material.rock);
		this.setUnlocalizedName("medkitfiller");
		setCreativeTab(CBCMod.cct);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("lambdacraft:medfiller_side");
        this.iconTop = par1IconRegister.registerIcon("lambdacraft:medfiller_top");
        this.iconBottom = par1IconRegister.registerIcon("lambdacraft:crafter_bottom");
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2)
    {
        if(par1 < 1)
        	return iconBottom;
        if(par1 < 2)
        	return iconTop;
        return this.blockIcon;
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileMedkitFiller();
	}


}
