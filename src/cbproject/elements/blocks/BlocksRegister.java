package cbproject.elements.blocks;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.src.ModLoader;
import cbproject.configure.Config;
import cbproject.elements.blocks.weapons.BlockTripmine;

public class BlocksRegister {
	
	public static BlockTripmine blockTripmine;
	public static BlockTripmineRay blockTripmineRay;
	
	public BlocksRegister(Config conf) {
		registerItems(conf);
		// TODO Auto-generated constructor stub
	}

	public void registerItems(Config conf){
		//TODO:添加调用config的部分
		try {
			
			blockTripmine = new BlockTripmine(conf.GetBlockID("tripmine", 400));
			blockTripmineRay = new BlockTripmineRay(conf.GetBlockID("tripmine ray", 401));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockTripmine);
		LanguageRegistry.addName(blockTripmine, "Tripmine");
		return;
	}
}
