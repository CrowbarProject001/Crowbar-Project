package cbproject.deathmatch.utils;

import net.minecraft.item.ItemStack;

public class InformationWeapon {

	public ItemStack itemStack;
	public int ticksExisted, lastTick, recoverTick;
	public Boolean isRecovering;
	public float originPitch;

	public InformationWeapon(ItemStack i) {
		isRecovering = false;
		itemStack = i;
	}

	public void updateTick() {
		ticksExisted++;
	}

	public void setLastTick() {
		lastTick = ticksExisted;
	}

	public int getDeltaTick() {
		return ticksExisted - lastTick;
	}

	public void resetState() {
		ticksExisted = lastTick = recoverTick = 0;
		isRecovering = false;
	}

}
