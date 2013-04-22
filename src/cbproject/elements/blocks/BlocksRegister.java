package cbproject.elements.blocks;

import cbproject.configure.Config;
import cbproject.elements.blocks.weapons.BlockTripmine;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.src.ModLoader;

/**
 * 
 * @author WeAthFolD
 * @description Block register,which handles all instance of blocks in mod.
 */
public class BlocksRegister {
	
	public static BlockTripmine blockTripmine;
	public static BlockTripmineRay blockTripmineRay;
	
	public BlocksRegister(Config conf) {
		registerItems(conf);
	}

	public void registerItems(Config conf){
		
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
