package cbproject.elements.Items.weapons;

import cbproject.proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Weapon_hgrenade extends Item {

	public Weapon_hgrenade(int par1ID) {
		super(par1ID);
		setItemName("weapon_hgrenade");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(2,0);
		setCreativeTab(CreativeTabs.tabTools);
		// TODO Auto-generated constructor stub
	}
	
	
}
