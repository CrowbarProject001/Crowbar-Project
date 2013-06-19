package cn.lambdacraft.mob.register;

import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.mob.items.Weapon_snark;
import net.minecraft.item.Item;

public class CBCMobItems {

	public static Item weapon_snark;

	public static void init(Config conf) {
		try {
			weapon_snark = new Weapon_snark(GeneralRegistry.getItemId("snark",
					1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
