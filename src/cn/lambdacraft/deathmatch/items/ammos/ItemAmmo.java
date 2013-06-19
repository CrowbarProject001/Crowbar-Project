package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.item.CBCGenericItem;
import net.minecraft.item.Item;

public abstract class ItemAmmo extends CBCGenericItem {
	public ItemAmmo(int par1) {
		super(par1);
		setMaxStackSize(1);
	}
}
