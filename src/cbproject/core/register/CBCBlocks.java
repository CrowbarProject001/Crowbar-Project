package cbproject.core.register;

import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.misc.Config;
import cbproject.crafting.blocks.BlockAdvWeaponCrafter;
import cbproject.crafting.blocks.BlockRefined;
import cbproject.crafting.blocks.BlockUraniumOre;
import cbproject.crafting.blocks.BlockWeaponCrafter;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
/**
 * 
 * @author WeAthFolD
 * @description Block register,which handles all instance of blocks in mod.
 */
public class CBCBlocks {
	
	public static Block blockWeaponCrafter, blockRefined, blockUraniumOre, blockAdvCrafter;
	
	public static void init(Config conf){
		
		try {
			
			blockWeaponCrafter = new BlockWeaponCrafter(conf.GetBlockID("weaponcrafter", 402));
			blockRefined = new BlockRefined(conf.GetBlockID("blockRefined", 403));
			blockUraniumOre = new BlockUraniumOre(conf.GetBlockID("oreuranium", 404));
			blockAdvCrafter = new BlockAdvWeaponCrafter(conf.GetBlockID("advcrafter", 405));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockWeaponCrafter);
		ModLoader.registerBlock(blockRefined);
		ModLoader.registerBlock(blockUraniumOre);
		ModLoader.registerBlock(blockAdvCrafter);
		MinecraftForge.setBlockHarvestLevel(blockUraniumOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockRefined, "pickaxe", 1);
		ModLoader.registerTileEntity(TileEntityWeaponCrafter.class, "tile_entity_weapon_crafter");
		
		return;
		
	}
	
}
