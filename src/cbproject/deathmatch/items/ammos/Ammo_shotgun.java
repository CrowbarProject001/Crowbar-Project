package cbproject.deathmatch.items.ammos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;

public class Ammo_shotgun extends ItemAmmo {

	public Ammo_shotgun(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_shotgun");
	}
}
