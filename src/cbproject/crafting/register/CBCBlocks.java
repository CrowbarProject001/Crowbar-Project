package cbproject.crafting.register;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.misc.Config;
import cbproject.core.register.GeneralRegistry;
import cbproject.crafting.blocks.BlockAdvWeaponCrafter;
import cbproject.crafting.blocks.BlockBatBox;
import cbproject.crafting.blocks.BlockCBCOres;
import cbproject.crafting.blocks.BlockElectricCrafter;
import cbproject.crafting.blocks.BlockElectricalBase;
import cbproject.crafting.blocks.BlockGeneratorFire;
import cbproject.crafting.blocks.BlockGeneratorLava;
import cbproject.crafting.blocks.BlockGeneratorSolar;
import cbproject.crafting.blocks.BlockRefined;
import cbproject.crafting.blocks.BlockWeaponCrafter;
import cbproject.crafting.blocks.BlockWire;
import cbproject.crafting.blocks.TileBatBox;
import cbproject.crafting.blocks.TileElCrafter;
import cbproject.crafting.blocks.TileGeneratorFire;
import cbproject.crafting.blocks.TileGeneratorLava;
import cbproject.crafting.blocks.TileGeneratorMugen;
import cbproject.crafting.blocks.TileGeneratorSolar;
import cbproject.crafting.blocks.TileWeaponCrafter;
import cbproject.crafting.blocks.TileWire;

/**
 * 方块注册类，包括了所有crafting包中的方块。
 * 
 * @author WeAthFolD
 */
public class CBCBlocks {

	public static Block weaponCrafter, blockRefined, uraniumOre, advCrafter,
			genFire, genLava, genSolar, genMugen, wire, storageS, storageL;
	public static Block oreTin, oreCopper, elCrafter;

	/**
	 * 在这里进行实际的方块加载。请在Init中调用它。
	 * 
	 * @param conf
	 *            Mod内部通用Config
	 */
	public static void init(Config conf) {

		weaponCrafter = new BlockWeaponCrafter(GeneralRegistry.getBlockId(
				"crafter", 0));
		blockRefined = new BlockRefined(
				GeneralRegistry.getBlockId("refined", 0));
		uraniumOre = new BlockCBCOres(GeneralRegistry.getBlockId("oreUranium",
				0), 0);
		advCrafter = new BlockAdvWeaponCrafter(GeneralRegistry.getBlockId(
				"advCrafter", 0));
		genMugen = new BlockElectricalBase(GeneralRegistry.getBlockId("mugen",
				0), Material.rock).setTileType(TileGeneratorMugen.class)
				.setIconName("genfire_side").setUnlocalizedName("mugen");
		wire = new BlockWire(GeneralRegistry.getBlockId("wire", 0));
		storageS = new BlockBatBox(
				GeneralRegistry.getBlockId("storagesmall", 0), 0);
		storageL = new BlockBatBox(
				GeneralRegistry.getBlockId("storagelarge", 0), 1);
		genSolar = new BlockGeneratorSolar(GeneralRegistry.getBlockId(
				"genSolar", 0));
		genLava = new BlockGeneratorLava(GeneralRegistry.getBlockId("genLava",
				0));
		genFire = new BlockGeneratorFire(GeneralRegistry.getBlockId("genFire",
				0));
		oreTin = new BlockCBCOres(GeneralRegistry.getBlockId("tinOre", 0), 1);
		oreCopper = new BlockCBCOres(
				GeneralRegistry.getBlockId("cooperOre", 0), 2);
		elCrafter = new BlockElectricCrafter(GeneralRegistry.getBlockId(
				"elCrafter", 0));

		ModLoader.registerBlock(weaponCrafter);
		ModLoader.registerBlock(blockRefined);
		ModLoader.registerBlock(uraniumOre);
		ModLoader.registerBlock(advCrafter);
		ModLoader.registerBlock(genMugen);
		ModLoader.registerBlock(wire);
		ModLoader.registerBlock(storageS);
		ModLoader.registerBlock(storageL);
		ModLoader.registerBlock(genLava);
		ModLoader.registerBlock(genSolar);
		ModLoader.registerBlock(genFire);
		ModLoader.registerBlock(oreTin);
		ModLoader.registerBlock(oreCopper);
		ModLoader.registerBlock(elCrafter);

		// TODO:添加其他房客的Harvest Level
		MinecraftForge.setBlockHarvestLevel(uraniumOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockRefined, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(weaponCrafter, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(advCrafter, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(oreTin, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreCopper, "pickaxe", 1);
		ModLoader.registerTileEntity(TileWeaponCrafter.class,
				"tile_entity_weapon_crafter");
		ModLoader.registerTileEntity(TileGeneratorMugen.class,
				"tile_entity_mugen");
		ModLoader.registerTileEntity(TileGeneratorSolar.class,
				"tile_entity_solar");
		ModLoader.registerTileEntity(TileGeneratorFire.class,
				"tile_entity_genfire");
		ModLoader.registerTileEntity(TileGeneratorLava.class,
				"tile_entity_genlava");
		ModLoader.registerTileEntity(TileBatBox.TileBoxSmall.class,
				"tile_entity_batbox1");
		ModLoader.registerTileEntity(TileBatBox.TileBoxLarge.class,
				"tile_entity_batbox2");
		ModLoader.registerTileEntity(TileWire.class, "tile_entity_wire");
		ModLoader.registerTileEntity(TileElCrafter.class,
				"tile_entity_elcrafter");
		return;

	}

}
