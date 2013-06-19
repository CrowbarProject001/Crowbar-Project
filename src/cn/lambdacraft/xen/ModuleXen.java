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
package cn.lambdacraft.xen;

import net.minecraftforge.common.MinecraftForge;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.Proxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author mkpoli
 *
 */
@Mod(modid = "LambdaCraft|Xen", name = "LambdaCraft Xen", version = CBCMod.VERSION, dependencies = CBCMod.DEPENDENCY_CORE)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModuleXen {
	
	public static final String DEPENCY_XEN = "required-after:LambdaCraft|World@" + CBCMod.VERSION;
	
	@SidedProxy(clientSide = "cn.lambdacraft.xen.proxy.ClientProxy", serverSide = "cn.lambdacraft.xen.proxy.Proxy")
	public static cn.lambdacraft.xen.proxy.Proxy proxy;
	
	@Instance("LambdaCraft|Xen")
	public static ModuleXen instance;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init) {
	}
}
