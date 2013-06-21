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
package cn.lambdacraft.xen.world.gen.structure;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * @author mkpoli
 *
 *
 * Mob 结构 生成
 */
public class MapGenXenBridge extends MapGenStructure {

	private List spawnList = new ArrayList();
	
	/**
	 * 在这里加入要生成的mobs
	 */        
	public MapGenXenBridge() {
		this.spawnList.add(new SpawnListEntry(EntitySkeleton.class, 10, 2, 3));
	}
	
	
	@Override
	protected boolean canSpawnStructureAtCoords(int par1, int par2) {
        int k = par1 >> 4;
        int l = par2 >> 4;
        this.rand.setSeed((long)(k ^ l << 4) ^ this.worldObj.getSeed());
        this.rand.nextInt();
        return this.rand.nextInt(3) != 0 ? false : (par1 != (k << 4) + 4 + this.rand.nextInt(8) ? false : par2 == (l << 4) + 4 + this.rand.nextInt(8));
	}

	
    public List getSpawnList()
    {
        return this.spawnList;
    }
    
    /* 结构一类的吧=w= */
    protected StructureStart getStructureStart(int par1, int par2)
    {
        return new StructureXenBridgeStart(this.worldObj, this.rand, par1, par2);
    }

}
