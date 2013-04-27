package cbproject.deathmatch.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationBullet extends InformationWeapon{
	//357,9mmAR,9mmhandgun等的辅助类
	
	public int ticksExisted, lastTick;
	public Boolean isShooting,isReloading;
	
	public InformationBullet(ItemStack par8Weapon, EntityPlayer par7Player) {
		
		super(par7Player, par8Weapon);
		ticksExisted = 0;
		lastTick = 0;
		recoverTick = 0;
		
		isShooting = false;
		isReloading = false;

	}
	
}
