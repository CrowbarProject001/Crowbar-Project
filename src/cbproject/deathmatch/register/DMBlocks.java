package cbproject.deathmatch.register;

import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;
import cbproject.core.misc.Config;
import cbproject.core.register.GeneralRegistry;
import cbproject.deathmatch.blocks.BlockArmorCharger;
import cbproject.deathmatch.blocks.BlockHealthCharger;
import cbproject.deathmatch.blocks.BlockMedkitFiller;
import cbproject.deathmatch.blocks.BlockTripmine;
import cbproject.deathmatch.blocks.TileArmorCharger;
import cbproject.deathmatch.blocks.TileHealthCharger;
import cbproject.deathmatch.blocks.TileTripmine;
import cbproject.deathmatch.blocks.TileMedkitFiller;

public class DMBlocks {

	public static Block blockTripmine, armorCharger, healthCharger,
			medkitFiller;

	public static void init(Config conf) {

		try {

			blockTripmine = new BlockTripmine(GeneralRegistry.getBlockId(
					"tripmine", 0));
			armorCharger = new BlockArmorCharger(GeneralRegistry.getBlockId(
					"armorCharger", 0));
			healthCharger = new BlockHealthCharger(GeneralRegistry.getBlockId(
					"healthCharger", 0));
			medkitFiller = new BlockMedkitFiller(GeneralRegistry.getBlockId(
					"medkitFiller", 0));

		} catch (Exception e) {
			e.printStackTrace();
		}

		ModLoader.registerBlock(blockTripmine);
		ModLoader.registerBlock(armorCharger);
		ModLoader.registerBlock(healthCharger);
		ModLoader.registerBlock(medkitFiller);

		ModLoader
				.registerTileEntity(TileTripmine.class, "tile_entity_tripmine");
		ModLoader.registerTileEntity(TileArmorCharger.class,
				"tile_entity_charger");
		ModLoader.registerTileEntity(TileHealthCharger.class,
				"tile_entity_hcharger");
		ModLoader.registerTileEntity(TileMedkitFiller.class,
				"tile_entity_medfiller");
	}
}
