package cn.lambdacraft.intergration.ic2;

import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.crafting.register.CBCBlocks;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.items.ArmorHEV.EnumAttachment;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.lambdacraft.intergration.ic2.item.ArmorHEVIC2;
import cn.lambdacraft.intergration.ic2.item.ItemBatteryIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockArmorChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockElCrafterIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockHealthChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockMedkitFillerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileArmorChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileElCrafterIC2;
import cn.lambdacraft.intergration.ic2.tile.TileHealthChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileMedkitFillerIC2;
import net.minecraft.src.ModLoader;

public class IC2Registration {

	public static void registerBlocks() {
		CBCBlocks.elCrafter = new BlockElCrafterIC2(GeneralRegistry.getBlockId("elCrafter", 0));
		DMBlocks.armorCharger = new BlockArmorChargerIC2(GeneralRegistry.getBlockId("armorCharger", 0));
		DMBlocks.healthCharger = new BlockHealthChargerIC2(GeneralRegistry.getBlockId("healthCharger", 0));
		DMBlocks.medkitFiller = new BlockMedkitFillerIC2(GeneralRegistry.getBlockId("medkitFiller", 0));
		
		ModLoader.registerBlock(CBCBlocks.elCrafter);
		ModLoader.registerBlock(DMBlocks.armorCharger);
		ModLoader.registerBlock(DMBlocks.healthCharger);
		ModLoader.registerBlock(DMBlocks.medkitFiller);
		ModLoader.registerTileEntity(TileArmorChargerIC2.class, "tile_entity_charger");
		ModLoader.registerTileEntity(TileHealthChargerIC2.class, "tile_entity_hcharger");
		ModLoader.registerTileEntity(TileMedkitFillerIC2.class, "tile_entity_medfiller");
		ModLoader.registerTileEntity(TileElCrafterIC2.class, "tile_entity_elcrafter");
	}
	
	public static void registerItems() {
		DMItems.armorHEVHelmet = new ArmorHEVIC2(GeneralRegistry.getItemId("hevHelmet",
				GeneralProps.CAT_EQUIPMENT), 0);
		DMItems.armorHEVChestplate = new ArmorHEVIC2(GeneralRegistry.getItemId(
				"hevChestplate", 3), 1);
		DMItems.armorHEVLeggings = new ArmorHEVIC2(GeneralRegistry.getItemId(
				"hevLeggings", 3), 2);
		DMItems.armorHEVBoot = new ArmorHEVIC2(GeneralRegistry.getItemId("hevBoot", 3), 3);
		DMItems.hevLongjump = new ArmorHEVIC2(GeneralRegistry.getItemId("hevLongjump", 3),
					EnumAttachment.LONGJUMP);
		CBCItems.battery = new ItemBatteryIC2(GeneralRegistry.getItemId("battery", 3));
	}
}
