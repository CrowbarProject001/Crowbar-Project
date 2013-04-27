package cbproject.crafting.items;

import cbproject.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class LambdaChipset extends Item {

	public LambdaChipset(int par1) {
		super(par1);
		setUnlocalizedName("lambdachip");
		setCreativeTab(CBCMod.cct);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:lambdachip");
    }

}
