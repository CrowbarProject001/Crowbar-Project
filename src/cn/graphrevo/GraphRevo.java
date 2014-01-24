package cn.graphrevo;

import java.util.logging.Logger;

import cn.graphrevo.entity.EntityFrozen;
import cn.graphrevo.misc.CCTGraphRevo;
import cn.graphrevo.proxy.GRCommonProxy;
import cn.graphrevo.registry.GRBlocks;
import cn.graphrevo.registry.GRItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

/**
 * 这是整个Mod的主类，负责进行各种注册的调用，提供Mod的基本信息。
 */
@Mod(modid = "graph-revo", name = "Graphics Revolution", version = GraphRevo.VERSION) //Mod的基本信息
@NetworkMod(clientSideRequired = true, serverSideRequired = false) //网络mod的基本信息（会在以后的数据同步等用到）
public class GraphRevo {

	/**
	 * 版本号。
	 */
	public static final String VERSION = "0.0.1";
	
	/**
	 * 日志
	 */
	public static Logger log = Logger.getLogger("GraphRevo");
	
	/**
	 * 创造模式栏。
	 */
	public static CreativeTabs cct = new CCTGraphRevo("graph-revo");
	
	/**
	 * mod实例。
	 */
	@Instance("graph-revo")
	public static GraphRevo instance;
	
	/**
	 * 加载代理，由forge来辅助管理。
	 */
	@SidedProxy(
			clientSide = "cn.graphrevo.proxy.GRClientProxy",
			serverSide = "cn.graphrevo.proxy.GRCommonProxy"
	)
	public static GRCommonProxy proxy;
	
	/**
	 * 加载第一阶段。
	 */
	@EventHandler()
	public void preInit(FMLPreInitializationEvent event) {
		log.setParent(FMLLog.getLogger()); //设置为FML日志的子日志
		log.info("Starting GraphRevo " + GraphRevo.VERSION); //启动信息
		log.info("Copyright (c) Lambda Innovation, 2013");
		
		GRItems.init();
		GRBlocks.init();
		proxy.preInit();
	}
	
	/**
	 * 加载第二阶段。
	 */
	@EventHandler()
	public void init(FMLInitializationEvent event) {
		proxy.init();
		registerEntity(EntityFrozen.class, "entity_frozen");
	}
	
	static int nextEntityID = 0;
	private void registerEntity(Class<? extends Entity> entityClass, String name) {
		EntityRegistry.registerModEntity(entityClass, name, ++nextEntityID, GraphRevo.instance, 32, 3, true);
	}
	
	/**
	 * 加载第三阶段。
	 */
	@EventHandler()
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	
	/**
	 * 服务器加载（注册指令）
	 */
	@EventHandler()
	public void serverStarting(FMLServerStartingEvent event) {
		
	}

}
