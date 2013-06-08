package cbproject.crafting.recipes;

import cbproject.crafting.items.ItemBullet;
import cbproject.deathmatch.items.ammos.ItemAmmo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class RecipeRepair extends RecipeCrafter{

	public ItemAmmo inputA;
	public ItemBullet inputB;
	
	public RecipeRepair(ItemAmmo ia, ItemBullet ib) {
		super(new ItemStack(ia, 1, 0), 0, new ItemStack(ia, 1, ia.getMaxDamage() - 1), new ItemStack(ib, ia.getMaxDamage() - 1));
		inputA = ia;
		inputB = ib;
	}
	
	@Override
	public String toString() {
		return StatCollector.translateToLocal("repair.name") + " : " + inputA.getLocalizedName(null);
	}

}
