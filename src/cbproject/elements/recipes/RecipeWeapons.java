package cbproject.elements.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import cbproject.elements.items.CBCItems;

public class RecipeWeapons {
	
	public static ArrayList<RecipeWeaponEntry> recipeList = new ArrayList<RecipeWeaponEntry>();
	
	public static void addWeaponRecipe(RecipeWeaponEntry... entry){
		for(RecipeWeaponEntry e: entry)
			recipeList.add(e);
	}
	
	public static  boolean doesNeedScrollBar(){
		return recipeList.size() > 3;
	}
	
	public static RecipeWeaponEntry getRecipe(int i){
		if(i > recipeList.size())
			return null;
		return recipeList.get(i);
	}
	
	public static RecipeWeaponEntry getRecipeByItemStack(ItemStack is){
		if(is == null){
			System.out.println("WTF!");
		}
		for(RecipeWeaponEntry r : recipeList){
			if(is != null && is.itemID == r.output.itemID)
				return r;
		}
		return null;
	}

}
