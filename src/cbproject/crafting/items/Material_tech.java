package cbproject.crafting.items;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Material_tech extends ItemMaterial {

	public Material_tech(int par1) {
		super(par1);
		setUnlocalizedName("mat_tech");
	}
	
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:mat_tech");
    }

}
