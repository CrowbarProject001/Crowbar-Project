package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.client.HEVRenderingUtils;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ammo_argrenade extends ItemAmmo {

	public Ammo_argrenade(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_argrenade");
		setMaxStackSize(10);
	}

	@Override
	public Icon getHudIcon() {
		return HEVRenderingUtils.getHudSheetIcon(16, 32, "grenade");
	}

	@Override
	public int getIconSheetIndex(ItemStack stack) {
		return 5;
	}
}
