package cbproject.elements.items;

import cbproject.configure.Config;
import cbproject.elements.items.ammos.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.elements.items.weapons.*;

public class ItemsRegister {
	
	public static Weapon_crowbar weapon_crowbar;
	public static Weapon_hgrenade weapon_hgrenade;
	
	public static Weapon_gauss weapon_gauss;
	public static Weapon_egon weapon_egon;
	public static Weapon_9mmhandgun weapon_9mmhandgun;
	public static Weapon_9mmAR weapon_9mmAR;
	public static Weapon_357 weapon_357;
	public static Weapon_shotgun weapon_shotgun;
	public static Weapon_RPG weapon_RPG;
	public static Weapon_crossbow weapon_crossbow;
	
	public static ItemAmmo_uranium itemAmmo_uranium;
	public static ItemAmmo_9mm itemAmmo_9mm;
	public static ItemAmmo_357 itemAmmo_357;
	
	public ItemsRegister(Config conf){
		registerItems(conf);
		return;
	}
	
	public void registerItems(Config conf){
		//TODO:添加调用config的部分
		try{
			itemAmmo_uranium = new ItemAmmo_uranium(conf.GetItemID("itemAmmo_uranium", 7200));
			itemAmmo_9mm = new ItemAmmo_9mm(conf.GetItemID("itemAmmo_9mm", 7201));
			itemAmmo_357 = new ItemAmmo_357(conf.GetItemID("itemAmmo_357", 7202));
		
			weapon_crowbar = new Weapon_crowbar(conf.GetItemID("weapon_crowbar", 7000));
			weapon_hgrenade = new Weapon_hgrenade(conf.GetItemID("weapon_hgrenade", 7001));
			weapon_9mmhandgun = new Weapon_9mmhandgun(conf.GetItemID("weapon_9mmhandgun", 7202));
			weapon_9mmAR = new Weapon_9mmAR(conf.GetItemID("weapon_9mmAR", 7003));
			weapon_357 = new Weapon_357(conf.GetItemID("weapon_357", 7004));
			weapon_shotgun = new Weapon_shotgun(conf.GetItemID("weapon_shotgun", 7005));
			weapon_RPG = new Weapon_RPG(conf.GetItemID("weapon_RPG", 7006));
			weapon_crossbow = new Weapon_crossbow(conf.GetItemID("weapon_crowbar", 7007));
		} catch(Exception e){
			System.err.println("Error when loading itemIDs from config . " +e );
		}
		
		LanguageRegistry.addName(itemAmmo_9mm, "9mm Ammo");
		LanguageRegistry.addName(weapon_9mmAR, "9mmAR");
		LanguageRegistry.addName(itemAmmo_uranium , "Uranium Ammo");
		
        LanguageRegistry.addName(weapon_crowbar, "Crowbar");
        LanguageRegistry.addName(weapon_hgrenade, "Hand Grenade");
        LanguageRegistry.addName(weapon_9mmhandgun , "9mm Handgun");
        LanguageRegistry.addName(weapon_357, ".357 Magnum");
        LanguageRegistry.addName(weapon_shotgun, "Shotgun");
        LanguageRegistry.addName(weapon_RPG, "RPG Rocket Launcher");
        LanguageRegistry.addName(weapon_crossbow, "High Heat Crossbow");
        
        //合成表：撬棍
        ItemStack rosereddyeStack = new ItemStack(351,1,0);
        ItemStack ingotIronStack = new ItemStack(Item.ingotIron);
        ItemStack crowbarStack = new ItemStack(weapon_crowbar);
        ModLoader.addShapelessRecipe(crowbarStack, rosereddyeStack);
        GameRegistry.addShapelessRecipe(crowbarStack, rosereddyeStack,ingotIronStack);
        //End Crowbar
        
        
		return;
	}
}
