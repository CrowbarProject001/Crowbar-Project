package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemAmmo_9mm extends ItemAmmo {

	public ItemAmmo_9mm(int par1) {
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("ammo_9mm");
		setIconCoord(1,1);
		setMaxDamage(17);
		setMaxStackSize (1);
	}

}
