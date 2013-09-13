package cn.lambdacraft.deathmatch.register;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.deathmatch.block.BlockArmorCharger;
import cn.lambdacraft.deathmatch.block.BlockHealthCharger;
import cn.lambdacraft.deathmatch.block.BlockMedkitFiller;
import cn.lambdacraft.deathmatch.block.BlockTripmine;
import cn.lambdacraft.deathmatch.block.TileArmorCharger;
import cn.lambdacraft.deathmatch.block.TileHealthCharger;
import cn.lambdacraft.deathmatch.block.TileMedkitFiller;
import cn.lambdacraft.deathmatch.block.TileTripmine;
import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;

public class DMBlocks {

	public static Block blockTripmine, armorCharger, healthCharger,
			medkitFiller;

	public static void init(Config conf) {

		blockTripmine = new BlockTripmine(GeneralRegistry.getBlockId(
				"tripmine", 0));
		ModLoader.registerBlock(blockTripmine);
		ModLoader.registerTileEntity(TileTripmine.class, "tile_entity_tripmine");

		if (!CBCMod.ic2Installed) {
			armorCharger = new BlockArmorCharger(GeneralRegistry.getBlockId(
					"armorCharger", 0));
			healthCharger = new BlockHealthCharger(GeneralRegistry.getBlockId(
					"healthCharger", 0));
			medkitFiller = new BlockMedkitFiller(GeneralRegistry.getBlockId(
					"medkitFiller", 0));
			ModLoader.registerBlock(armorCharger);
			ModLoader.registerBlock(healthCharger);
			ModLoader.registerBlock(medkitFiller);

			ModLoader.registerTileEntity(TileArmorCharger.class,
					"tile_entity_charger");
			ModLoader.registerTileEntity(TileHealthCharger.class,
					"tile_entity_hcharger");
			ModLoader.registerTileEntity(TileMedkitFiller.class,
					"tile_entity_medfiller");
		}

	}
}
