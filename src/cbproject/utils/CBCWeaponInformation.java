/**
 * 
 */
package cbproject.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

/**
 * @author WeAthFolD
 * @description General weapon information storage class.
 */
public class CBCWeaponInformation {

	private static List list;
	
	public CBCWeaponInformation() {
		list = new ArrayList();
	}
	
	public static int addToList(InformationSet inf){
		list.add(inf);
		return list.size() - 1;
	}
	
	public static InformationSet getInformation(int id){
		if(id >= list.size())
			return null;
		return (InformationSet) list.get(id);
	}
	
	public static InformationSet getInformationWithCheck(int id, double uniqueID){
		if(id >= list.size())
			return null;
		InformationSet i = (InformationSet) list.get(id);
		if(i.signID != uniqueID){
			System.out.println("ID doesn't match. " + "signID : " + i.signID + " uniqueID : " + uniqueID);
			return null;
		}
		return i;
	}
	
	public static InformationSet getInformation(ItemStack itemStack, World world){
		if(itemStack.getTagCompound() == null)
			return null;
		int id = itemStack.getTagCompound().getInteger("weaponID");
		double uniqueID = itemStack.getTagCompound().getDouble("uniqueID");
		if(id >= list.size())
			return null;
		InformationSet inf = (InformationSet) list.get(id);
		if(inf.signID != uniqueID)
			return null;
		return inf;
	}
	
	public static InformationSet getInformation(ItemStack itemStack){
		if(itemStack.getTagCompound() == null)
			return null;
		int id = itemStack.getTagCompound().getInteger("weaponID");
		double uniqueID = itemStack.getTagCompound().getDouble("uniqueID");
		if(id >= list.size())
			return null;
		InformationSet inf = (InformationSet) list.get(id);
		if(inf.signID != uniqueID)
			return null;
		return inf;
	}
	

}
