package cbproject.deathmatch.register;

import net.minecraft.item.Item;
import cbproject.core.misc.Config;
import cbproject.deathmatch.items.ArmorHEV;
import cbproject.deathmatch.items.ArmorLongjump;
import cbproject.deathmatch.items.wpns.Weapon_357;
import cbproject.deathmatch.items.wpns.Weapon_9mmAR;
import cbproject.deathmatch.items.wpns.Weapon_9mmhandgun;
import cbproject.deathmatch.items.wpns.Weapon_RPG;
import cbproject.deathmatch.items.wpns.Weapon_crossbow;
import cbproject.deathmatch.items.wpns.Weapon_crowbar;
import cbproject.deathmatch.items.wpns.Weapon_egon;
import cbproject.deathmatch.items.wpns.Weapon_gauss;
import cbproject.deathmatch.items.wpns.Weapon_hgrenade;
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
	public static Weapon_RPG weapon_RPG;
	public static Item weapon_crossbow;
	
	public static ArmorHEV armorHEVBoot, armorHEVLeggings;
	public static ArmorLongjump longjump;
	//, armorHEVChestplate, armorHEVHelmet, armorHEVLeggings;
	
	
	public static void init(Config conf){
		
		try{
			weapon_crowbar = new Weapon_crowbar(conf.GetItemID("weapon_crowbar", 7000));
			
			weapon_hgrenade = new Weapon_hgrenade(conf.GetItemID("weapon_hgrenade", 7001));
			weapon_9mmhandgun = new Weapon_9mmhandgun(conf.GetItemID("weapon_nmmhandgun", 7002));
			weapon_9mmAR = new Weapon_9mmAR(conf.GetItemID("weapon_nmmAR", 7003));
			weapon_357 = new Weapon_357(conf.GetItemID("weapon_357", 7004));
			weapon_shotgun = new Weapon_shotgun(conf.GetItemID("weapon_shotgun", 7005));
			weapon_RPG = new Weapon_RPG(conf.GetItemID("weapon_RPG", 7006));
			weapon_crossbow = new Weapon_crossbow(conf.GetItemID("weapon_crossbow", 7007));	
			weapon_satchel = new Weapon_satchel(conf.GetItemID("weapon_satchel", 7008));
			
			weapon_gauss = new Weapon_gauss(conf.GetItemID("weapon_gauss", 7050));
			weapon_egon = new Weapon_egon(conf.GetItemID("weapon_egon", 7051));
			
			//armorHEVHelmet = new ArmorHEV(conf.GetItemID("armorHEVHelmet", 8001), 0);
			//armorHEVChestplate = new ArmorHEV(conf.GetItemID("armorHEVChestplate", 8002), 1);
			armorHEVLeggings = new ArmorHEV(conf.GetItemID("armorHEVLeggings", 8003), 2);
			armorHEVBoot = new ArmorHEV(conf.GetItemID("armorHEVBoot", 8000), 3);
			longjump = new ArmorLongjump(conf.GetItemID("armorLongjump", 8001), 1);
		}catch(Exception e){
			e.printStackTrace();
		}
        
	}
	
	
	
}
