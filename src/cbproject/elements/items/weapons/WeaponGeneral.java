package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;

public class WeaponGeneral extends Item {
	
	public List listItemStack;
	public int mode;
	public int ammoID;
	
	public WeaponGeneral(int par1, int par2AmmoID) {
		super(par1);
		bFull3D = true;
		listItemStack = new ArrayList();
		ammoID = par2AmmoID;
		// TODO Auto-generated constructor stub
	}

}
