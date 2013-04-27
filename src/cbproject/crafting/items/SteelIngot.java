package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class SteelIngot extends Item{

	public SteelIngot(int par1) {
		
		super(par1);
		setUnlocalizedName("refinedStell");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(64);

	}

    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:steel");
    }
}
