package cbproject.elements.items.weapons;

import java.util.List;

import cbproject.CBCMod;
import cbproject.utils.weapons.AmmoManager;
import cbproject.utils.weapons.BulletManager;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class WeaponGeneral extends Item {
	
	protected AmmoManager ammoManager;
	
	public List listItemStack;
	public int mode;
	public int ammoID;
	
	public WeaponGeneral(int par1 , int par2ammoID) {
		super(par1);
		ammoID = par2ammoID;
		// TODO Auto-generated constructor stub
	}
	

}
