package cbproject.utils.weapons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationSatchel extends InformationWeapon {
	
	public List list;
	public InformationSatchel(EntityPlayer ent, ItemStack itemStack) {
		super(ent, itemStack);
		list = new ArrayList();
		ammoManager = new AmmoManager(ent, itemStack);
	}

}
