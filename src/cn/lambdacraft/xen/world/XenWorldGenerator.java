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
package cn.lambdacraft.xen.world;

import java.util.Random;

import cn.lambdacraft.xen.ModuleXen;
import cn.lambdacraft.xen.register.XENBlocks;

import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import cpw.mods.fml.common.IWorldGenerator;

/**
 * @author mkpoli
 *
 */
public class XenWorldGenerator implements IWorldGenerator {

	private byte[] densities;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		world.setBlock(chunkX*16 + random.nextInt(16), 100, chunkZ*16 + random.nextInt(16), 5);
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
//		case ModuleXen.dimensionId:
//			generateXen(world, random, chunkX * 16, chunkZ * 16);
		}
	}

	/**
	 * @param world
	 * @param random
	 * @param i
	 * @param j
	 */
	private void generateXen(World world, Random random, int i, int j) {
		
		
	}

	/**
	 * @param world
	 * @param random
	 * @param i
	 * @param j
	 */
	private void generateEnd(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param world
	 * @param random
	 * @param i
	 * @param j
	 */
	private void generateSurface(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param world
	 * @param random
	 * @param i
	 * @param j
	 */
	private void generateNether(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	
}
