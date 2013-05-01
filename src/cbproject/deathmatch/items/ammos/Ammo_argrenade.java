package cbproject.deathmatch.items.ammos;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;

public class Ammo_argrenade extends ItemAmmo {

	public Ammo_argrenade(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("ammo_argrenade");
		setMaxStackSize(10);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:ammo_argrenade");
    }
	
}
