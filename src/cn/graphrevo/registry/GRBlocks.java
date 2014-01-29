/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.registry;

import cn.graphrevo.block.BlockTripmine;
import cn.graphrevo.tileentity.TileEntityTripmine;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;

/**
 * @author WeAthFolD
 *
 */
public class GRBlocks {

	public static Block blockTripmine;
	
	public static void init() {
		blockTripmine = new BlockTripmine(2300);
		
		GameRegistry.registerBlock(blockTripmine, "tripmine");
		
		GameRegistry.registerTileEntity(TileEntityTripmine.class, "tile_tripmine");
		
		LanguageRegistry.addName(blockTripmine, "M18A1阔剑地雷");
	}

}
