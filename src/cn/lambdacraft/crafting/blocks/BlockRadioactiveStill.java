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
import cn.lambdacraft.crafting.register.CBCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

/**
 * @author WeAthFolD
 *
 */
public class BlockRadioactiveStill extends BlockStationary {

	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockRadioactiveStill(int id) {
		super(id, Material.water);
        setCreativeTab(CBCMod.cctMisc);
        blockHardness = 100.0F;
        setLightOpacity(3);
        disableStats();
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        theIcon = new Icon[] {
                iconRegister.registerIcon("lambdacraft:radioactive_still"),
                iconRegister.registerIcon("lambdacraft:radioactive_flow") };
        LiquidDictionary.getCanonicalLiquid(new LiquidStack(blockID, 1000, 0)).setRenderingIcon(theIcon[0]).setTextureSheet("/gui/items.png");
    }
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLiving){
			EntityLiving player=(EntityLiving)entity;
			player.addPotionEffect(new PotionEffect(Potion.poison.id,100,0));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id,100,0));
		}
	}

}
