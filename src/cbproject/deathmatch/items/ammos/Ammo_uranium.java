package cbproject.deathmatch.items.ammos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;

public class Ammo_uranium extends ItemAmmo {

	public Ammo_uranium(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("ammo_uranium");
		setMaxDamage( 100 );
		setMaxStackSize ( 1 );
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:ammo_uranium");
    }

}
