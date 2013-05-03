package cbproject.core.misc;

import cbproject.core.configure.Config;
import cbproject.core.register.CBCLanguage;
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
/**
 * 
 * @author WeAthFolD
 * @description Block register,which handles all instance of blocks in mod.
 */
public class CBCBlocks {
	
	public static Block blockWeaponCrafter, blockRefined, blockUraniumOre;
	
	public static void init(Config conf){
		
		try {
			
			blockWeaponCrafter = new BlockWeaponCrafter(conf.GetBlockID("weaponcrafter", 402));
			blockRefined = new BlockRefined(conf.GetBlockID("blockRefined", 4003));
			blockUraniumOre = new BlockUraniumOre(conf.GetBlockID("oreuranium", 4004));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockWeaponCrafter);
		ModLoader.registerBlock(blockRefined);
		ModLoader.registerBlock(blockUraniumOre);
		MinecraftForge.setBlockHarvestLevel(blockUraniumOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockRefined, "pickaxe", 1);
		ModLoader.registerTileEntity(TileEntityWeaponCrafter.class, "tile_entity_weapon_crafter");
		
		return;
		
	}
	
}
