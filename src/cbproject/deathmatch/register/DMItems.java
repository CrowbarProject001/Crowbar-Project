package cbproject.deathmatch.register;

import net.minecraft.item.Item;
import cbproject.core.misc.Config;
import cbproject.core.register.GeneralRegistry;
import cbproject.deathmatch.items.ArmorHEV;
import cbproject.deathmatch.items.ArmorLongjump;
import cbproject.deathmatch.items.ItemBattery;
import cbproject.deathmatch.items.ItemMedkit;
import cbproject.deathmatch.items.ArmorHEV.EnumAttachment;
import cbproject.deathmatch.items.wpns.Weapon_357;
import cbproject.deathmatch.items.wpns.Weapon_9mmAR;
import cbproject.deathmatch.items.wpns.Weapon_9mmhandgun;
import cbproject.deathmatch.items.wpns.Weapon_RPG;
import cbproject.deathmatch.items.wpns.Weapon_crossbow;
import cbproject.deathmatch.items.wpns.Weapon_crowbar;
import cbproject.deathmatch.items.wpns.Weapon_egon;
import cbproject.deathmatch.items.wpns.Weapon_gauss;
import cbproject.deathmatch.items.wpns.Weapon_hgrenade;
import cbproject.deathmatch.items.wpns.Weapon_hornet;
import cbproject.deathmatch.items.wpns.Weapon_satchel;
import cbproject.deathmatch.items.wpns.Weapon_shotgun;

public class DMItems {
	
	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;
	
	public static Weapon_gauss weapon_gauss;
	public static Weapon_satchel weapon_satchel;
	public static Item weapon_egon;
	public static Item weapon_9mmhandgun;
	public static Item weapon_9mmAR;
	public static Item weapon_357;
	public static Item weapon_shotgun;
	public static Weapon_hornet weapon_hornet;
	public static Weapon_RPG weapon_RPG;
	public static Item weapon_crossbow;
	public static ItemBattery battery;
	public static ItemMedkit medkit;
	
	public static ArmorHEV armorHEVBoot, armorHEVLeggings, armorHEVChestplate, armorHEVHelmet;
	public static ArmorHEV hevLongjump;
	public static ArmorLongjump longjump;
	
	public static void init(Config conf){
		
		weapon_crowbar = new Weapon_crowbar(GeneralRegistry.getItemId("weapon_crowbar", 1));
		
		weapon_hgrenade = new Weapon_hgrenade(GeneralRegistry.getItemId("weapon_hgrenade", 1));
		weapon_9mmhandgun = new Weapon_9mmhandgun(GeneralRegistry.getItemId("weapon_nmmhandgun", 1));
		weapon_9mmAR = new Weapon_9mmAR(GeneralRegistry.getItemId("weapon_nmmAR", 1));
		weapon_357 = new Weapon_357(GeneralRegistry.getItemId("weapon_357", 1));
		weapon_shotgun = new Weapon_shotgun(GeneralRegistry.getItemId("weapon_shotgun", 1));
		weapon_RPG = new Weapon_RPG(GeneralRegistry.getItemId("weapon_RPG", 1));
		weapon_crossbow = new Weapon_crossbow(GeneralRegistry.getItemId("weapon_crossbow", 1));	
		weapon_satchel = new Weapon_satchel(GeneralRegistry.getItemId("weapon_satchel", 1));
		
		weapon_gauss = new Weapon_gauss(GeneralRegistry.getItemId("weapon_gauss", 1));
		weapon_egon = new Weapon_egon(GeneralRegistry.getItemId("weapon_egon", 1));
		weapon_hornet = new Weapon_hornet(GeneralRegistry.getItemId("weapon_hornet", 1));
		
		armorHEVHelmet = new ArmorHEV(GeneralRegistry.getItemId("hevHelmet", 8002), 3);
		armorHEVChestplate = new ArmorHEV(GeneralRegistry.getItemId("hevChestplate", 8003), 3);
		armorHEVLeggings = new ArmorHEV(GeneralRegistry.getItemId("hevLeggings", 8004), 3);
		armorHEVBoot = new ArmorHEV(GeneralRegistry.getItemId("hevBoot", 8005), 3);
		longjump = new ArmorLongjump(GeneralRegistry.getItemId("armorLongjump", 4006), 3);
		hevLongjump = new ArmorHEV(GeneralRegistry.getItemId("hevLongjump", 3), EnumAttachment.LONGJUMP);
		
		battery = new ItemBattery(GeneralRegistry.getItemId("battery", 3));
		medkit = new ItemMedkit(GeneralRegistry.getItemId("medkit", 3));
		
	}
}
