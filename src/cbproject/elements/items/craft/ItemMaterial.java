package cbproject.elements.items.craft;

import cbproject.CBCMod;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public abstract class ItemMaterial extends Item{

	public ItemMaterial(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
	}

}
