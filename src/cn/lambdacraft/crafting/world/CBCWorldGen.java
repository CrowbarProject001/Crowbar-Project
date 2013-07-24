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
package cn.lambdacraft.crafting.world;

import java.util.Random;

import cn.lambdacraft.crafting.register.CBCBlocks;


import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenVillage;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * 喜闻乐见的矿石生成。
 * 
 * @author Mkpoli
 */
public class CBCWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	private void generateEnd(World world, Random random, int i, int j) {
	}

	private void generateSurface(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		for (int k = 0; k < 10; k++) {
			int uraniumOreXCoord = i + random.nextInt(16);
			int uraniumOreYCoord = random.nextInt(35);
			int uraniumOreZCoord = j + random.nextInt(16);
			(new WorldGenMinable(CBCBlocks.uraniumOre.blockID, 3)).generate(
					world, random, uraniumOreXCoord, uraniumOreYCoord,
					uraniumOreZCoord);
		}
		for (int k = 0; k < 15; k++) {
			int tinOreXCoord = i + random.nextInt(16);
			int tinOreYCoord = random.nextInt(48);
			int tinOreZCoord = j + random.nextInt(16);
			(new WorldGenMinable(CBCBlocks.oreTin.blockID, 3)).generate(world,
					random, tinOreXCoord, tinOreYCoord, tinOreZCoord);
		}
		for (int k = 0; k < 15; k++) {
			int copperOreXCoord = i + random.nextInt(16);
			int copperOreYCoord = random.nextInt(48);
			int copperOreZCoord = j + random.nextInt(16);
			(new WorldGenMinable(CBCBlocks.oreCopper.blockID, 3)).generate(
					world, random, copperOreXCoord, copperOreYCoord,
					copperOreZCoord);
		}
		for (int k = 0; k < 50; k++) {
			int copperOreXCoord = i + random.nextInt(16);
			int copperOreYCoord = random.nextInt(128);
			int copperOreZCoord = j + random.nextInt(16);
			(new MapGenVillage()).generateStructuresInChunk(world, random, i, j);
		}
	}

	private void generateNether(World world, Random random, int i, int j) {
	}

}
