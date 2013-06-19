package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ammo_357 extends ItemAmmo {

	public Ammo_357(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(64);
		setIAndU("ammo_357");
	} 
}
