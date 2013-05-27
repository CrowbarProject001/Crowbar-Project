package cbproject.deathmatch.register;

import net.minecraft.src.ModLoader;
import cbproject.core.misc.Config;
import cbproject.deathmatch.blocks.BlockArmorCharger;
import cbproject.deathmatch.blocks.BlockHealthCharger;
import cbproject.deathmatch.blocks.BlockTripmine;
import cbproject.deathmatch.blocks.tileentities.TileEntityArmorCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityHealthCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;

public class DMBlocks {
	
	public static BlockTripmine blockTripmine;
	public static BlockArmorCharger armorCharger;
	public static BlockHealthCharger healthCharger;
	
	public static void init(Config conf){
		
		try {
			
			blockTripmine = new BlockTripmine(conf.GetBlockID("tripmine", 350));
			armorCharger = new BlockArmorCharger(conf.GetBlockID("armorcharger", 451));
			healthCharger = new BlockHealthCharger(conf.GetBlockID("healthcharger", 452));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockTripmine);
		ModLoader.registerBlock(armorCharger);
		ModLoader.registerBlock(healthCharger);
		ModLoader.registerTileEntity(TileEntityTripmine.class, "tile_entity_tripmine");
		ModLoader.registerTileEntity(TileEntityArmorCharger.class, "tile_entity_charger");
		ModLoader.registerTileEntity(TileEntityHealthCharger.class, "tile_entity_hcharger");
	}
}
