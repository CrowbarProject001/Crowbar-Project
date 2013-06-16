package cbproject.deathmatch.items.ammos;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;

public class Ammo_9mm2 extends ItemAmmo {

	public Ammo_9mm2(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("ammo_9mmar");
		setMaxDamage(51);
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:ammo_9mmar");
	}

}
