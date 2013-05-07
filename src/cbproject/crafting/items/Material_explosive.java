package cbproject.crafting.items;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Material_explosive extends ItemMaterial {

	public Material_explosive(int par1) {
		super(par1);
		setUnlocalizedName("mat_explosive");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:mat_explosive");
    }

}
