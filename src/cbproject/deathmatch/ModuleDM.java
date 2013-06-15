package cbproject.deathmatch;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

import cbproject.core.CBCMod;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCKeyProcess;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.CBCSoundEvents;
import cbproject.crafting.items.ItemMaterial.EnumMaterial;
import cbproject.crafting.recipes.RecipeCrafter;
import cbproject.crafting.recipes.RecipeRepair;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.crafting.register.CBCItems;
import cbproject.deathmatch.entities.EntityARGrenade;
import cbproject.deathmatch.entities.EntityBattery;
import cbproject.deathmatch.entities.EntityCrossbowArrow;
import cbproject.deathmatch.entities.EntityHGrenade;
import cbproject.deathmatch.entities.EntityHornet;
import cbproject.deathmatch.entities.EntityMedkit;
import cbproject.deathmatch.entities.EntityRPGDot;
import cbproject.deathmatch.entities.EntityRocket;
import cbproject.deathmatch.entities.EntitySatchel;
import cbproject.deathmatch.entities.fx.EntityEgonRay;
import cbproject.deathmatch.entities.fx.EntityGaussRay;
import cbproject.deathmatch.entities.fx.EntityGaussRayColored;
import cbproject.deathmatch.gui.DMGuiElements;
import cbproject.deathmatch.keys.KeyMode;
import cbproject.deathmatch.keys.KeyReload;
import cbproject.deathmatch.network.NetChargerClient;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.network.NetMedFillerClient;
import cbproject.deathmatch.register.DMBlocks;
import cbproject.deathmatch.register.DMItems;
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

@Mod(modid="lcdm",name="LambdaCraft|DeathMatch",version="1.0.0.05")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class ModuleDM
{
	
	@Instance("lcdm")
	public static ModuleDM instance;
	
	@SidedProxy(clientSide="cbproject.deathmatch.proxy.ClientProxy",serverSide="cbproject.deathmatch.proxy.Proxy")
	public static cbproject.deathmatch.proxy.Proxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			for(String s : SOUND_WEAPONS)
				CBCSoundEvents.addSoundPath("cbc/weapons/" + s, "/cbproject/gfx/sounds/weapons/" + s);
			for(String s : SND_ENTITIES)
				CBCSoundEvents.addSoundPath("cbc/entities/" + s, "/cbproject/gfx/sounds/entities/" + s);
			CBCKeyProcess.addKey(new KeyBinding("key.reload", Keyboard.KEY_R), false, new KeyReload());
			CBCKeyProcess.addKey(new KeyBinding("key.mode", Keyboard.KEY_V), false, new KeyMode());
		}
		
		CBCNetHandler.addChannel(GeneralProps.NET_ID_DM, new NetDeathmatch());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_CHARGER_CL, new NetChargerClient());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_MEDFILLER_CL, new NetMedFillerClient());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_CHARGER, new DMGuiElements.ElementArmorCharger());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_HEALTH, new DMGuiElements.ElementHealthCharger());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_MEDFILLER, new DMGuiElements.ElementMedFiller());
		
	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		DMItems.init(CBCMod.config);
		DMBlocks.init(CBCMod.config);
		
		EntityRegistry.registerModEntity(EntityGaussRay.class, "gauss", GeneralProps.ENT_ID_GAUSS1, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityGaussRayColored.class, "gauss2", GeneralProps.ENT_ID_GAUSS2, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityEgonRay.class, "egonray", GeneralProps.ENT_ID_EGON_RAY, CBCMod.instance, 32, 1, true);
		EntityRegistry.registerModEntity(EntityARGrenade.class, "argrenade", GeneralProps.ENT_ID_ARGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHGrenade.class, "hgrenade", GeneralProps.ENT_ID_HGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHornet.class, "hornet", GeneralProps.ENT_ID_HORNET, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityRocket.class, "rocket", GeneralProps.ENT_ID_ROCKET, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityRPGDot.class, "dot", GeneralProps.ENT_ID_DOT, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntitySatchel.class, "satchel", GeneralProps.ENT_ID_SATCHEL, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityCrossbowArrow.class, "arrow", GeneralProps.ENT_ID_ARROW, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityMedkit.class, "medkit", GeneralProps.ENT_ID_MEDKIT, CBCMod.instance, 32, 5, true);
		EntityRegistry.registerModEntity(EntityBattery.class, "battery", GeneralProps.ENT_ID_BATTERY, CBCMod.instance, 32, 5, true);
		
		proxy.init();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
	
	public final static String SOUND_WEAPONS[]={
		
		"hgrenadepin",
		"hgrenadebounce",
		"plgun_c",
		"nmmclipa",
		"explode_a",
		"explode_b",
		"g_bounceb",
		"gunjam_a",
		"hksa",
		"hksb",
		"hksc",
		"nmmarr",
		"pyt_shota",
		"pyt_shotb",
		"pyt_cocka",
		"pyt_reloada",
		"sbarrela",
		"sbarrela_a",
		"sbarrelb",
		"sbarrelb_a",
		"scocka",
		"cbar_hita",
		"cbar_hitb",
		"cbar_hitboda",
		"cbar_hitbodb",
		"cbar_hitbodc",
		"reloada",
		"reloadb",
		"reloadc",
		"gaussb",
		"gauss_charge",
		"gauss_windupa",
		"gauss_windupb",
		"gauss_windupc",
		"gauss_windupd",
		"rocketfirea",
		"xbow_fire",
		"xbow_reload",
		"egon_run",
		"egon_windup",
		"egon_off",
		"rocket",
		"rocketfire",
		"glauncher",
		"glauncherb",
		"ag_firea",
		"ag_fireb",
		"ag_firec"
		
	};
	
	public static final String SND_ENTITIES[] = {
		"medkit",
		"battery",
		"suitcharge",
		"suitchargeno",
		"suitchargeok",
		"medshot",
		"medshotno",
		"medcharge"
	};
	
	static {
		
	}
	
}
