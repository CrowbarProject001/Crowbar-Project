package cbproject.utils.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationBulletWeapon extends InformationWeapon{
	//357,9mmAR,9mmhandgun等的辅助类
	
	public int ticksExisted, lastTick;
	public AmmoManager ammoManager;
	public Boolean canUse, isShooting,isReloading, rsp;
	
	public InformationBulletWeapon(EntityPlayer par7Player, ItemStack par8Weapon) {
		
		ticksExisted = 0;
		lastTick = 0;
		recoverTick = 0;
		
		
		canUse = false;
		isShooting = false;
		isReloading = false;
		isRecovering = false;
		
		ammoManager = new AmmoManager(par7Player, par8Weapon);
		rsp = false;

	}
	
	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}

}
