package cbproject.elements.items;

import cbproject.configure.Config;
import cbproject.elements.items.ammos.*;
import cbproject.elements.items.bullets.ItemBullet_Shotgun;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.elements.items.misc.ItemRefinedIronIngot;
import cbproject.elements.items.weapons.*;

public class ItemsRegister {
	
	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;
	
	public static Item weapon_gauss;
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
	
	public static ItemBullet_Shotgun itemBullet_Shotgun;

	public static ItemRefinedIronIngot itemRefinedIronIngot;
	
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
			
			itemBullet_Shotgun = new ItemBullet_Shotgun(conf.GetItemID("itemBullet_Shotgun", 7350));
	
			weapon_crowbar = new Weapon_crowbar(conf.GetItemID("weapon_crowbar", 7000));
			
			weapon_hgrenade = new Weapon_hgrenade(conf.GetItemID("weapon_hgrenade", 7001));
			weapon_9mmhandgun = new Weapon_9mmhandgun(conf.GetItemID("weapon_nmmhandgun", 7002));
			weapon_9mmAR = new Weapon_9mmAR(conf.GetItemID("weapon_nmmAR", 7003));
			weapon_357 = new Weapon_357(conf.GetItemID("weapon_357", 7004));
			weapon_shotgun = new Weapon_shotgun(conf.GetItemID("weapon_shotgun", 7005));
			weapon_RPG = new Weapon_RPG(conf.GetItemID("weapon_RPG", 7006));
			weapon_crossbow = new Weapon_crossbow(conf.GetItemID("weapon_crossbow", 7007));	
			
			weapon_gauss = new Weapon_gauss(conf.GetItemID("weapon_gauss", 7050));
			
			
		} catch(Exception e){
			System.err.println("Error when loading itemIDs from config . " + e );
		}
		

		
		LanguageRegistry.addName(itemAmmo_9mm, "9mm Handgun Clip");
		LanguageRegistry.addName(itemAmmo_uranium , "Uranium Ammo");
		LanguageRegistry.addName(itemAmmo_9mm2, "9mmAR Clip");
		LanguageRegistry.addName(itemAmmo_bow,"Crossbow Clip");

		LanguageRegistry.addName(itemBullet_Shotgun,"Shotgun Ammo");
		
		
		LanguageRegistry.addName(weapon_crowbar, "Crowbar");
		
		
		LanguageRegistry.addName(weapon_shotgun, "Shotgun");
		LanguageRegistry.addName(weapon_9mmhandgun , "9mm Handgun");
		LanguageRegistry.addName(weapon_9mmAR, "9mmAR");
        LanguageRegistry.addName(weapon_hgrenade, "Hand Grenade");
        
        LanguageRegistry.addName(weapon_357, ".357 Magnum");
       
        LanguageRegistry.addName(weapon_RPG, "RPG Rocket Launcher");
        LanguageRegistry.addName(weapon_crossbow, "High Heat Crossbow");
        LanguageRegistry.addName(weapon_gauss, "Gauss Gun");
        LanguageRegistry.addName(itemRefinedIronIngot, "Refined Iron Ingot");
        
		
        //鍚堟垚琛細鎾
        ItemStack rosereddyeStack = new ItemStack(351,1,0);
        ItemStack ingotIronStack = new ItemStack(Item.ingotIron);
        ItemStack crowbarStack = new ItemStack(weapon_crowbar);
        ModLoader.addShapelessRecipe(crowbarStack, rosereddyeStack);
        GameRegistry.addShapelessRecipe(crowbarStack, rosereddyeStack,ingotIronStack);
        //End Crowbar

        ModLoader.addSmelting(Item.ingotIron.itemID,new ItemStack(itemRefinedIronIngot.itemID,1,0) );
        
        
		return;
	}
}
