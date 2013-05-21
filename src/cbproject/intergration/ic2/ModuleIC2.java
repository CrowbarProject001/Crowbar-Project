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
package cbproject.intergration.ic2;

import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cbproject.core.CBCMod;
import cbproject.core.module.CBCSubModule;
import cbproject.core.module.ModuleInit;
import cbproject.core.module.ModuleInit.EnumInitType;

/**
 * 
 * @author WeAthFolD
 *
 */
@CBCSubModule("ic2")
public class ModuleIC2 {
	
	@Instance("cbc.ic2")
	public static ModuleIC2 instance;

	@ModuleInit(EnumInitType.PREINIT)
	public void preInit(FMLPreInitializationEvent event){
		System.out.println("IC2 intergration preInit");
	}
	
	@ModuleInit(EnumInitType.INIT)
	public void init(FMLInitializationEvent event){
		System.out.println("IC2 intergration init");
	}
	
	@ModuleInit(EnumInitType.POSTINIT)
	public void postInit(FMLPostInitializationEvent event){
		System.out.println("IC2 intergration postInit");
	}
	
	@ModuleInit(EnumInitType.SVINIT)
	public void serverStarting(FMLServerStartingEvent event){
		System.out.println("IC2 intergration server");
	}
	
	@ModuleInit(EnumInitType.CLINIT)
	public void clientInit(){
		System.out.println("IC2 intergration clInit");
	}

}
