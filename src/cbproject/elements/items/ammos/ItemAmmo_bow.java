package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemAmmo_bow extends ItemAmmo {

	public ItemAmmo_bow(int par1) {
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("ammo_bow");
		setIconCoord(1,1);
		setMaxDamage(6);
		setMaxStackSize (1);
		// TODO Auto-generated constructor stub
	}

}
