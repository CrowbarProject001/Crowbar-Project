package cbproject.utils.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationBullet extends InformationWeapon{
	//357,9mmAR,9mmhandgun等的辅助类
	
	public int ticksExisted, lastTick;
	public AmmoManager ammoManager;
	public Boolean isShooting,isReloading;
	
	public InformationBullet(EntityPlayer par7Player, ItemStack par8Weapon) {
		
		ticksExisted = 0;
		lastTick = 0;
		recoverTick = 0;
		
		isShooting = false;
		isReloading = false;
		isRecovering = false;
		
		ammoManager = new AmmoManager(par7Player, par8Weapon);

	}
	
	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}

}
