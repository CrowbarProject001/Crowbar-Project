package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.client.HEVRenderingUtils;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ammo_9mm extends ItemAmmo {

	public Ammo_9mm(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_9mmhandgun");
		setMaxDamage(19);
	}

	@Override
	public Icon getHudIcon() {
		return HEVRenderingUtils.getHudSheetIcon(0, 32, "9mm");
	}

	@Override
	public int getIconSheetIndex(ItemStack stack) {
		return 5;
	}
}
