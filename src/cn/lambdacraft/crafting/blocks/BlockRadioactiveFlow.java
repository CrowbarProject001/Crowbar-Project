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
package cn.lambdacraft.crafting.blocks;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

/**
 * 辐射废液-流动。 
 * @author Rikka
 *
 */
public class BlockRadioactiveFlow extends BlockFlowing {

	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockRadioactiveFlow(int id) {
        super(id, Material.water);
        setCreativeTab(CBCMod.cct);
        blockHardness = 100.0F;
        setLightOpacity(3);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        theIcon = new Icon[2];
        theIcon[0] = iconRegister.registerIcon("lambdacraft:radioactive_still");
        theIcon[1] = iconRegister.registerIcon("lambdacraft:radioactive_flow");
        System.out.println(theIcon[0] + " " + theIcon[1]);
        System.out.println(LiquidDictionary.getCanonicalLiquid(new LiquidStack(blockID, 1000, 0)));
        LiquidDictionary.getOrCreateLiquid("radioActive", new LiquidStack(blockID, 1000, 0))
        	.setRenderingIcon(theIcon[0])
        	.setTextureSheet("/gui/items.png");
    }


}
