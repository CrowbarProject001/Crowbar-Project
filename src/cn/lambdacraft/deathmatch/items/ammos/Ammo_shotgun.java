package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;

public class Ammo_shotgun extends ItemAmmo {

	public Ammo_shotgun(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(64);
		setIAndU("ammo_shotgun");
	}
}
