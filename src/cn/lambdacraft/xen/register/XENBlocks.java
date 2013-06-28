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
package cn.lambdacraft.xen.register;

import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import cn.lambdacraft.core.block.CBCBlock;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.xen.block.BlockXenPortal;
import cn.lambdacraft.xen.block.BlockXenStone;

/**
 * @author Administrator
 *
 */
public class XENBlocks {
	public static Block[] blocks;
	public static BlockXenPortal portal;
	public static BlockXenStone stone;
	
	public static void init(Config conf) {
		portal = new BlockXenPortal(GeneralRegistry.getBlockId("xenportal", 0));
		stone = new BlockXenStone(GeneralRegistry.getBlockId("xenstone", 0));
		blocks = new Block[] {
				portal, stone
		};
		registerBlocks(blocks);
	}
	
	public static void registerBlocks(Block... blocks) {
		for (Block block : blocks) {
			ModLoader.registerBlock(block);
		}
	}
}
