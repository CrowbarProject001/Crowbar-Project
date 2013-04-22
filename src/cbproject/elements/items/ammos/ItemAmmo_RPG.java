package cbproject.elements.items.ammos;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;

public class ItemAmmo_RPG extends ItemAmmo {

	public ItemAmmo_RPG(int par1) {
		
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("ammo_rpg");
		setMaxStackSize (5);
		
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:ammo_rpg");
    }

}
