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
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Crowbar_Electrical;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_RPG;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Crossbow;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Crowbar;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Egon;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Gauss;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Hgrenade;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Hornet;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Satchel;
import cn.lambdacraft.deathmatch.items.wpns.Weapon_Shotgun;
import cn.lambdacraft.intergration.ic2.item.ArmorHEVIC2;
import net.minecraft.item.Item;

public class DMItems {

	public static Weapon_Crowbar weapon_crowbar;
	public static Weapon_Hgrenade weapon_hgrenade;

	public static Weapon_Gauss weapon_gauss;
	public static Weapon_Satchel weapon_satchel;
	public static Weapon_Egon weapon_egon;
	public static Item weapon_9mmhandgun;
	public static Item weapon_9mmAR;
	public static Item weapon_357;
	public static Item weapon_shotgun;
	public static Weapon_Hornet weapon_hornet;
	public static Weapon_RPG weapon_RPG;
	public static Weapon_Crossbow weapon_crossbow;
	public static ItemMedkit medkit;
	public static Weapon_Crowbar_Electrical weapon_crowbar_el;
	
	public static ArmorHEV armorHEVBoot, armorHEVLeggings, armorHEVChestplate,
			armorHEVHelmet;
	public static ArmorLongjump longjump;

	public static void init(Config conf) {

		weapon_crowbar = new Weapon_Crowbar(GeneralRegistry.getItemId(
				"weapon_crowbar", 1));

		weapon_hgrenade = new Weapon_Hgrenade(GeneralRegistry.getItemId(
				"weapon_hgrenade", 1));
		weapon_9mmhandgun = new Weapon_9mmhandgun(GeneralRegistry.getItemId(
				"weapon_nmmhandgun", 1));
		weapon_9mmAR = new Weapon_9mmAR(GeneralRegistry.getItemId(
				"weapon_nmmAR", 1));
		weapon_357 = new Weapon_357(GeneralRegistry.getItemId("weapon_357", 1));
		weapon_shotgun = new Weapon_Shotgun(GeneralRegistry.getItemId(
				"weapon_shotgun", 1));
		weapon_RPG = new Weapon_RPG(GeneralRegistry.getItemId("weapon_RPG", 1));
		weapon_crossbow = new Weapon_Crossbow(GeneralRegistry.getItemId(
				"weapon_crossbow", 1));
		weapon_satchel = new Weapon_Satchel(GeneralRegistry.getItemId(
				"weapon_satchel", 1));

		weapon_gauss = new Weapon_Gauss(GeneralRegistry.getItemId(
				"weapon_gauss", 1));
		weapon_egon = new Weapon_Egon(GeneralRegistry.getItemId("weapon_egon",
				1));
		weapon_hornet = new Weapon_Hornet(GeneralRegistry.getItemId(
				"weapon_hornet", 1));
		if(!CBCMod.ic2Installed) {
			armorHEVHelmet = new ArmorHEV(GeneralRegistry.getItemId("hevHelmet",
				GeneralProps.CAT_EQUIPMENT), 0);
			armorHEVChestplate = new ArmorHEV(GeneralRegistry.getItemId(
				"hevChestplate", 3), 1);
			armorHEVLeggings = new ArmorHEV(GeneralRegistry.getItemId(
				"hevLeggings", 3), 2);
			armorHEVBoot = new ArmorHEV(GeneralRegistry.getItemId("hevBoot", 3), 3);
			weapon_crowbar_el = new Weapon_Crowbar_Electrical(GeneralRegistry.getItemId("weapon_crowbar_el", 1));
			
		}
		
		longjump = new ArmorLongjump(GeneralRegistry.getItemId("armorLongjump",
				3), 1);
		
		medkit = new ItemMedkit(GeneralRegistry.getItemId("medkit", 3));

	}
}
