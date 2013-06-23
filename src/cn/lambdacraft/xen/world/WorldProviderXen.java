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

import cn.lambdacraft.xen.ModuleXen;
import cn.lambdacraft.xen.world.gen.ChunkProviderXen;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;

/**
 * @author Administrator
 *
 */
public class WorldProviderXen extends WorldProvider {
	public boolean inControl = false;
	
	@Override
	public String getDimensionName() {
		// TODO Auto-generated method stub
		return "XEN";
	}

	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.beach, 0.5f, 0.1f);
		this.dimensionId = ModuleXen.dimensionId;
	}
	
    /**
     * True if the player can respawn in this dimension (true = overworld, false = nether).
     */
    public boolean canRespawnHere()
    {
        return true;
    }
	
    @Override
    public IChunkProvider createChunkGenerator()
    {
    	return new ChunkProviderXen(worldObj, worldObj.getSeed());
    }

	@Override
	public String getSaveFolder() {
		// TODO Auto-generated method stub
		return "DIM_XEN";
	}

	@Override
	public String getWelcomeMessage() {
		// TODO Auto-generated method stub
		return "Welcome to the borderworld, Xen";
	}

	@Override
	public String getDepartMessage() {
		if (inControl)
			return "The borderworld, Xen, is in our control, for the time being... thanks to you.";
		else
			return "See you next time";
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		// TODO Auto-generated method stub
		return false;
	}
    
    
}
