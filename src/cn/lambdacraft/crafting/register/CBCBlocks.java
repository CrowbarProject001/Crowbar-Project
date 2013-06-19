package cn.lambdacraft.crafting.register;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.crafting.blocks.BlockAdvWeaponCrafter;
import cn.lambdacraft.crafting.blocks.BlockBatBox;
import cn.lambdacraft.crafting.blocks.BlockCBCOres;
import cn.lambdacraft.crafting.blocks.BlockElectricCrafter;
import cn.lambdacraft.crafting.blocks.BlockElectricalBase;
import cn.lambdacraft.crafting.blocks.BlockGeneratorFire;
import cn.lambdacraft.crafting.blocks.BlockGeneratorLava;
import cn.lambdacraft.crafting.blocks.BlockGeneratorSolar;
import cn.lambdacraft.crafting.blocks.BlockRefined;
import cn.lambdacraft.crafting.blocks.BlockWeaponCrafter;
import cn.lambdacraft.crafting.blocks.BlockWire;
import cn.lambdacraft.crafting.blocks.TileBatBox;
import cn.lambdacraft.crafting.blocks.TileElCrafter;
import cn.lambdacraft.crafting.blocks.TileGeneratorFire;
import cn.lambdacraft.crafting.blocks.TileGeneratorLava;
import cn.lambdacraft.crafting.blocks.TileGeneratorMugen;
import cn.lambdacraft.crafting.blocks.TileGeneratorSolar;
import cn.lambdacraft.crafting.blocks.TileWeaponCrafter;
import cn.lambdacraft.crafting.blocks.TileWire;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;

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

		oreTin = new BlockCBCOres(GeneralRegistry.getBlockId("tinOre", 0), 1);
		oreCopper = new BlockCBCOres(
				GeneralRegistry.getBlockId("cooperOre", 0), 2);
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

		ModLoader.registerBlock(weaponCrafter);
		ModLoader.registerBlock(blockRefined);
		ModLoader.registerBlock(uraniumOre);
		ModLoader.registerBlock(advCrafter);
		ModLoader.registerBlock(oreTin);
		ModLoader.registerBlock(oreCopper);
		
		if(!CBCMod.ic2Installed) {
			elCrafter = new BlockElectricCrafter(GeneralRegistry.getBlockId(
					"elCrafter", 0));
			ModLoader.registerBlock(wire);
			ModLoader.registerBlock(storageS);
			ModLoader.registerBlock(storageL);
			ModLoader.registerBlock(genLava);
			ModLoader.registerBlock(genSolar);
			ModLoader.registerBlock(genFire);
			ModLoader.registerBlock(genMugen);
			ModLoader.registerBlock(elCrafter);
			ModLoader.registerTileEntity(TileElCrafter.class,
					"tile_entity_elcrafter");
		}
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
		
		return;

	}

}
