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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开原协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.core;

import java.util.EnumSet;
import java.util.logging.Logger;

import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.energy.EnergyNet;
import cbproject.core.misc.CBCCreativeTab;
import cbproject.core.misc.Config;
import cbproject.core.network.NetExplosion;
import cbproject.core.props.GeneralProps;
import cbproject.core.proxy.Proxy;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCModuleRegisty;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.CBCSoundEvents;
import cbproject.crafting.ModuleCrafting;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.deathmatch.ModuleDM;
import cbproject.intergration.ic2.ModuleIC2;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="lc",name="LambdaCraft",version="0.9.9 pre")
@NetworkMod(clientSideRequired=true,serverSideRequired=false, channels = {GeneralProps.NET_CHANNEL_CLIENT, GeneralProps.NET_CHANNEL_SERVER}, packetHandler = CBCNetHandler.class)
public class CBCMod implements ITickHandler
{ 
	
	/**
	 *  日志
	 */
	public static Logger log =FMLLog.getLogger();
	
	/**
	 * 武器制作机的合成表。
	 */
	public static RecipeWeapons recipeWeapons;
	
	/**
	 * Creative Tab.
	 */
	public static CreativeTabs cct = new CBCCreativeTab("CBCMod");
	
	/**
	 * 公用设置。
	 */
	public static Config config;
	
	/**
	 * 子模块注册。
	 */
	public static CBCModuleRegisty module;
	
	@Instance("lc")
	public static CBCMod instance;
	
	/**
	 * 加载代理。
	 */
	@SidedProxy(clientSide="cbproject.core.proxy.ClientProxy",serverSide="cbproject.core.proxy.Proxy")
	public static cbproject.core.proxy.Proxy proxy;
	
	/**
	 * 预加载（设置、世界生成、注册Event）
	 * @param event
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		config=new Config(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new CBCSoundEvents());
		EnergyNet.initialize();
		TickRegistry.registerTickHandler(this, Side.CLIENT);
		TickRegistry.registerTickHandler(this, Side.SERVER);
		
		module = new CBCModuleRegisty();
		CBCModuleRegisty.registerModule(ModuleCrafting.class.getName());
		CBCModuleRegisty.registerModule(ModuleIC2.class.getName());
		CBCModuleRegisty.registerModule(ModuleDM.class.getName());
		module.preInit(event);
	} 

	/**
	 * 加载（方块、物品、网络处理、其他)
	 * @param Init
	 */
	@Init
	public void init(FMLInitializationEvent Init){
		//Blocks, Items, GUI Handler,Key Process.
        NetworkRegistry.instance().registerGuiHandler(this, new CBCGuiHandler());
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod", "LambdaCraft");
		CBCNetHandler.addChannel(GeneralProps.NET_ID_EXPLOSION, new NetExplosion());
		GeneralProps.loadProps(CBCMod.config);
		proxy.init();
		module.init(Init);
		if(Proxy.isRendering()){
			module.clientInit();
		}
	}

	/**
	 * 加载后（保存设置）
	 * @param Init
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
		config.SaveConfig();
		module.postInit(Init);
	}

	/**
	 * 服务器加载（注册指令）
	 * @param event
	 */
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	    CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();
	    module.serverStarting(event);
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		World world = (World) tickData[0];
		Proxy.profilerEndStartSection("EnergyNet");
		EnergyNet.onTick(world);
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		Proxy.profilerEndStartSection("EnergyNet");
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel() {
		return "LambdaCraft ticks";
	}
	
}






