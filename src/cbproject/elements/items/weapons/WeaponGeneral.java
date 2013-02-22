package cbproject.elements.items.weapons;

import cbproject.utils.weapons.AmmoManager;
import cbproject.utils.weapons.BulletManager;
import net.minecraft.item.Item;

public abstract class WeaponGeneral extends Item {
	
	protected AmmoManager ammoManager;
	
	public int mode;
	public int ammoID;
	
	public WeaponGeneral(int par1 , int par2ammoID) {
		super(par1);
		ammoID = par2ammoID;
		// TODO Auto-generated constructor stub
	}

}
