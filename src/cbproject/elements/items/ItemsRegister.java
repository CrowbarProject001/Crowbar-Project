package cbproject.elements.items;

import cbproject.configure.Config;
import cbproject.elements.items.ammos.*;
import cbproject.elements.items.armor.ArmorHEV;
import cbproject.elements.items.bullets.ItemBullet_Shotgun;
import cbproject.elements.items.craft.ItemRefinedIronIngot;
import cbproject.elements.items.weapons.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * CBC Mod Item Register class.
 * @author WeAthFolD, mkpoli
 *
 */
public class ItemsRegister {
	
	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;
	
	public static Weapon_gauss weapon_gauss;
	public static Weapon_satchel weapon_satchel;
	public static Item weapon_egon;
	public static Item weapon_9mmhandgun;
	public static Item weapon_9mmAR;
	public static Item weapon_357;
	public static Item weapon_shotgun;
	public static Item weapon_RPG;
	public static Item weapon_crossbow;
	
	public static ItemAmmo_uranium itemAmmo_uranium;
	public static ItemAmmo_9mm itemAmmo_9mm;
	public static ItemAmmo_357 itemAmmo_357;
	public static ItemAmmo_9mm2 itemAmmo_9mm2;
	public static ItemAmmo_bow itemAmmo_bow;
	public static ItemAmmo_RPG itemAmmo_rpg;
	public static ItemAmmo_ARGrenade itemAmmo_ARGrenade;
	
	public static ItemBullet_Shotgun itemBullet_Shotgun;

	public static ItemRefinedIronIngot itemRefinedIronIngot;
	
	public static ArmorHEV armorHEVBoot, armorHEVChestplate, armorHEVHelmet, armorHEVLeggings;
	public ItemsRegister(Config conf){
		registerItems(conf);
		return;
	}
	
	public void registerItems(Config conf){
	
		try{

			itemRefinedIronIngot = new ItemRefinedIronIngot(conf.GetItemID("itemRefinedIronIngot",7100));
			
			itemAmmo_uranium = new ItemAmmo_uranium(conf.GetItemID("itemAmmo_uranium", 7300));
			itemAmmo_9mm = new ItemAmmo_9mm(conf.GetItemID("itemAmmo_9mm", 7301));
			itemAmmo_9mm2 = new ItemAmmo_9mm2(conf.GetItemID("itemAmmo_9mm2", 7302));
			itemAmmo_357 = new ItemAmmo_357(conf.GetItemID("itemAmmo_357", 7303));
			itemAmmo_bow = new ItemAmmo_bow(conf.GetItemID("itemAmmo_bow", 7304));
			itemAmmo_rpg = new ItemAmmo_RPG(conf.GetItemID("itemAmmo_RPG", 7305));
			itemAmmo_ARGrenade = new ItemAmmo_ARGrenade(conf.GetItemID("itemAmmo_ARGrenade", 7306));
			
			itemBullet_Shotgun = new ItemBullet_Shotgun(conf.GetItemID("itemBullet_Shotgun", 7350));
	
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
			
			armorHEVHelmet = new ArmorHEV(conf.GetItemID("armorHEVHelmet", 8001), 0);
			armorHEVChestplate = new ArmorHEV(conf.GetItemID("armorHEVChestplate", 8002), 1);
			armorHEVLeggings = new ArmorHEV(conf.GetItemID("armorHEVLeggings", 8003), 2);
			armorHEVBoot = new ArmorHEV(conf.GetItemID("armorHEVBoot", 8000), 3);
			
		} catch(Exception e){
			System.err.println("Error when loading itemIDs from config . " + e );
		}
		
		weapon_satchel.ammoID = weapon_satchel.itemID;
		
		LanguageRegistry.addName(itemAmmo_9mm, "9mm Handgun Clip");
		LanguageRegistry.addName(itemAmmo_uranium , "Uranium Ammo");
		LanguageRegistry.addName(itemAmmo_9mm2, "9mmAR Clip");
		LanguageRegistry.addName(itemAmmo_bow,"Crossbow Clip");
		LanguageRegistry.addName(itemAmmo_357, ".357 Ammo");
		LanguageRegistry.addName(itemAmmo_ARGrenade, "9mmAR Grenade");
		
		LanguageRegistry.addName(itemBullet_Shotgun,"Shotgun Ammo");
		
		
		LanguageRegistry.addName(weapon_crowbar, "Crowbar");
		
		
		LanguageRegistry.addName(weapon_shotgun, "Shotgun");
		LanguageRegistry.addName(weapon_9mmhandgun , "9mm Handgun");
		LanguageRegistry.addName(weapon_9mmAR, "9mmAR");
        LanguageRegistry.addName(weapon_hgrenade, "Hand Grenade");
        
        LanguageRegistry.addName(weapon_357, ".357 Magnum");
        LanguageRegistry.addName(weapon_satchel, "Satchel");
        LanguageRegistry.addName(weapon_RPG, "RPG Rocket Launcher");
        LanguageRegistry.addName(weapon_crossbow, "High Heat Crossbow");
        LanguageRegistry.addName(weapon_gauss, "Gauss Gun");
        LanguageRegistry.addName(itemRefinedIronIngot, "Refined Iron Ingot");
        
		LanguageRegistry.addName(armorHEVBoot, "HEV boot");
		LanguageRegistry.addName(armorHEVHelmet, "HEV helmet");
		LanguageRegistry.addName(armorHEVChestplate, "HEV chestplate");
		LanguageRegistry.addName(armorHEVLeggings, "HEV leggings");
		
        //Recipes
        ItemStack rosereddyeStack = new ItemStack(351,1,0);
        ItemStack ingotIronStack = new ItemStack(Item.ingotIron);
        ItemStack crowbarStack = new ItemStack(weapon_crowbar);
        ModLoader.addShapelessRecipe(crowbarStack, rosereddyeStack);
        GameRegistry.addShapelessRecipe(crowbarStack, rosereddyeStack,ingotIronStack);
        //End Crowbar

        //Smeltings
        ModLoader.addSmelting(Item.ingotIron.itemID,new ItemStack(itemRefinedIronIngot.itemID,1,0) );
        
        
		return;
	}
}
