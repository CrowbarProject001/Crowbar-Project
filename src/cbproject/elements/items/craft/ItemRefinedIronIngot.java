package cbproject.elements.items.craft;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class ItemRefinedIronIngot extends Item{

	public ItemRefinedIronIngot(int par1) {
		
		super(par1);
		setItemName("itemRefinedIronIngot");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(1,0);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(64);

	}

}
