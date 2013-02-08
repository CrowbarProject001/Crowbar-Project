/* Custom CreativeTab */

package cbproject;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CCT extends CreativeTabs {

	public CCT(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack() {
	    return new ItemStack(Item.snowball);
	    //return new ItemStack(Crowbar);
	}
	//public static CreativeTabs cct = new CCT("CBCMod");
	
}
