package cbproject.elements.items;

import cbproject.configure.Config;
import cbproject.elements.items.ammos.ItemUranium;
import cbproject.elements.items.weapons.Weapon_crowbar;
import cbproject.elements.items.weapons.Weapon_hgrenade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemsRegister {
	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;
	
	public static ItemUranium itemUranium;
	
	public ItemsRegister(Config conf){
		registerItems(conf);
		return;
	}
	
	public void registerItems(Config conf){
		//TODO:添加调用config的部分
		weapon_crowbar = new Weapon_crowbar(10001);
		weapon_hgrenade = new Weapon_hgrenade(10002);
		
		itemUranium = new ItemUranium(10003);
		
        LanguageRegistry.addName(weapon_crowbar, "Crowbar");
        LanguageRegistry.addName(weapon_hgrenade, "Hand Grenade");
        //合成表：撬棍
        ItemStack rosereddyeStack = new ItemStack(351,1,0);
        ItemStack ingotIronStack = new ItemStack(Item.ingotIron);
        ItemStack crowbarStack = new ItemStack(weapon_crowbar);
        ModLoader.addShapelessRecipe(crowbarStack, rosereddyeStack);
        GameRegistry.addShapelessRecipe(crowbarStack, rosereddyeStack,ingotIronStack);
        //End Crowbar
        
        
		return;
	}
}
