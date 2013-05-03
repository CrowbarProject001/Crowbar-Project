package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class IngotUranium extends Item {

	public IngotUranium(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("uranium");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:uranium");
    }
	
}
