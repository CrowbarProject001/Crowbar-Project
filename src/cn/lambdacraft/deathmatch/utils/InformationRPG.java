package cn.lambdacraft.deathmatch.utils;

import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.deathmatch.entities.EntityRPGDot;
import net.minecraft.item.ItemStack;

public class InformationRPG extends InformationBullet {

	public EntityRPGDot currentDot;

	public InformationRPG(ItemStack par8Weapon) {
		super(par8Weapon);
	}

}
