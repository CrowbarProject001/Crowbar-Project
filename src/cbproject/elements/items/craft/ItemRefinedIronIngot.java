package cbproject.elements.items.craft;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class ItemRefinedIronIngot extends Item{

	public ItemRefinedIronIngot(int par1) {
		
		super(par1);
		setUnlocalizedName("refinedIron");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(64);

	}

}
