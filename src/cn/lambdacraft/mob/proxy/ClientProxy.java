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
package cn.lambdacraft.mob.proxy;

import cn.lambdacraft.mob.client.models.ModelHLZombie;
import cn.lambdacraft.mob.client.models.ModelHeadcrab;
import cn.lambdacraft.mob.client.models.ModelSnark;
import cn.lambdacraft.mob.client.render.RenderBarnacle;
import cn.lambdacraft.mob.entities.EntityBarnacle;
import cn.lambdacraft.mob.entities.EntityHLZombie;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.entities.EntitySnark;
import net.minecraft.client.renderer.entity.RenderLiving;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 * 
 */
public class ClientProxy extends Proxy {

	@Override
	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySnark.class,
				new RenderLiving(new ModelSnark(), 0.2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeadcrab.class,
				new RenderLiving(new ModelHeadcrab(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBarnacle.class, new RenderBarnacle());
		RenderingRegistry.registerEntityRenderingHandler(EntityHLZombie.class, new RenderLiving(new ModelHLZombie(), 0.5F));
	}
	
}
