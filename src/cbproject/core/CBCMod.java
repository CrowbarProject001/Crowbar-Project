package cbproject.core;

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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;


/**
 * The main mod class.
 * @author WeAthFolD, mkpoli
 */

@Mod(modid="lc",name="lambdacraft",version="0.9.0beta")
@NetworkMod(clientSideRequired=true,serverSideRequired=false,
serverPacketHandlerSpec = @SidedPacketHandler(channels = { "CBCCrafter" }, packetHandler = CBCPacketHandler.class ))
public class CBCMod
{ 
	public static RecipeWeapons recipeWeapons;
	
	public static CreativeTabs cct = new CBCCreativeTab("CBCMod");
	
	public static Config config;
	
	@Instance("lc")
	public static CBCMod instance;
	
	@SidedProxy(clientSide="cbproject.core.proxy.ClientProxy",serverSide="cbproject.core.proxy.Proxy")
	public static cbproject.core.proxy.Proxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		//Events, World gen, Config
		GameRegistry.registerWorldGenerator(new CBCOreGenerator());
		config=new Config(Init.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new CBCSoundEvents());
	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		//Blocks, Items, GUI Handler,Key Process.
		CBCBlocks.init(config);
		CBCItems.init(config);
		recipeWeapons = new RecipeWeapons();
		CBCAchievements.init(config);
        NetworkRegistry.instance().registerGuiHandler(this, new CBCGuiHandler());
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod", "LambdaCraft");
		proxy.init();
		
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
		config.SaveConfig();
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	    CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();
	}
	
}






