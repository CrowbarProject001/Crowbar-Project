package cbproject.utils.weapons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationSatchel extends InformationWeapon {
	
	public int[] satchelIDs;
	public InformationSatchel(EntityPlayer player, ItemStack itemStack) {
		super(player, itemStack);
		satchelIDs = new int[6];
	}
	
	public void clearSatchel(){
		for(int i = 0; i < satchelIDs.length; i++)
			satchelIDs[i] = 0;
	}
	
	public Boolean addSatchel(int entityID){
		for(int i= 0; i < satchelIDs.length; i++){
			if(satchelIDs[i] == 0){
				satchelIDs[i] = entityID;
				return true;
			}
		}
		return false;
	}
	
	public Boolean canAddSatchel(){
		for(int i : satchelIDs){
			if(i == 0)
				return true;
		}
		return false;
	}
}
