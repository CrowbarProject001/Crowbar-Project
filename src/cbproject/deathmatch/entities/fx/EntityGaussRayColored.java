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
package cbproject.deathmatch.entities.fx;

import net.minecraft.world.World;
import cbproject.core.utils.MotionXYZ;

/**
 * @author WeAthFolD
 *
 */
public class EntityGaussRayColored extends EntityGaussRay {

	/**
	 * @param begin
	 * @param par1World
	 */
	public EntityGaussRayColored(MotionXYZ begin, World par1World) {
		super(begin, par1World);
	}

	/**
	 * @param world
	 */
	public EntityGaussRayColored(World world) {
		super(world);
	}

}
