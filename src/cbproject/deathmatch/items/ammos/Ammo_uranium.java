package cbproject.deathmatch.items.ammos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;

public class Ammo_uranium extends ItemAmmo {

	public Ammo_uranium(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_uranium");
		setMaxDamage(100);
	}
}
