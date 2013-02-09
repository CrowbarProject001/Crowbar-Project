package cbproject.Items.Weapon;

import cbproject.Proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class weapon_crowbar extends ItemSword {

	public weapon_crowbar(int item_id) {
		super(item_id, EnumToolMaterial.IRON);
		
		setItemName("Crowbar");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(1, 0);
		setCreativeTab(CreativeTabs.tabTools);
	}

}
