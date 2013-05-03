package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class IngotSteel extends Item{

	public IngotSteel(int par1) {
		super(par1);
		setUnlocalizedName("ingotSteel");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(64);
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:steel");
    }
}
