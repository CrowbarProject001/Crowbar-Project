package cbproject.mob.register;

import cbproject.core.misc.Config;
import cbproject.core.register.GeneralRegistry;
import cbproject.mob.items.Weapon_snark;
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
