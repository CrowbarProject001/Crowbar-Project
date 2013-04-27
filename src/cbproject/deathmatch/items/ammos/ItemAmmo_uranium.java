package cbproject.deathmatch.items.ammos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemAmmo_uranium extends ItemAmmo {

	public ItemAmmo_uranium(int par1) {
		
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
