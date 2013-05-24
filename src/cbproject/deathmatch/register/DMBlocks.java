package cbproject.deathmatch.register;

import net.minecraft.src.ModLoader;
import cbproject.core.misc.Config;
import cbproject.deathmatch.blocks.BlockArmorCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityArmorCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;

public class DMBlocks {
	
	public static BlockTripmine blockTripmine;
	public static BlockArmorCharger armorCharger;
	
	public static void init(Config conf){
		
		try {
			
			blockTripmine = new BlockTripmine(conf.GetBlockID("tripmine", 350));
			armorCharger = new BlockArmorCharger(conf.GetBlockID("armorcharger", 451));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockTripmine);
		ModLoader.registerBlock(armorCharger);
		ModLoader.registerTileEntity(TileEntityTripmine.class, "tile_entity_tripmine");
		ModLoader.registerTileEntity(TileEntityArmorCharger.class, "tile_entity_charger");
	}
}