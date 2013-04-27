package cbproject.deathmatch.items.ammos;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.proxy.ClientProxy;

public class ItemAmmo_9mm2 extends ItemAmmo {

	public ItemAmmo_9mm2(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("ammo_9mmar");
		setMaxDamage(51);
		setMaxStackSize (1);
		// TODO Auto-generated constructor stub
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:ammo_9mmar");
    }
	
}
