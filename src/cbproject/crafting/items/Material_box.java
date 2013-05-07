package cbproject.crafting.items;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Material_box extends ItemMaterial {

	public Material_box(int par1) {
		super(par1);
		this.setUnlocalizedName("mat_box");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:mat_box");
    }

}
