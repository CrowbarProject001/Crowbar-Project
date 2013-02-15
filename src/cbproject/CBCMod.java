/**
 * 
 */
/**
 * @author Administrator
 *
 */
//This packet is the main frame of the mod.
package cbproject;

import java.util.Random;
import org.omg.CORBA.PUBLIC_MEMBER;
import cbproject.elements.Blocks.Test_Block;
import cbproject.elements.Items.Test_Item;
import cbproject.elements.Items.weapons.Weapons;
import cbproject.elements.Items.weapons.Weapon_crowbar;
import cbproject.Configure.Config;
import cbproject.Misc.CCT;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

@Mod(modid="cbc",name="Crowbar Craft",version="0.0.0.2")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)

public class CBCMod
{
	Weapons cbcWeapons;
	public static CreativeTabs cct = new CCT("CBCMod");
	Config config;
	@Instance("cbc")
	public static CBCMod CBCMod;
	@SidedProxy(clientSide="cbproject.proxy.ClientProxy",serverSide="chproject.proxy.Proxy")
	public static cbproject.proxy.Proxy Proxy;
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		config=new Config(Init.getSuggestedConfigurationFile());
		config.SaveConfig();
	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		Proxy.init();
		
		//以下是物品的注册（武器统一封装到cbcWeapons)
		cbcWeapons=new Weapons();
		cbcWeapons.registerItems(config);
		
		Block test_block = new Test_Block(531,Material.anvil);
		Item test_item = new Test_Item(10000);
		
        
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




