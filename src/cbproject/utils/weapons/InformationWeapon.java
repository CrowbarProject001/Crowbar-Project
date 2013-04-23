package cbproject.utils.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationWeapon {
	
	public EntityPlayer player;
	public ItemStack itemStack;
	public int ticksExisted, lastTick, recoverTick, mode;
	public AmmoManager ammoManager;
	public Boolean isRecovering;
	public float originPitch;
	
	public InformationWeapon(EntityPlayer p, ItemStack i) {
		isRecovering = false;
		player = p;
		itemStack = i;
	}
	
	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}

	public int getDeltaTick(){
		return ticksExisted - lastTick;
	}
	
	public void resetState(){
		ticksExisted = lastTick = recoverTick = 0;
		isRecovering = false;
	}

}
