package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemAmmo_9mm2 extends ItemAmmo {

	public ItemAmmo_9mm2(int par1) {
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("ammo_9mm2");
		setIconCoord(2,1);
		setMaxDamage(51);
		setMaxStackSize (1);
		// TODO Auto-generated constructor stub
	}

}
