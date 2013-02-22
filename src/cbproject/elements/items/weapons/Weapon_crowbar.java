package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class Weapon_crowbar extends ItemSword {

	public Weapon_crowbar(int item_id) {
		super(item_id, EnumToolMaterial.IRON); //铁剑的基本属性
		
		setItemName("weapon_rowbar");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(0, 2);
		setCreativeTab( CBCMod.cct );
	}

}
