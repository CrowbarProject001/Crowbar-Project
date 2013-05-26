package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;


public class IngotCopper extends Item {

	public IngotCopper(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("ingotCopper");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:copper");
	}
}
