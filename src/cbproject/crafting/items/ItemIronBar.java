package cbproject.crafting.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemIronBar extends Item {

	public ItemIronBar(int par1) {
		super(par1);
		setUnlocalizedName("ironbar");
	}

    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:ironbar");
    }
}
