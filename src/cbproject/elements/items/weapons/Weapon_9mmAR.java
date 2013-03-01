package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cbproject.CBCMod;

public class Weapon_9mmAR extends WeaponGeneralBullet {
	public static final int pShootTime[]= { 4 } , pReloadTime = 45;
	public Weapon_9mmAR(int par1) {
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID , pReloadTime, pShootTime ,"","");
		setIconCoord(3,2);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
		
	}

}
