/**
 * 
 */
package cbproject.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cbproject.deathmatch.utils.InformationSet;
import cbproject.deathmatch.utils.InformationWeapon;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;


/**
 * @author WeAthFolD
 * @description General weapon information storage class.
 */
public class CBCWeaponInformation {

	private static HashMap<Double, InformationSet> map;
	
	public CBCWeaponInformation() {
		map = new HashMap<Double, InformationSet>();
	}
	
	public static InformationSet addToList(double uniqueID, InformationSet inf){
		map.put(uniqueID, inf);
		return inf;
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
