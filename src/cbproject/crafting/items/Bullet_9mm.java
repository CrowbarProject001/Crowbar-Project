package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;

public class Bullet_9mm extends ItemBullet {

	public Bullet_9mm(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("bullet_9mm");
		setMaxStackSize (64);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:bullet_9mm");
    }

}
