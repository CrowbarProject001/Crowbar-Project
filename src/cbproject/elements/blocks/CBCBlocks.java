package cbproject.elements.blocks;

import cbproject.configure.Config;
import cbproject.elements.blocks.weapons.BlockTripmine;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.src.ModLoader;

/**
 * 
 * @author WeAthFolD
 * @description Block register,which handles all instance of blocks in mod.
 */
public class CBCBlocks {
	
	public static BlockTripmine blockTripmine;
	public static BlockTripmineRay blockTripmineRay;
	public static BlockWeaponCrafter blockWeaponCrafter;
	
	public CBCBlocks(Config conf) {
		registerItems(conf);
	}

	public void registerItems(Config conf){
		
		try {
			
			blockTripmine = new BlockTripmine(conf.GetBlockID("tripmine", 400));
			blockTripmineRay = new BlockTripmineRay(conf.GetBlockID("tripmine ray", 401));
			blockWeaponCrafter = new BlockWeaponCrafter(conf.GetBlockID("weaponcrafter", 4077));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(blockTripmine);
		ModLoader.registerBlock(blockWeaponCrafter);
		LanguageRegistry.addName(blockTripmine, "Tripmine");
		LanguageRegistry.addName(blockWeaponCrafter, "Weapon Crafter");
		
		ModLoader.registerTileEntity(BlockTripmine.TileEntityTripmine.class, "tile_entity_tripmine");
		ModLoader.registerTileEntity(BlockTripmineRay.TileEntityTripmineRay.class, "tile_entity_tripmine_ray");
		ModLoader.registerTileEntity(BlockWeaponCrafter.TileEntityWeaponCrafter.class, "tile_entity_weapon_crafter");
		return;
		
	}
}
