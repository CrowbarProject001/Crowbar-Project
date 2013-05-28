package cbproject.crafting.register;

import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.misc.Config;
import cbproject.core.register.GeneralRegistry;
import cbproject.crafting.blocks.BlockAdvWeaponCrafter;
import cbproject.crafting.blocks.BlockRefined;
import cbproject.crafting.blocks.BlockUraniumOre;
import cbproject.crafting.blocks.BlockWeaponCrafter;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
/**
 * 方块注册类，包括了所有crafting包中的方块。
 * @author WeAthFolD
 */
public class CBCBlocks {
	
	public static Block weaponCrafter, blockRefined, uraniumOre, advCrafter;
	
	/**
	 * 在这里进行实际的方块加载。请在Init中调用它。
	 * @param conf Mod内部通用Config
	 */
	public static void init(Config conf){
		
		try {
			
			weaponCrafter = new BlockWeaponCrafter(GeneralRegistry.getBlockId("crafter", 0));
			blockRefined = new BlockRefined(GeneralRegistry.getBlockId("refined", 0));
			uraniumOre = new BlockUraniumOre(GeneralRegistry.getBlockId("oreUranium", 0));
			advCrafter = new BlockAdvWeaponCrafter(GeneralRegistry.getBlockId("advCrafter", 0));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ModLoader.registerBlock(weaponCrafter);
		ModLoader.registerBlock(blockRefined);
		ModLoader.registerBlock(uraniumOre);
		ModLoader.registerBlock(advCrafter);
		MinecraftForge.setBlockHarvestLevel(uraniumOre, "pickaxe", 2);
		MinecraftForge.setBlockHarvestLevel(blockRefined, "pickaxe", 1);
		ModLoader.registerTileEntity(TileEntityWeaponCrafter.class, "tile_entity_weapon_crafter");
		
		return;
		
	}
	
}
