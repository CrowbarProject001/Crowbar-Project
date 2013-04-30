package cbproject.core;

import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import cbproject.core.configure.Config;
import cbproject.core.misc.CBCCreativeTab;
import cbproject.core.misc.CBCGuiHandler;
import cbproject.core.misc.CBCLanguage;
import cbproject.core.register.CBCBlocks;
import cbproject.core.register.CBCEventHandler;
import cbproject.core.register.CBCGeneralRegistry;
import cbproject.core.register.CBCItems;
import cbproject.core.register.CBCPacketHandler;
import cbproject.core.register.CBCRenderManager;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.crafting.recipes.RecipeWeapons;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/**
 * The main mod class.
 * @author WeAthFolD, mkpoli
 */

@Mod(modid="lambdacraft",name="lambdacraft",version="0.8.5.0a")
@NetworkMod(clientSideRequired=true,serverSideRequired=false,
serverPacketHandlerSpec = @SidedPacketHandler(channels = {"CBCWeapons", "CBCCrafter"}, packetHandler = CBCPacketHandler.class ))
public class CBCMod
{
	
	public static CBCItems cbcItems;
	public static CBCBlocks cbcBlocks;
	
	public static CBCWeaponInformation wpnInformation;
	public static RecipeWeapons recipeWeapons;
	
	public static CreativeTabs cct = new CBCCreativeTab("CBCMod");
	
	@SideOnly(Side.CLIENT)
	public static CBCRenderManager renderManager;
	
	private static Config config;
	
	@Instance("lambdacraft")
	public static CBCMod cbcMod;
	
	@SidedProxy(clientSide="cbproject.core.proxy.ClientProxy",serverSide="cbproject.core.proxy.Proxy")
	public static cbproject.core.proxy.Proxy Proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		//Events loading
		CBCEventHandler.registerAll();
		config=new Config(Init.getSuggestedConfigurationFile());

	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		
		CBCLanguage.bindLanguage("zh_CN");
		
		cbcBlocks = new CBCBlocks(config);
		cbcItems=new CBCItems(config);
		wpnInformation = new CBCWeaponInformation();
		recipeWeapons = new RecipeWeapons();
        NetworkRegistry.instance().registerGuiHandler(this, new CBCGuiHandler());
        
		CBCLanguage.addDefaultName("itemGroup.CBCMod", "LambdaCraft");
		CBCGeneralRegistry.register();
		Proxy.init();
		
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	    CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();
	}
	
}






