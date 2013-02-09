package cbproject.Items;

import cbproject.Proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class Crowbar extends ItemSword {

	public Crowbar(int item_id) {
		super(item_id, EnumToolMaterial.IRON);
		
		setItemName("Crowbar");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(1, 0);
		setCreativeTab(CreativeTabs.tabTools);
	}

}
