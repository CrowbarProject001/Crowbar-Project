package cbproject.crafting.items;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Material_light extends ItemMaterial {

	public Material_light(int par1) {
		super(par1);
		setUnlocalizedName("mat_light");
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:mat_lightweapon");
    }
	
}
