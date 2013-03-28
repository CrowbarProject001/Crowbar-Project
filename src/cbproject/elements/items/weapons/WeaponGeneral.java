package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class WeaponGeneral extends Item {
	
	protected List listItemStack;
	public int mode, type;
	public int ammoID;
	
	public WeaponGeneral(int par1, int par2AmmoID) {
		super(par1);
		bFull3D = true;
		listItemStack = new ArrayList();
		ammoID = par2AmmoID;
		// TODO Auto-generated constructor stub
	}
	
	public abstract InformationSet loadInformation(ItemStack par1Itack, EntityPlayer entityPlayer);
	public abstract InformationSet getInformation(ItemStack itemStack);
	
	public InformationWeapon getSpecInformation(ItemStack itemStack,World world){
		InformationSet inf =  getInformation(itemStack);
		if(inf != null)
			return (world.isRemote? inf.getClientAsBullet(): inf.getServerAsBullet());
		return null;
	}

}
