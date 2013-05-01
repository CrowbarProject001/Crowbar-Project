package cbproject.deathmatch.items.ammos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class Ammo_shotgun extends ItemAmmo{

	public Ammo_shotgun(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("ammo_shotgun");
		setMaxStackSize (64);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:ammo_shotgun");
    }
	
}
