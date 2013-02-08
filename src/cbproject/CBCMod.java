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

import cbproject.Blocks.Test_Block;
import cbproject.Items.Test_Item;
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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.BaseMod;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

@Mod(modid="cbc",name="CrowbarCraft",version="0.0.0.2")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)

public class CBCMod
{
	@Instance("cbc")
	public static CBCMod CBCMod;
	@SidedProxy(clientSide="cbproject.Proxy.ClientProxy",serverSide="chproject.Proxy.Proxy")
	public static cbproject.Proxy.Proxy Proxy;
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		
	}
	
	@Init
	public void init(FMLInitializationEvent Init){
		Proxy.init();
		Block test_block = new Test_Block(531,Material.anvil);
		Item test_item = new Test_Item(10000);
		//·½¿éµÄ×¢²á
		ModLoader.addName(test_block , "Test_Block");
		LanguageRegistry.addName(test_block,"Test Block");
		ModLoader.registerBlock(test_block);

	}
	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
		
	}
}




