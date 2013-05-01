package cbproject.core.register;

import cbproject.core.configure.Config;
import cbproject.crafting.blocks.BlockUraniumOre;
import cbproject.crafting.blocks.BlockWeaponCrafter;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.deathmatch.blocks.BlockRefined;
import cbproject.deathmatch.blocks.BlockTripmineRay;
import cbproject.deathmatch.blocks.tileentities.*;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;
import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.misc.CBCLanguage;
/**
 * 
 * @author WeAthFolD
 * @description Block register,which handles all instance of blocks in mod.
 */
public class CBCBlocks {
	
	public static BlockTripmine blockTripmine;
	public static BlockTripmineRay blockTripmineRay;
	public static BlockWeaponCrafter blockWeaponCrafter;
	public static Block blockRefined;
	public static Block blockUraniumOre;
	
	
	public CBCBlocks(Config conf) {
		registerItems(conf);
	}

	public void registerItems(Config conf){
		
		try {
			
			blockTripmine = new BlockTripmine(conf.GetBlockID("tripmine", 400));
			blockTripmineRay = new BlockTripmineRay(conf.GetBlockID("tripmine ray", 401));
			blockWeaponCrafter = new BlockWeaponCrafter(conf.GetBlockID("weaponcrafter", 402));
			blockRefined = new BlockRefined(conf.GetBlockID("blockRefined", 4003));
			blockUraniumOre = new BlockUraniumOre(conf.GetBlockID("oreuranium", 4004));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockTripmine);
		ModLoader.registerBlock(blockWeaponCrafter);
		ModLoader.registerBlock(blockRefined);
		ModLoader.registerBlock(blockUraniumOre);
		MinecraftForge.setBlockHarvestLevel(blockUraniumOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockRefined, "pickaxe", 1);
		ModLoader.registerTileEntity(TileEntityTripmine.class, "tile_entity_tripmine");
		ModLoader.registerTileEntity(TileEntityTripmineRay.class, "tile_entity_tripmine_ray");
		ModLoader.registerTileEntity(TileEntityWeaponCrafter.class, "tile_entity_weapon_crafter");
		return;
		
	}
	
	public static void addNameForBlocks(){
		
		CBCLanguage.addDefaultName(blockTripmine, "Tripmine");
		CBCLanguage.addDefaultName(blockWeaponCrafter, "Weapon Crafter");
		CBCLanguage.addDefaultName(blockRefined, "Refined Iron Block");
		CBCLanguage.addDefaultName(blockUraniumOre, "Uranium Ore");
		
		CBCLanguage.addLocalName(blockTripmine, "激光绊雷");
		CBCLanguage.addLocalName(blockWeaponCrafter, "武器制作台");
		CBCLanguage.addLocalName(blockRefined, "精铁块");
		CBCLanguage.addDefaultName(blockUraniumOre, "铀矿");
		
	}
	
}
