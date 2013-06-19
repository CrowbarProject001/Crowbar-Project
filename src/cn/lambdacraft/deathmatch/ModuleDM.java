package cn.lambdacraft.deathmatch;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.props.GeneralProps;
import cn.lambdacraft.core.register.CBCGuiHandler;
import cn.lambdacraft.core.register.CBCKeyProcess;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.crafting.ModuleCrafting;
import cn.lambdacraft.deathmatch.entities.EntityARGrenade;
import cn.lambdacraft.deathmatch.entities.EntityBattery;
import cn.lambdacraft.deathmatch.entities.EntityCrossbowArrow;
import cn.lambdacraft.deathmatch.entities.EntityHGrenade;
import cn.lambdacraft.deathmatch.entities.EntityHornet;
import cn.lambdacraft.deathmatch.entities.EntityMedkit;
import cn.lambdacraft.deathmatch.entities.EntityRPGDot;
import cn.lambdacraft.deathmatch.entities.EntityRocket;
import cn.lambdacraft.deathmatch.entities.EntitySatchel;
import cn.lambdacraft.deathmatch.entities.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.entities.fx.EntityGaussRay;
import cn.lambdacraft.deathmatch.entities.fx.EntityGaussRayColored;
import cn.lambdacraft.deathmatch.entities.fx.GaussParticleFX;
import cn.lambdacraft.deathmatch.gui.DMGuiElements;
import cn.lambdacraft.deathmatch.keys.KeyMode;
import cn.lambdacraft.deathmatch.keys.KeyReload;
import cn.lambdacraft.deathmatch.network.NetChargerClient;
import cn.lambdacraft.deathmatch.network.NetDeathmatch;
import cn.lambdacraft.deathmatch.network.NetMedFillerClient;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMEventHandler;
import cn.lambdacraft.deathmatch.register.DMItems;

import cpw.mods.fml.common.FMLCommonHandler;
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
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "LambdaCraft|DeathMatch", name = "LambdaCraft DeathMatch", version = CBCMod.VERSION, dependencies = ModuleCrafting.DEPENCY_CRAFTING)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModuleDM {

	@Instance("LambdaCraft|DeathMatch")
	public static ModuleDM instance;

	@SidedProxy(clientSide = "cn.lambdacraft.deathmatch.proxy.ClientProxy", serverSide = "cn.lambdacraft.deathmatch.proxy.Proxy")
	public static cn.lambdacraft.deathmatch.proxy.Proxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent Init) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			for (String s : SOUND_WEAPONS)
				CBCSoundEvents.addSoundPath("cbc/weapons/" + s,
						"/cbproject/gfx/sounds/weapons/" + s);
			for (String s : SND_ENTITIES)
				CBCSoundEvents.addSoundPath("cbc/entities/" + s,
						"/cbproject/gfx/sounds/entities/" + s);
			CBCKeyProcess.addKey(new KeyBinding("key.reload", Keyboard.KEY_R),
					false, new KeyReload());
			CBCKeyProcess.addKey(new KeyBinding("key.mode", Keyboard.KEY_V),
					false, new KeyMode());
		}

		MinecraftForge.EVENT_BUS.register(new DMEventHandler());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_DM, new NetDeathmatch());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_CHARGER_CL,
				new NetChargerClient());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_MEDFILLER_CL,
				new NetMedFillerClient());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_CHARGER,
				new DMGuiElements.ElementArmorCharger());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_HEALTH,
				new DMGuiElements.ElementHealthCharger());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_MEDFILLER,
				new DMGuiElements.ElementMedFiller());
	}

	@Init
	public void init(FMLInitializationEvent Init) {
		DMItems.init(CBCMod.config);
		DMBlocks.init(CBCMod.config);
		
		EntityRegistry.registerModEntity(EntityGaussRay.class, "gauss",
				GeneralProps.ENT_ID_GAUSS1, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityGaussRayColored.class, "gauss2",
				GeneralProps.ENT_ID_GAUSS2, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityEgonRay.class, "egonray",
				GeneralProps.ENT_ID_EGON_RAY, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityARGrenade.class, "argrenade",
				GeneralProps.ENT_ID_ARGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHGrenade.class, "hgrenade",
				GeneralProps.ENT_ID_HGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHornet.class, "hornet",
				GeneralProps.ENT_ID_HORNET, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityRocket.class, "rocket",
				GeneralProps.ENT_ID_ROCKET, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityRPGDot.class, "dot",
				GeneralProps.ENT_ID_DOT, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntitySatchel.class, "satchel",
				GeneralProps.ENT_ID_SATCHEL, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityCrossbowArrow.class, "arrow",
				GeneralProps.ENT_ID_ARROW, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityMedkit.class, "medkit",
				GeneralProps.ENT_ID_MEDKIT, CBCMod.instance, 32, 5, true);
		EntityRegistry.registerModEntity(EntityBattery.class, "battery",
				GeneralProps.ENT_ID_BATTERY, CBCMod.instance, 32, 5, true);
		EntityRegistry.registerModEntity(GaussParticleFX.class, "gaussp",
				GeneralProps.ENT_ID_GAUSS_PARTICLE, CBCMod.instance, 32, 5, true);

		proxy.init();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init) {
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}

	public final static String SOUND_WEAPONS[] = {

	"hgrenadepin", "hgrenadebounce", "plgun_c", "nmmclipa", "explode_a",
			"explode_b", "g_bounceb", "gunjam_a", "hksa", "hksb", "hksc",
			"nmmarr", "pyt_shota", "pyt_shotb", "pyt_cocka", "pyt_reloada",
			"sbarrela", "sbarrela_a", "sbarrelb", "sbarrelb_a", "scocka",
			"cbar_hita", "cbar_hitb", "cbar_hitboda", "cbar_hitbodb",
			"cbar_hitbodc", "reloada", "reloadb", "reloadc", "gaussb",
			"gauss_charge", "gauss_windupa", "gauss_windupb", "gauss_windupc",
			"gauss_windupd", "rocketfirea", "xbow_fire", "xbow_reload",
			"egon_run", "egon_windup", "egon_off", "rocket", "rocketfire",
			"glauncher", "glauncherb", "ag_firea", "ag_fireb", "ag_firec"

	};

	public static final String SND_ENTITIES[] = { "medkit", "battery",
			"suitcharge", "suitchargeno", "suitchargeok", "medshot",
			"medshotno", "medcharge" };

}
