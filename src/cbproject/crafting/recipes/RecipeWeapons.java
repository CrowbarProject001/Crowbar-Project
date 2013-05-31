package cbproject.crafting.recipes;

import java.util.ArrayList;

import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;

public class RecipeWeapons {
	
	public static ArrayList<RecipeCrafter>recipes[]; 
	public static ArrayList<RecipeCrafter> advancedRecipes[];
	public static String pageDescriptions[];
	public static String advPageDescriptions[];
	private static boolean finished = true;
	
	public static void InitializeRecipes(int pages, String[] ds){
		if(getRecipePages() > 0)
			return;
		if(pages != ds.length)
			throw new WrongUsageException("size does not match for adding recipe", pages);
		
		pageDescriptions = ds.clone();
		recipes = new ArrayList[pages];
		for(int i = 0; i < pages; i++){
			recipes[i] = new ArrayList<RecipeCrafter>();
		}
		open();
	}
	
	public static void InitializeAdvRecipes(int pages, String[] ds){
		if(getAdvRecipePages() > 0)
			return;
		if(pages != ds.length)
			throw new WrongUsageException("size does not match for adding recipe", pages);
		
		advPageDescriptions = ds.clone();
		advancedRecipes = new ArrayList[pages];
		for(int i = 0; i < pages; i++){
			advancedRecipes[i] = new ArrayList<RecipeCrafter>();
		}
		open();
	}
	
	private static void open(){
		finished = false;
	}
	
	public static void close(){
		finished = true;
	}
	
	private static int getAdvRecipePages() {
		return advancedRecipes != null ? advancedRecipes.length: 0;
	}

	public static void addWeaponRecipe(int page, RecipeCrafter... entry){
		if(finished)
			throw new RuntimeException("Trying to add a weapon recipe while finished initializing");
		for(RecipeCrafter e: entry){
			recipes[page].add(e);
		}
	}
	
	public static void addAdvWeaponRecipe(int page, RecipeCrafter... entry){
		if(finished)
			throw new RuntimeException("Trying to add a weapon recipe while finished initializing");
		for(RecipeCrafter e: entry){
			advancedRecipes[page].add(e);
		}
	}
	
	public static boolean doesNeedScrollBar(int page){
		return recipes[page].size() > 3;
	}
	
	public static boolean doesAdvNeedScrollBar(int page){
		return advancedRecipes[page].size() > 3;
	}
	
	public static RecipeCrafter getRecipe(int page, ItemStack is){
		if(is == null)
			return null;
		
		for(RecipeCrafter r : recipes[page]){
			if(is.itemID == r.output.itemID)
				return r;
		}
		return null;
	}
	
	public static RecipeCrafter getAdvancedRecipe(int page, ItemStack is){
		if(is == null)
			return null;
		
		for(RecipeCrafter r : advancedRecipes[page]){
			if(is.itemID == r.output.itemID)
				return r;
		}
		return null;
	}
	
	public static int getRecipeLength(int page){
		return recipes[page].size();
	}
	
	public static int getAdvRecipeLength(int page){
		return advancedRecipes[page].size();
	}
	
	public static int getRecipePages(){
		return recipes != null ? recipes.length: 0;
	}

	public static RecipeCrafter getRecipe(int page, int i) {
		if(recipes[page].size() < i + 1)
			return null;
		return recipes[page].get(i);
	}
	
	public static RecipeCrafter getAdvRecipe(int page, int i) {
		if(advancedRecipes[page].size() < i + 1)
			return null;
		return advancedRecipes[page].get(i);
	}

}
