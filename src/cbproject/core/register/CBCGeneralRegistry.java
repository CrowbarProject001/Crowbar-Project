package cbproject.core.register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import cbproject.core.misc.CBCLanguage;
import cbproject.crafting.items.ItemBullet;
import cbproject.crafting.network.NetWeaponCrafter;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.deathmatch.items.ammos.ItemAmmo;
import cbproject.deathmatch.language.WeaponModes;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.crafting.recipes.*;

public class CBCGeneralRegistry {
	
	public static void register(){
		addRecipes();
		registerNetPackets();
		addLanguages();
	}
	
	private static void registerNetPackets(){
		CBCPacketHandler.addChannel("CBCCrafter", new NetWeaponCrafter());
		CBCPacketHandler.addChannel("CBCWeapons", new NetDeathmatch());
	}
	
	@SideOnly(Side.CLIENT)
	private static void addLanguages(){
		CBCItems.addItemNames();
		CBCBlocks.addNameForBlocks();
		WeaponModes.registerModeDescriptions();
		
		CBCLanguage.addDefaultName("crafter.weapon", "Weapon Forging");
		CBCLanguage.addDefaultName("crafter.ammo", "Ammo Forging");
		CBCLanguage.addDefaultName("crafter.storage", "Material Storage");
		
		CBCLanguage.addLocalName("crafter.weapon", "武器制造");
		CBCLanguage.addLocalName("crafter.ammo", "弹药制造");
		CBCLanguage.addLocalName("crafter.storage", "材料存放");
	}
	
	private static void addRecipes(){
		String description[] = {"crafter.weapon", "crafter.ammo"};
		RecipeWeapons.InitializeRecipes(2, description);
		
		RecipeWeaponEntry wpnRecipes[] = {
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_crowbar),800, new ItemStack(CBCItems.ironBar, 2), new ItemStack(CBCItems.mat_accessories, 1), new ItemStack(Item.dyePowder, 1, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_9mmhandgun),1000, new ItemStack(CBCItems.mat_pistol, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_357),1000 ,new ItemStack(CBCItems.mat_pistol, 3), new ItemStack(CBCItems.mat_accessories, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_9mmAR) ,1700, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_shotgun) ,1700 , new ItemStack(CBCItems.mat_light, 5), new ItemStack(CBCItems.mat_accessories, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_crossbow), 1800, new ItemStack(CBCItems.mat_light, 6) ,new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.ironBar, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_hgrenade, 10), 1600, new ItemStack(CBCItems.mat_light, 2), new ItemStack(CBCItems.mat_explosive, 4)),
				new RecipeWeaponEntry(new ItemStack(CBCBlocks.blockTripmine, 15), 2000, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_tech, 1), new ItemStack(CBCItems.mat_explosive, 6)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_satchel, 15), 2000, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_tech, 1), new ItemStack(CBCItems.mat_explosive, 6)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_gauss), 2300, new ItemStack(CBCItems.mat_light, 8), new ItemStack(CBCItems.mat_tech, 3), new ItemStack(Block.glass, 5)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_egon), 2300, new ItemStack(CBCItems.mat_heavy, 5), new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.mat_tech, 4)),
		};
		//new ItemStack()
		RecipeWeaponEntry ammoRecipes[] = {
				new RecipeWeaponEntry(new ItemStack(CBCItems.bullet_9mm, 18), 600, new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_357, 12), 650, new ItemStack(CBCItems.mat_accessories, 2), new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_shotgun, 8), 850, new ItemStack(CBCItems.mat_ammunition, 4), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_9mm2, 1), 600, new ItemStack(CBCItems.mat_ammunition, 5), new ItemStack(CBCItems.mat_light, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.bullet_steelbow, 10), 650, new ItemStack(CBCItems.ironBar, 10), new ItemStack(CBCItems.mat_explosive, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_bow, 1), 950, new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_argrenade, 5), 600, new ItemStack(CBCItems.mat_light, 1), new ItemStack(CBCItems.mat_explosive, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_rpg, 6), 1500, new ItemStack(CBCItems.mat_heavy, 1), new ItemStack(CBCItems.mat_explosive, 2)),
				new RecipeWeaponSpecial(CBCItems.ammo_9mm, CBCItems.bullet_9mm),
				new RecipeWeaponSpecial(CBCItems.ammo_9mm2, CBCItems.bullet_9mm),
				new RecipeWeaponSpecial(CBCItems.ammo_bow, CBCItems.bullet_steelbow)
		};
		RecipeWeapons.addWeaponRecipe(0, wpnRecipes);
		RecipeWeapons.addWeaponRecipe(1, ammoRecipes);
	}
	
}
