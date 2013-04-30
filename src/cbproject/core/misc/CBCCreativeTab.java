package cbproject.core.misc;

import cbproject.core.register.CBCItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * 
 * @author mkpoli
 * Custom CreativeTab.
 */
public class CBCCreativeTab extends CreativeTabs {

	public CBCCreativeTab(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
	    return new ItemStack(CBCItems.weapon_crowbar);
	}
	
}
