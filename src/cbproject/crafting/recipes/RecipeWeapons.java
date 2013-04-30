package cbproject.crafting.recipes;

import java.util.ArrayList;

import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;

public class RecipeWeapons {
	
	public static ArrayList<RecipeWeaponEntry>recipes[]; 
	public static String pageDescriptions[];
	
	public static void InitializeRecipes(int pages, String[] ds){
		if(getRecipePages() > 0)
			return;
		if(pages != ds.length)
			throw new WrongUsageException("size does not match for adding recipe", pages);
		
		pageDescriptions = ds.clone();
		recipes = new ArrayList[pages];
		for(int i = 0; i < pages; i++){
			recipes[i] = new ArrayList<RecipeWeaponEntry>();
		}
	}
	
	public static void addWeaponRecipe(int page, RecipeWeaponEntry... entry){
		for(RecipeWeaponEntry e: entry){
			recipes[page].add(e);
		}
	}
	
	public static boolean doesNeedWeaponScrollBar(int page){
		return recipes[page].size() > 3;
	}
	
	public static RecipeWeaponEntry getRecipe(int page, ItemStack is){
		if(is == null)
			return null;
		
		for(RecipeWeaponEntry r : recipes[page]){
			if(is.itemID == r.output.itemID)
				return r;
		}
		return null;
	}
	
	public static int getRecipeLength(int page){
		return recipes[page].size();
	}
	
	public static int getRecipePages(){
		return recipes != null ? recipes.length: 0;
	}

	public static RecipeWeaponEntry getRecipe(int page, int i) {
		return recipes[page].get(i);
	}

}
