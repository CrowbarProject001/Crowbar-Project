package cbproject;

import cbproject.configure.Config;
import cbproject.elements.blocks.CBCBlocks;
import cbproject.elements.items.ItemsRegister;
import cbproject.elements.renderers.CBCRenderManager;
import cbproject.misc.CBCEvents;
import cbproject.misc.CBCGuiHandler;
import cbproject.misc.CBCKeyProcess;
import cbproject.misc.CBCPacketHandler;
import cbproject.misc.CCT;
import cbproject.utils.CBCWeaponInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


/**
 * The main mod class.
 * @author WeAthFolD, mkpoli
 */

@Mod(modid="lambdacraft",name="lambdacraft",version="0.0.0.1")
@NetworkMod(clientSideRequired=true,serverSideRequired=false, channels = {"CBCWeaponMode", "CBCReload"}, packetHandler = CBCPacketHandler.class)
public class CBCMod
{
	
	public static ItemsRegister cbcItems;
	public static CBCBlocks cbcBlocks;;
	public static CreativeTabs cct = new CCT("CBCMod");
	public static CBCRenderManager renderManager;
	public static CBCKeyProcess keyProcess;
	public static CBCWeaponInformation wpnInformation;
	
	public static final int RENDER_TYPE_TRIPMINE = RenderingRegistry.getNextAvailableRenderId();;
	public static final int RENDER_TYPE_TRIPMINE_RAY = RenderingRegistry.getNextAvailableRenderId();;
	public static final int RENDER_TYPE_EMPTY = RenderingRegistry.getNextAvailableRenderId();
	
	private static Config config;
	
	@Instance("lambdacraft")
	public static CBCMod cbcMod;
	
	@SidedProxy(clientSide="cbproject.proxy.ClientProxy",serverSide="cbproject.proxy.Proxy")
	public static cbproject.proxy.Proxy Proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		//Events loading
		MinecraftForge.EVENT_BUS.register(new CBCEvents());
		
		config=new Config(Init.getSuggestedConfigurationFile());
		config.SaveConfig();

	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		
		//Items, Blocks, Recipes, Key registers, Weapon information loads.
		cbcItems=new ItemsRegister(config);
		cbcBlocks = new CBCBlocks(config);
		keyProcess = new CBCKeyProcess(config);
		wpnInformation = new CBCWeaponInformation();
		KeyBindingRegistry.registerKeyBinding(new CBCKeyProcess(config));
        NetworkRegistry.instance().registerGuiHandler(this, new CBCGuiHandler());
		Proxy.init();
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod", "en_US", "LambdaCraft");
		
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	    CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();

	}
	
	
}






