package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.api.hud.IHudIconProvider;
import cn.lambdacraft.core.item.CBCGenericItem;
import net.minecraft.item.Item;

public abstract class ItemAmmo extends CBCGenericItem implements IHudIconProvider {
	public ItemAmmo(int par1) {
		super(par1);
		setMaxStackSize(1);
	}
}
