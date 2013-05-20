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

import java.util.logging.Logger;

import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.misc.CBCCreativeTab;
import cbproject.core.misc.Config;
import cbproject.core.register.CBCAchievements;
import cbproject.core.register.CBCBlocks;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCItems;
import cbproject.core.register.CBCPacketHandler;
import cbproject.core.register.CBCSoundEvents;
import cbproject.core.world.CBCOreGenerator;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.gui.ElementCrafter;
import cbproject.crafting.recipes.RecipeWeapons;
import cpw.mods.fml.common.FMLLog;
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
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="lc",name="LambdaCraft",version="0.9.9 pre")
@NetworkMod(clientSideRequired=true,serverSideRequired=false,
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "CBCCrafter" }, packetHandler = CBCPacketHandler.class ),
clientPacketHandlerSpec = @SidedPacketHandler(channels = {"CBCExplosion"}, packetHandler = CBCPacketHandler.class ))
public class CBCMod
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
	
	@Instance("lc")
	public static CBCMod instance;
	
	/**
	 * 加载代理。
	 */
	@SidedProxy(clientSide="cbproject.core.proxy.ClientProxy",serverSide="cbproject.core.proxy.Proxy")
	public static cbproject.core.proxy.Proxy proxy;
	
	/**
	 * 预加载（设置、世界生成、注册Event）
	 * @param Init
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		config=new Config(Init.getSuggestedConfigurationFile());
		GameRegistry.registerWorldGenerator(new CBCOreGenerator());
		MinecraftForge.EVENT_BUS.register(new CBCSoundEvents());
	} 

	/**
	 * 加载（方块、物品、网络处理、其他)
	 * @param Init
	 */
	@Init
	public void init(FMLInitializationEvent Init){
		//Blocks, Items, GUI Handler,Key Process.
		CBCBlocks.init(config);
		CBCItems.init(config);
		CBCAchievements.init(config);
		CBCGuiHandler.addGuiElement(TileEntityWeaponCrafter.class, new ElementCrafter());
        NetworkRegistry.instance().registerGuiHandler(this, new CBCGuiHandler());
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod", "LambdaCraft");
		proxy.init();
		
	}

	/**
	 * 加载后（保存设置）
	 * @param Init
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
		config.SaveConfig();
	}

	/**
	 * 服务器加载（注册指令）
	 * @param event
	 */
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	    CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();
	}
	
}






