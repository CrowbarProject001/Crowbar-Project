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
package cbproject.crafting;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import cbproject.core.CBCMod;
import cbproject.core.module.CBCSubModule;
import cbproject.core.module.ModuleInit;
import cbproject.core.module.ModuleInit.EnumInitType;
import cbproject.core.props.GeneralProps;
import cbproject.core.proxy.ClientProxy;
import cbproject.core.register.CBCAchievements;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCSoundEvents;
import cbproject.core.world.CBCOreGenerator;
import cbproject.crafting.gui.ElementCrafter;
import cbproject.crafting.register.CBCBlocks;
import cbproject.crafting.register.CBCItems;
import cbproject.mob.ModuleMob;
import cbproject.mob.entities.EntitySnark;
import cbproject.mob.register.CBCMobItems;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Administrator
 *
 */
@CBCSubModule("crafting")
public class ModuleCrafting {

	@Instance("cbc.crafting")
	public static ModuleCrafting instance;
	
	@ModuleInit(EnumInitType.PREINIT)
	public void preInit(FMLPreInitializationEvent Init)
	{
		GameRegistry.registerWorldGenerator(new CBCOreGenerator());
	}
	
	@ModuleInit(EnumInitType.INIT)
	public void init(FMLInitializationEvent Init){
		CBCBlocks.init(CBCMod.config);
		CBCItems.init(CBCMod.config);
		CBCAchievements.init(CBCMod.config);
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_CRAFTER, new ElementCrafter());
	}

	@ModuleInit(EnumInitType.POSTINIT)
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ModuleInit(EnumInitType.SVINIT)
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
	@ModuleInit(EnumInitType.CLINIT)
	public void registerRenderingThings(){
	}

}
