package cbproject.deathmatch.register;

import net.minecraft.src.ModLoader;
import cbproject.core.misc.Config;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;

public class DMBlocks {
	
	public static BlockTripmine blockTripmine;
	
	public static void init(Config conf){
		
		try {
			
			blockTripmine = new BlockTripmine(conf.GetBlockID("tripmine", 400));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockTripmine);

		ModLoader.registerTileEntity(TileEntityTripmine.class, "tile_entity_tripmine");
	}
}
