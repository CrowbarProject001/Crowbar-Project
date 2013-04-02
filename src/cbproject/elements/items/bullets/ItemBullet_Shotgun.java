package cbproject.elements.items.bullets;

import net.minecraft.item.Item;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemBullet_Shotgun extends Item{

	public ItemBullet_Shotgun(int par1) {
		super(par1);
		setTextureFile( ClientProxy.ITEMS_TEXTURE_PATH );
		setCreativeTab( CBCMod.cct );
		setItemName("bullet_shotgun");
		setIconCoord(5,1);
		setMaxStackSize (64);
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}

}
