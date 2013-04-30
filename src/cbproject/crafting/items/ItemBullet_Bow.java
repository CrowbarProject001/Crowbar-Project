package cbproject.crafting.items;

import cbproject.core.CBCMod;
import net.minecraft.item.Item;

public class ItemBullet_Bow extends Item {

	public ItemBullet_Bow(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("bullet_bow");
		setMaxStackSize (64);
	}
	
	@Override
	public boolean isDamageable(){
		return false;
	}

}
