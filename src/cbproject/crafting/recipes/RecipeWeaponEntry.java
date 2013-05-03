package cbproject.crafting.recipes;

import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;

public class RecipeWeaponEntry {
	public ItemStack[] input;
	public ItemStack output;
	public int heatRequired;
	
	public RecipeWeaponEntry(ItemStack out, int heat, ItemStack...in) {
		if(in == null){
			System.err.println("dont register null!");
			return;
		}
		if(in.length > 3)
			throw new WrongUsageException("length must be within 3", input[0]);
		output = out;
		input = in;
		heatRequired = heat;
	}

	@Override
	public String toString(){
		String out = "[recipe:";
		for(ItemStack s : input)
			out.concat(" " + s.getItemName());
		out += " -> " + output.getDisplayName() + "]";
		return out;
	}
}
