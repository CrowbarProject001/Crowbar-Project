package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemAmmo_357 extends ItemAmmo {

	public ItemAmmo_357(int par1) {
		
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("ammo_357");
		setIconCoord(1,1);
		setMaxDamage(7);
		setMaxStackSize (1);
		
	}

}
