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

import net.minecraftforge.common.MinecraftForge;
import cbproject.core.CBCMod;
import cbproject.core.props.GeneralProps;
import cbproject.core.proxy.Proxy;
import cbproject.core.register.CBCAchievements;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.CBCSoundEvents;
import cbproject.core.world.CBCOreGenerator;
import cbproject.crafting.entities.EntitySpray;
import cbproject.crafting.gui.CRGuiElements;
import cbproject.crafting.network.NetCrafterClient;
import cbproject.crafting.register.CBCBlocks;
import cbproject.crafting.register.CBCItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Administrator
 *
 */
@Mod(modid="lcwrld",name="LambdaCraft|World",version="1.0.0pre1")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class ModuleCrafting {

	@SidedProxy(clientSide="cbproject.crafting.proxy.ClientProxy",serverSide="cbproject.crafting.proxy.Proxy")
	public static cbproject.crafting.proxy.Proxy proxy;
	
	@Instance("lcwrld")
	public static ModuleCrafting instance;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		for(String s : SND_ENTITIES)
			CBCSoundEvents.addSoundPath("cbc/entities/" + s, "/cbproject/gfx/sounds/entities/" + s);
		GameRegistry.registerWorldGenerator(new CBCOreGenerator());
		if(Proxy.isRendering())
			MinecraftForge.EVENT_BUS.register(new CRClientEventHandler());
	}
	
	@Init
	public void init(FMLInitializationEvent Init){
		CBCBlocks.init(CBCMod.config);
		CBCItems.init(CBCMod.config);
		CBCAchievements.init(CBCMod.config);
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_CRAFTER, new CRGuiElements.ElementCrafter());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_GENFIRE, new CRGuiElements.ElementGenFire());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_GENLAVA, new CRGuiElements.ElementGenLava());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_GENSOLAR, new CRGuiElements.ElementGenSolar());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_CRAFTER_CL, new NetCrafterClient());
		EntityRegistry.registerModEntity(EntitySpray.class, "entityart", 16, CBCMod.instance, 250, 5, true);
		proxy.init();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
	public static final String SND_ENTITIES[] = {
		"sprayer"
	};

}
