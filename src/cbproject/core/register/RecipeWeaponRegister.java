package cbproject.core.register;

import net.minecraft.item.ItemStack;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeapons;

public class RecipeWeaponRegister {
	
	public static void addRecipes(){
		String description[] = {"Weapon Forging", "Ammo Crafting"};
		RecipeWeapons.InitializeRecipes(2, description);
		
		RecipeWeaponEntry wpnRecipes[] = {
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_9mmhandgun),500, new ItemStack(CBCItems.mat_pistol, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_357),500 ,new ItemStack(CBCItems.mat_pistol, 3), new ItemStack(CBCItems.mat_accessories, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_9mmAR) ,500, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_shotgun) ,500 , new ItemStack(CBCItems.mat_light, 5), new ItemStack(CBCItems.mat_accessories, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_crossbow), 500, new ItemStack(CBCItems.mat_light, 6) ,new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.ironBar, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_RPG), 500, new ItemStack(CBCItems.mat_heavy, 3), new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.mat_explosive, 2))
		};
		RecipeWeapons.addWeaponRecipe(0, wpnRecipes);
	}
	
}

