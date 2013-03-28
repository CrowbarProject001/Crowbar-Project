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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cbproject.configure.Config;
import cbproject.elements.blocks.BlocksRegister;
import cbproject.elements.blocks.Test_Block;
import cbproject.elements.events.process.weapons.HGrenadePinEvent;
import cbproject.elements.items.ItemsRegister;
import cbproject.elements.items.Test_Item;
import cbproject.elements.renderers.CBCRenderManager;
import cbproject.misc.CBCBindingRegistry;
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
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="cbc",name="Crowbar Craft",version="0.0.0.1")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)


public class CBCMod
{
	public static ItemsRegister cbcItems;
	public static BlocksRegister cbcBlocks;
	public static BulletManager bulletManager;
	public static CBCBindingRegistry bindingRegistry;
	
	public static CreativeTabs cct = new CCT("CBCMod");
	public static CBCRenderManager renderManager;
	Config config;
	
	public static final int RENDER_TYPE_ITEM_9MMHANDGUN = 502;
	
	@Instance("cbc")
	public static CBCMod CBCMod;
	@SidedProxy(clientSide="cbproject.proxy.ClientProxy",serverSide="chproject.proxy.Proxy")
	public static cbproject.proxy.Proxy Proxy;
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		//声音的加载
		MinecraftForge.EVENT_BUS.register(new CBCSoundEvents());
		MinecraftForge.EVENT_BUS.register(new HGrenadePinEvent());
		
		config=new Config(Init.getSuggestedConfigurationFile());
		config.SaveConfig();
		
	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		
		//以下是物品的注册（武器统一封装到cbcWeapons)
		cbcItems=new ItemsRegister(config);
		cbcBlocks = new BlocksRegister(config);
		bulletManager = new BulletManager();
		Proxy.init();
		Block test_block = new Test_Block(531,Material.anvil);
		Item test_item = new Test_Item(10000);
		
		bindingRegistry = new CBCBindingRegistry();
		KeyBindingRegistry.registerKeyBinding(bindingRegistry);
		
		//方块的注册
        //		ModLoader.addName(test_block , "Test_Block");
		LanguageRegistry.addName(test_block,"Test Block");
		ModLoader.registerBlock(test_block);
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.CBCMod", "en_US", "Crowbarcraft");
		
        //物品的名称
        LanguageRegistry.addName(test_item, "Test Item");
        
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
		
	}

	
}




