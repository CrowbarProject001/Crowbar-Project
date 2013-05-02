package cbproject.core.misc;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cbproject.core.lang.CBCLanguages;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCPacketHandler;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.gui.GuiWeaponCrafter;
import cbproject.crafting.network.NetWeaponCrafter;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.deathmatch.lang.DMLanguages;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.register.DMBlocks;
import cbproject.deathmatch.register.DMItems;
import cbproject.crafting.recipes.*;

public class CBCGeneralRegistry {
	
	public static void register(){
		CBCPacketHandler.addChannel("CBCCrafter", new NetWeaponCrafter());
		CBCLanguages.register();
	}
	
}
