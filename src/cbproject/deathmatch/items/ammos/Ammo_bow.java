package cbproject.deathmatch.items.ammos;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;

public class Ammo_bow extends ItemAmmo {

	public Ammo_bow(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_bow");
		setMaxDamage(6);
	}
}
