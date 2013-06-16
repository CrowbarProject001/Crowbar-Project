package cbproject.mob;

import cbproject.core.CBCMod;
import cbproject.core.props.GeneralProps;
import cbproject.core.proxy.Proxy;
import cbproject.core.register.CBCSoundEvents;
import cbproject.mob.entities.EntitySnark;
import cbproject.mob.register.CBCMobItems;
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

@Mod(modid = "LambdaCraft|Living", name = "LambdaCraft Living", version = CBCMod.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModuleMob {

	@SidedProxy(clientSide = "cbproject.mob.proxy.ClientProxy", serverSide = "cbproject.mob.proxy.Proxy")
	public static cbproject.mob.proxy.Proxy proxy;

	@Instance("LambdaCraft|Living")
	public static ModuleMob instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent Init) {
		if (Proxy.isRendering())
			for (String s : SND_MOBS) {
				CBCSoundEvents.addSoundPath("cbc/mobs/" + s,
						"/cbproject/gfx/sounds/mobs/" + s);
			}
	}

	@Init
	public void init(FMLInitializationEvent Init) {
		CBCMobItems.init(CBCMod.config);
		EntityRegistry.registerModEntity(EntitySnark.class, "snark",
				GeneralProps.ENT_ID_SNARK, CBCMod.instance, 32, 3, true);
		proxy.init();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init) {
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}

	public static final String SND_MOBS[] = { "sqk_blast", "sqk_hunta",
			"sqk_huntb", "sqk_huntc", "sqk_die", "sqk_deploy" };

}