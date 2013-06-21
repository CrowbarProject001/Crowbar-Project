package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.client.HEVRenderingUtils;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ammo_357 extends ItemAmmo {

	public Ammo_357(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(64);
		setIAndU("ammo_357");
	}

	@Override
	public Icon getHudIcon() {
		return HEVRenderingUtils.getHudSheetIcon(0, 32, "357");
	}

	@Override
	public int getIconSheetIndex(ItemStack stack) {
		return 5;
	} 
}