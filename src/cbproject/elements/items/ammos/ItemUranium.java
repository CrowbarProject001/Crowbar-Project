package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class ItemUranium extends Item {

	public ItemUranium(int par1) {
		super(par1);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setCreativeTab(CBCMod.cct);
		// TODO Auto-generated constructor stub
	}

}
