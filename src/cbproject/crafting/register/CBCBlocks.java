package cbproject.crafting.register;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.misc.Config;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.GeneralRegistry;
import cbproject.crafting.blocks.BlockAdvWeaponCrafter;
import cbproject.crafting.blocks.BlockBatBox;
import cbproject.crafting.blocks.BlockCopperOre;
import cbproject.crafting.blocks.BlockElectricalBase;
import cbproject.crafting.blocks.BlockRefined;
import cbproject.crafting.blocks.BlockTinOre;
import cbproject.crafting.blocks.BlockUraniumOre;
import cbproject.crafting.blocks.BlockWeaponCrafter;
import cbproject.crafting.blocks.BlockWire;
import cbproject.crafting.blocks.TileBatBox;
import cbproject.crafting.blocks.TileGeneratorFire;
import cbproject.crafting.blocks.TileGeneratorLava;
import cbproject.crafting.blocks.TileGeneratorMugen;
import cbproject.crafting.blocks.TileGeneratorSolar;
import cbproject.crafting.blocks.TileWeaponCrafter;
import cbproject.crafting.blocks.TileWire;
import cpw.mods.fml.common.registry.LanguageRegistry;
/**
 * 方块注册类，包括了所有crafting包中的方块。
 * @author WeAthFolD
 */
public class CBCBlocks {
	
	public static Block weaponCrafter, blockRefined, uraniumOre, advCrafter,genFire, genLava, genSolar, genMugen, wire, storageS, storageL;
	public static Block oreTin, oreCooper;
	
	/**
	 * 在这里进行实际的方块加载。请在Init中调用它。
	 * @param conf Mod内部通用Config
	 */
	public static void init(Config conf){

		weaponCrafter = new BlockWeaponCrafter(GeneralRegistry.getBlockId("crafter", 0));
		blockRefined = new BlockRefined(GeneralRegistry.getBlockId("refined", 0));
		uraniumOre = new BlockUraniumOre(GeneralRegistry.getBlockId("oreUranium", 0));
		advCrafter = new BlockAdvWeaponCrafter(GeneralRegistry.getBlockId("advCrafter", 0));
		genMugen = new BlockElectricalBase(GeneralRegistry.getBlockId("mugen", 0), Material.rock).setGeneratorType(TileGeneratorMugen.class).setUnlocalizedName("mugen");
		wire = new BlockWire(GeneralRegistry.getBlockId("wire", 0));
		storageS = new BlockBatBox(GeneralRegistry.getBlockId("storagesmall", 0), 0);
		storageL = new BlockBatBox(GeneralRegistry.getBlockId("storagelarge", 0), 1);
		genSolar = new BlockElectricalBase(GeneralRegistry.getBlockId("genSolar", 0), Material.rock).setGeneratorType(TileGeneratorSolar.class).setGuiId(GeneralProps.GUI_ID_GENSOLAR).setUnlocalizedName("genSolar");
		genLava = new BlockElectricalBase(GeneralRegistry.getBlockId("genLava", 0), Material.rock).setGeneratorType(TileGeneratorLava.class).setGuiId(GeneralProps.GUI_ID_GENLAVA).setUnlocalizedName("genLava");
		genFire = new BlockElectricalBase(GeneralRegistry.getBlockId("genFire", 0), Material.rock).setGeneratorType(TileGeneratorFire.class).setGuiId(GeneralProps.GUI_ID_GENFIRE).setUnlocalizedName("genFire");
		oreTin = new BlockTinOre(GeneralRegistry.getBlockId("tinOre", 0));
		oreCooper = new BlockCopperOre(GeneralRegistry.getBlockId("cooperOre", 0));
		
		LanguageRegistry.addName(genMugen, "Mugen Generator");
		LanguageRegistry.addName(wire, "Conductive wire");
		LanguageRegistry.addName(storageS, "Small Battery Box");
		LanguageRegistry.addName(storageL, "Large Battery Box");
		LanguageRegistry.addName(genLava, "Lava Generator");
		LanguageRegistry.addName(genSolar, "Solar Generator");
		LanguageRegistry.addName(genFire, "Fire Generator");
		
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
		ModLoader.registerBlock(oreCooper);
		
		MinecraftForge.setBlockHarvestLevel(uraniumOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockRefined, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreTin, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(oreCooper, "pickaxe", 1);
		ModLoader.registerTileEntity(TileWeaponCrafter.class, "tile_entity_weapon_crafter");
		ModLoader.registerTileEntity(TileGeneratorMugen.class, "tile_entity_mugen");
		ModLoader.registerTileEntity(TileGeneratorSolar.class, "tile_entity_solar");
		ModLoader.registerTileEntity(TileGeneratorFire.class, "tile_entity_genfire");
		ModLoader.registerTileEntity(TileGeneratorLava.class, "tile_entity_genlava");
		ModLoader.registerTileEntity(TileBatBox.class, "tile_entity_batbox");
		ModLoader.registerTileEntity(TileWire.class, "tile_entity_wire");
		
		return;
		
	}
	
}
