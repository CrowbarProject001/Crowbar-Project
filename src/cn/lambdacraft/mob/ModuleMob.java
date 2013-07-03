package cn.lambdacraft.mob;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.proxy.Proxy;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.mob.entities.EntityBarnacle;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.entities.EntitySnark;
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
		EntityRegistry.registerModEntity(EntitySnark.class, "snark", GeneralProps.ENT_ID_SNARK, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHeadcrab.class, "headcrab", GeneralProps.ENT_ID_HEADCRAB, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityBarnacle.class, "barnacle", GeneralProps.ENT_ID_BARNACLE, CBCMod.instance, 32, 5, false);
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
			"bcl_tongue", "bcl_chewa", "bcl_chewb", "bcl_chewc", "bcl_alert", "bcl_bite"};

}