package cbproject.crafting.items;

import cbproject.core.CBCMod;
import net.minecraft.item.Item;

public abstract class ItemMaterial extends Item{

	public ItemMaterial(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
	}

}
