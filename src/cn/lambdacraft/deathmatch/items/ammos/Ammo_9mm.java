package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ammo_9mm extends ItemAmmo {

	public Ammo_9mm(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_9mmhandgun");
		setMaxDamage(19);
	}
}
