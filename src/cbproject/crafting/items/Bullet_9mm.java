package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.register.CBCItems;
import cbproject.deathmatch.items.ammos.ItemAmmo;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class Bullet_9mm extends ItemBullet {

	public Bullet_9mm(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cct );
		setUnlocalizedName("bullet_9mm");
		setMaxStackSize (64);
	}
	
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:bullet_9mm");
    }

}
