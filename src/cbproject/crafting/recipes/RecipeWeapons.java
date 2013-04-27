package cbproject.crafting.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import cbproject.core.register.CBCItems;

public class RecipeWeapons {
	
	public static ArrayList<RecipeWeaponEntry> weaponRecipes = new ArrayList<RecipeWeaponEntry>();
	public static ArrayList<RecipeWeaponEntry> ammoRecipes = new ArrayList<RecipeWeaponEntry>();
	
	public static void addWeaponRecipe(RecipeWeaponEntry... entry){
		for(RecipeWeaponEntry e: entry)
			weaponRecipes.add(e);
	}
	
	public static boolean doesNeedWeaponScrollBar(){
		return weaponRecipes.size() > 3;
	}
	
	public static RecipeWeaponEntry getWeaponRecipe(int i){
		if(i > weaponRecipes.size())
			return null;
		return weaponRecipes.get(i);
	}
	
	public static RecipeWeaponEntry getWeaponRecipe(ItemStack is){
		if(is == null){
			System.out.println("WTF!");
		}
		for(RecipeWeaponEntry r : ammoRecipes){
			if(is != null && is.itemID == r.output.itemID)
				return r;
		}
		return null;
	}
	
	public static RecipeWeaponEntry getAmmoRecipe(ItemStack is){
		if(is == null){
			System.out.println("WTF!");
		}
		for(RecipeWeaponEntry r : ammoRecipes){
			if(is != null && is.itemID == r.output.itemID)
				return r;
		}
		return null;
	}
	
	public static void addAmmoRecipe(RecipeWeaponEntry... entry){
		for(RecipeWeaponEntry e: entry)
			ammoRecipes.add(e);
	}
	
	public static boolean doesNeedAmmoScrollBar(){
		return ammoRecipes.size() > 3;
	}
	
	public static RecipeWeaponEntry getAmmoRecipe(int i){
		if(i > ammoRecipes.size())
			return null;
		return ammoRecipes.get(i);
	}

}
