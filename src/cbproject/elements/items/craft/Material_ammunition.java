package cbproject.elements.items.craft;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Material_ammunition extends ItemMaterial{

	public Material_ammunition(int par1) {
		super(par1);
		setUnlocalizedName("mat_ammunition");
	}

    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:mat_ammunition");
    }
}
