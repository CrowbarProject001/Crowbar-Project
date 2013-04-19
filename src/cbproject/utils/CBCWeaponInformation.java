/**
 * 
 */
package cbproject.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

/**
 * @author WeAthFolD
 * @description General weapon information storage class.
 */
public class CBCWeaponInformation {

	private static HashMap<Double, InformationSet> map;
	
	public CBCWeaponInformation() {
		map = new HashMap<Double, InformationSet>();
	}
	
	public static void addToList(double uniqueID, InformationSet inf){
		map.put(uniqueID, inf);
	}
	
	public static InformationSet getInformation(double uniqueID){
		return map.get(uniqueID);
	}
	
	public static InformationSet getInformation(ItemStack itemStack){
		if(itemStack.getTagCompound() == null){
			itemStack.stackTagCompound = new NBTTagCompound();
			return null;
		}
		double uniqueID = itemStack.getTagCompound().getDouble("uniqueID");
		return map.get(uniqueID);
	}

}
