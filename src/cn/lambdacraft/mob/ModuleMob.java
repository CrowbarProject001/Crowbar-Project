package cn.lambdacraft.mob;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.proxy.Proxy;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.core.utils.BlockPos;
import cn.lambdacraft.mob.blocks.TileSentryRay;
import cn.lambdacraft.mob.entities.EntityBarnacle;
import cn.lambdacraft.mob.entities.EntityHLZombie;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.entities.EntitySentry;
import cn.lambdacraft.mob.entities.EntitySnark;
import cn.lambdacraft.mob.network.NetSentrySync;
import cn.lambdacraft.mob.register.CBCMobBlocks;
import cn.lambdacraft.mob.register.CBCMobItems;
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

@Mod(modid = "LambdaCraft|Living", name = "LambdaCraft Living", version = CBCMod.VERSION, dependencies = CBCMod.DEPENDENCY_CORE)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModuleMob {

	/**
	 * 玩家对应当前选定的Sentry的映射表。
	 */
	public static HashMap<EntityPlayer, EntitySentry> syncMap = new HashMap();
	/**
	 * 放置时临时采用的映射表。
	 */
	public static HashMap<EntityPlayer, TileSentryRay> tileMap = new HashMap();
	/**
	 * 放置着对应方块位置的映射表。
	 */
	public static HashMap<BlockPos, EntityPlayer> playerMap = new HashMap();
	
	@SidedProxy(clientSide = "cn.lambdacraft.mob.proxy.ClientProxy", serverSide = "cn.lambdacraft.mob.proxy.Proxy")
	public static cn.lambdacraft.mob.proxy.Proxy proxy;

	@Instance("LambdaCraft|Living")
	public static ModuleMob instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent Init) {
		if (Proxy.isRendering())
			for (String s : SND_MOBS) {
				CBCSoundEvents.addSoundPath("cbc/mobs/" + s,
						"/mods/lambdacraft/sounds/mobs/" + s);
			}
	}

	@Init
	public void init(FMLInitializationEvent Init) {
		CBCMobItems.init(CBCMod.config);
		CBCMobBlocks.init();
		CBCNetHandler.addChannel(GeneralProps.NET_ID_SENTRYSYNCER, new NetSentrySync());
		EntityRegistry.registerModEntity(EntitySnark.class, "snark", GeneralProps.ENT_ID_SNARK, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHeadcrab.class, "headcrab", GeneralProps.ENT_ID_HEADCRAB, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityBarnacle.class, "barnacle", GeneralProps.ENT_ID_BARNACLE, CBCMod.instance, 32, 5, false);
		EntityRegistry.registerModEntity(EntityHLZombie.class, "hlzombie", GeneralProps.ENT_ID_ZOMBIE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntitySentry.class, "turret", GeneralProps.ENT_ID_TURRET, CBCMod.instance, 32, 3, true);
		proxy.init();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init) {
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}

	public static final String SND_MOBS[] = { "sqk_blast", "sqk_hunta",
		"sqk_huntb", "sqk_huntc", "sqk_die", "sqk_deploy", "hc_attacka", "hc_attackb",
		"hc_attackc", "hc_idlea", "hc_idleb", "hc_idlec", "hc_idled", "hc_idlee",
		"hc_diea", "hc_dieb", "hc_paina", "hc_painb", "hc_painc",
		"bcl_tongue", "bcl_chewa", "bcl_chewb", "bcl_chewc", "bcl_alert", "bcl_bite", "zo_alerta",
		"zo_alertb", "zo_alertc", "zo_attacka", "zo_attackb", "zo_claw_missa", "zo_claw_missb", 
		"zo_claw_strikea", "zo_claw_strikeb", "zo_claw_strikec", "zo_idlea", "zo_idleb", "zo_idlec",
		"zo_paina", "zo_painb", "zo_moan_loopa", "zo_moan_loopb", "zo_moan_loopc", "zo_moan_loopd", "zo_diea", "zo_dieb", "zo_diec"};

}