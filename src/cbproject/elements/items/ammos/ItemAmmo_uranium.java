package cbproject.elements.items.ammos;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class ItemAmmo_uranium extends ItemAmmo {

	public ItemAmmo_uranium(int par1) {
		
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("ammo_uranium");
		setIconCoord(0,1);
		setMaxDamage( 100 );
		setMaxStackSize ( 1 );
		// TODO Auto-generated constructor stub
	}

}
