package cbproject.crafting.items;

import cbproject.core.CBCMod;
import net.minecraft.item.Item;

public class ItemBullet_9mm extends Item {

	public ItemBullet_9mm(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("bullet_9mm");
		setMaxStackSize (64);
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}

}
