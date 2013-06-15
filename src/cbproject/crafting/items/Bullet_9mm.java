package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;

public class Bullet_9mm extends ItemBullet {

	public Bullet_9mm(int par1) {
		super(par1);
		setCreativeTab( CBCMod.cctMisc );
		setIAndU("bullet_9mm");
		setMaxStackSize(64);
	}

}
