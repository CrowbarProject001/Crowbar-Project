package cbproject.elements.items.bullets;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class ItemBullet_357 extends Item {

	public ItemBullet_357(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("bullet_357");
		setMaxStackSize (64);
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}

}
