/**
 * 
 */
package cbproject.core.utils;

import java.util.HashMap;
import cbproject.deathmatch.utils.InformationSet;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


/**
 * 武器信息的公用存储。
 * @author WeAthFolD
 */
public class CBCWeaponInformation {

	private static HashMap<Double, InformationSet> map = new HashMap();
	
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
