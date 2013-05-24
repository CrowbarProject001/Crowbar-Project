package cbproject.mob.register;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.core.misc.Config;
import cbproject.mob.items.Weapon_snark;
import net.minecraft.item.Item;

public class CBCMobItems {
	
	public static Item weapon_snark;
	
	public static void init(Config conf){
		try {
			weapon_snark = new Weapon_snark(conf.GetItemID("weapon_snark", 7010));
		} catch (Exception e) {
			e.printStackTrace();
		}
		LanguageRegistry.addName(weapon_snark, "Snark");
	}
	
}