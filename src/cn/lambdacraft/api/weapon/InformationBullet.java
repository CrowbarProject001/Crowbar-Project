package cn.lambdacraft.api.weapon;

import net.minecraft.item.ItemStack;

public class InformationBullet extends InformationWeapon {
	// 357,9mmAR,9mmhandgun等的辅助类

	public Boolean isShooting, isReloading;

	public InformationBullet(ItemStack par8Weapon) {

		super(par8Weapon);
		ticksExisted = 0;
		lastTick = 0;
		recoverTick = 0;

		isShooting = false;
		isReloading = false;

	}

}
