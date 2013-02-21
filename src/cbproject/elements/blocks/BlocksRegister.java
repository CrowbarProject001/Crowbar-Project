package cbproject.elements.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.configure.Config;
import cbproject.elements.blocks.ammos.Ammo_uranium;
import cbproject.elements.items.weapons.Weapon_crowbar;
import cbproject.elements.items.weapons.Weapon_hgrenade;

public class BlocksRegister {
	public static Ammo_uranium ammo_uranium;
	public BlocksRegister(Config conf) {
		registerItems(conf);
		// TODO Auto-generated constructor stub
	}

	public void registerItems(Config conf){
		//TODO:添加调用config的部分
        ammo_uranium = new Ammo_uranium(532,1,Material.iron);
        
        LanguageRegistry.addName(ammo_uranium, "Uranium Ammo");
        
        ModLoader.registerBlock(ammo_uranium);
		return;
	}
}
