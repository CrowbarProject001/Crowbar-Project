package cbproject.elements.items.bullets;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class ItemBullet_9mm extends Item {

	public ItemBullet_9mm(int par1) {
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("bullet_9mm");
		setIconCoord(0,5);
		setMaxStackSize (64);
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}

}
