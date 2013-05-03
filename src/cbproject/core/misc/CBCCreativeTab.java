package cbproject.core.misc;

import cbproject.deathmatch.register.DMItems;
import net.minecraft.creativetab.CreativeTabs;
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
	    return new ItemStack(DMItems.weapon_crowbar);
	}
	
}
