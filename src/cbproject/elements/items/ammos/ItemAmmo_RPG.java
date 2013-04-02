package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemAmmo_RPG extends ItemAmmo {

	public ItemAmmo_RPG(int par1) {
		
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("ammo_rpg");
		setIconCoord(7,1);
		setMaxStackSize (5);
		
	}

}
