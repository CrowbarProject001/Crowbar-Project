package cn.lambdacraft.deathmatch.register;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.deathmatch.items.ArmorHEV;
import cn.lambdacraft.deathmatch.items.ArmorLongjump;
import cn.lambdacraft.deathmatch.items.ItemMedkit;
import cn.lambdacraft.deathmatch.items.ArmorHEV.EnumAttachment;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_357;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_9mmAR;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_9mmhandgun;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_RPG;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_crossbow;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_crowbar;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_egon;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_gauss;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_hgrenade;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_hornet;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_satchel;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_shotgun;
import cn.lambdacraft.intergration.ic2.item.ArmorHEVIC2;
import net.minecraft.item.Item;

public class DMItems {

	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;

	public static Weapon_gauss weapon_gauss;
	public static Weapon_satchel weapon_satchel;
	public static Weapon_egon weapon_egon;
	public static Item weapon_9mmhandgun;
	public static Item weapon_9mmAR;
	public static Item weapon_357;
	public static Item weapon_shotgun;
	public static Weapon_hornet weapon_hornet;
	public static Weapon_RPG weapon_RPG;
	public static Weapon_crossbow weapon_crossbow;
	public static ItemMedkit medkit;

	public static ArmorHEV armorHEVBoot, armorHEVLeggings, armorHEVChestplate,
			armorHEVHelmet;
	public static ArmorHEV hevLongjump;
	public static ArmorLongjump longjump;

	public static void init(Config conf) {

		weapon_crowbar = new Weapon_crowbar(GeneralRegistry.getItemId(
				"weapon_crowbar", 1));

		weapon_hgrenade = new Weapon_hgrenade(GeneralRegistry.getItemId(
				"weapon_hgrenade", 1));
		weapon_9mmhandgun = new Weapon_9mmhandgun(GeneralRegistry.getItemId(
				"weapon_nmmhandgun", 1));
		weapon_9mmAR = new Weapon_9mmAR(GeneralRegistry.getItemId(
				"weapon_nmmAR", 1));
		weapon_357 = new Weapon_357(GeneralRegistry.getItemId("weapon_357", 1));
		weapon_shotgun = new Weapon_shotgun(GeneralRegistry.getItemId(
				"weapon_shotgun", 1));
		weapon_RPG = new Weapon_RPG(GeneralRegistry.getItemId("weapon_RPG", 1));
		weapon_crossbow = new Weapon_crossbow(GeneralRegistry.getItemId(
				"weapon_crossbow", 1));
		weapon_satchel = new Weapon_satchel(GeneralRegistry.getItemId(
				"weapon_satchel", 1));

		weapon_gauss = new Weapon_gauss(GeneralRegistry.getItemId(
				"weapon_gauss", 1));
		weapon_egon = new Weapon_egon(GeneralRegistry.getItemId("weapon_egon",
				1));
		weapon_hornet = new Weapon_hornet(GeneralRegistry.getItemId(
				"weapon_hornet", 1));
		if(!CBCMod.ic2Installed) {
			armorHEVHelmet = new ArmorHEV(GeneralRegistry.getItemId("hevHelmet",
				GeneralProps.CAT_EQUIPMENT), 0);
			armorHEVChestplate = new ArmorHEV(GeneralRegistry.getItemId(
				"hevChestplate", 3), 1);
			armorHEVLeggings = new ArmorHEV(GeneralRegistry.getItemId(
				"hevLeggings", 3), 2);
			armorHEVBoot = new ArmorHEV(GeneralRegistry.getItemId("hevBoot", 3), 3);
			hevLongjump = new ArmorHEV(GeneralRegistry.getItemId("hevLongjump", 3),
					EnumAttachment.LONGJUMP);
		}
		
		longjump = new ArmorLongjump(GeneralRegistry.getItemId("armorLongjump",
				3), 1);
		
		medkit = new ItemMedkit(GeneralRegistry.getItemId("medkit", 3));

	}
}
