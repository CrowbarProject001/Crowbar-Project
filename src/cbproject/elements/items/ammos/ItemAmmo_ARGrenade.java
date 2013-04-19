package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemAmmo_ARGrenade extends ItemAmmo {

	public ItemAmmo_ARGrenade(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setMaxStackSize(10);
		setIconCoord(4, 1);
	}

}
