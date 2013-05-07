package cbproject.crafting.items;

import cbproject.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;

public class Bullet_steelbow extends ItemBullet{

	public Bullet_steelbow(int id) {
		super(id);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("bullet_steelbow");
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:steelbow");
    }

}
