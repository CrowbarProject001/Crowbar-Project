package cbproject.deathmatch.items.ammos;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.proxy.ClientProxy;

public class ItemAmmo_9mm extends ItemAmmo {

	public ItemAmmo_9mm(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("ammo_9mmhandgun");
		setMaxDamage(18);
		setMaxStackSize (1);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:ammo_9mmhandgun");
    }

}
