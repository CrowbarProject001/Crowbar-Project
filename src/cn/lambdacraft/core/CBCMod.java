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
package cn.lambdacraft.core;

import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.energy.EnergyNet;
import cn.lambdacraft.core.keys.KeyUse;
import cn.lambdacraft.core.misc.CBCCreativeTab;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.network.NetExplosion;
import cn.lambdacraft.core.network.NetKeyUsing;
import cn.lambdacraft.core.props.GeneralProps;
import cn.lambdacraft.core.proxy.Proxy;
import cn.lambdacraft.core.register.CBCGuiHandler;
import cn.lambdacraft.core.register.CBCKeyProcess;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.crafting.recipes.RecipeWeapons;
import cn.lambdacraft.intergration.ic2.IC2Module;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Loader;
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
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "LambdaCraft", name = "LambdaCraft Core", version = CBCMod.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { GeneralProps.NET_CHANNEL_CLIENT }, packetHandler = CBCNetHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { GeneralProps.NET_CHANNEL_SERVER }, packetHandler = CBCNetHandler.class))
public class CBCMod implements ITickHandler {
	
	public static final String VERSION = "1.1.0 dev";
	
	public static final String DEPENDENCY_CORE = "required-after:LambdaCraft@" + VERSION;
	
	@SideOnly(Side.CLIENT)
	private Minecraft mc;

	/**
	 * 日志
	 */
	public static Logger log = FMLLog.getLogger();

	/**
	 * 武器制作机的合成表。
	 */
	public static RecipeWeapons recipeWeapons;

	/**
	 * Creative Tab.
	 */
	public static CreativeTabs cct = new CBCCreativeTab("CBCMod", 0),
			cctMisc = new CBCCreativeTab("CBCMisc", 1);

	/**
	 * 公用设置。
	 */
	public static Config config;

	@Instance("LambdaCraft")
	public static CBCMod instance;

	/**
	 * 加载代理。
	 */
	@SidedProxy(clientSide = "cbproject.core.proxy.ClientProxy", serverSide = "cbproject.core.proxy.Proxy")
	public static cn.lambdacraft.core.proxy.Proxy proxy;

	public static boolean ic2Installed = true;

	/**
	 * 预加载（设置、世界生成、注册Event）
	 * 
	 * @param event
	 */
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		config = new Config(event.getSuggestedConfigurationFile());
		EnergyNet.initialize();

		TickRegistry.registerTickHandler(this, Side.CLIENT);
		TickRegistry.registerTickHandler(this, Side.SERVER);

		if (FMLCommonHandler.instance().getSide().isClient()) {
			MinecraftForge.EVENT_BUS.register(new CBCSoundEvents());
			CBCKeyProcess.addKey(new KeyBinding("key.cbcuse", Keyboard.KEY_F),
					true, new KeyUse());
		}

	}

	/**
	 * 加载（方块、物品、网络处理、其他)
	 * 
	 * @param Init
	 */
	@Init
	public void init(FMLInitializationEvent Init) {
		Class a;
		ic2Installed = IC2Module.init();
		System.out.println("LambdaCraft IC2 Intergration Module STATE : " + ic2Installed);
		// Blocks, Items, GUI Handler,Key Process.
		NetworkRegistry.instance()
				.registerGuiHandler(this, new CBCGuiHandler());
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod",
				"LambdaCraft");
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMisc",
				"LambdaCraft:Misc");
		CBCNetHandler.addChannel(GeneralProps.NET_ID_EXPLOSION,
				new NetExplosion());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_USE, new NetKeyUsing());
		GeneralProps.loadProps(CBCMod.config);
		Vec3[][] vs = RenderUtils.shapeSphere.getVertices();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		StringBuilder b = new StringBuilder("The Vertices : \n");
		for(int i = 0; i < vs.length; i ++) {
			b.append("[");
			for(int j = 0; j < vs[i].length; j++) {
				b.append("(").append(df.format((float)vs[i][j].xCoord)).append(",").append(df.format((float)vs[i][j].yCoord)).append(",").append(df.format((float)vs[i][j].zCoord)).append(")");
			}
			b.append("],\n");
		}
		System.out.println(b.toString());
		proxy.init();
	}

	/**
	 * 加载后（保存设置）
	 * 
	 * @param Init
	 */
	@PostInit
	public void postInit(FMLPostInitializationEvent Init) {
		config.SaveConfig();

	}

	/**
	 * 服务器加载（注册指令）
	 * 
	 * @param event
	 */
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
		CommandHandler commandManager = (CommandHandler) event.getServer()
				.getCommandManager();

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
