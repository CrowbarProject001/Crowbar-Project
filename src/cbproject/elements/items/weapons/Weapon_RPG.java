package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.item.Item;

public class Weapon_RPG extends Item {

	public Weapon_RPG(int par1) {
		super(par1);
		setItemName("weapon_rpg");
		
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(7,2);
		setCreativeTab(CBCMod.cct);
		// TODO Auto-generated constructor stub
	}

}
