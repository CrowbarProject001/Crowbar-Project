package cbproject.elements.items.bullets;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class ItemBullet_Bow extends Item {

	public ItemBullet_Bow(int par1) {
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("bullet_bow");
		setIconCoord(3,5);
		setMaxStackSize (64);
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}

}
