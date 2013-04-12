/**
 * 
 */
/**
 * @author WeAthFolD
 * @author mkpoli
 */
//This packet is the main frame of the mod.
package cbproject;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cbproject.configure.Config;
import cbproject.elements.blocks.BlocksRegister;
import cbproject.elements.blocks.Test_Block;
import cbproject.elements.commands.CommandChangeMode;
import cbproject.elements.items.ItemsRegister;
import cbproject.elements.items.Test_Item;
import cbproject.elements.renderers.CBCRenderManager;
import cbproject.misc.CBCKeyProcess;
import cbproject.misc.CBCPacketHandler;
import cbproject.misc.CBCSoundEvents;
import cbproject.misc.CCT;
import cbproject.utils.weapons.BulletManager;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
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
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="cbc",name="Crowbar Craft",version="0.0.0.1")
@NetworkMod(clientSideRequired=true,serverSideRequired=false, channels = {"CBCWeaponMode"}, packetHandler = CBCPacketHandler.class)

public class CBCMod
{
	public static ItemsRegister cbcItems;
	public static BlocksRegister cbcBlocks;;
	
	public static CreativeTabs cct = new CCT("CBCMod");
	public static CBCRenderManager renderManager;
	public static CBCKeyProcess keyProcess;
	
	public static final int RENDER_TYPE_TRIPMINE = 400;
	public static final int RENDER_TYPE_TRIPMINE_RAY = 401;
	
	private static Config config;
	
	@Instance("cbc")
	public static CBCMod CBCMod;
	
	@SidedProxy(clientSide="cbproject.proxy.ClientProxy",serverSide="cbproject.proxy.Proxy")
	public static cbproject.proxy.Proxy Proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		//声音的加载
		MinecraftForge.EVENT_BUS.register(new CBCSoundEvents());
		
		config=new Config(Init.getSuggestedConfigurationFile());
		config.SaveConfig();

	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		
		//以下是物品的注册（武器统一封装到cbcWeapons)
		cbcItems=new ItemsRegister(config);
		cbcBlocks = new BlocksRegister(config);
		keyProcess = new CBCKeyProcess(config);
		KeyBindingRegistry.registerKeyBinding(new CBCKeyProcess(config));
		
		Proxy.init();
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod", "en_US", "LambdaCraft");
		
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){

	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
		
	    CommandHandler commandManager = (CommandHandler)event.getServer().getCommandManager();
	    commandManager.registerCommand(new CommandChangeMode());

	}
	
	
}




