package cbproject.core.register;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cbproject.core.misc.CBCLanguage;
import cbproject.crafting.network.NetWeaponCrafter;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.deathmatch.language.WeaponModes;
import cbproject.deathmatch.network.NetDeathmatch;

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
				new RecipeWeaponEntry(new ItemStack(CBCItems.itemBullet_9mm, 18), 600, new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.itemAmmo_357, 12), 650, new ItemStack(CBCItems.mat_accessories, 2), new ItemStack(CBCItems.mat_ammunition, 3))
		};
		RecipeWeapons.addWeaponRecipe(0, wpnRecipes);
		RecipeWeapons.addWeaponRecipe(1, ammoRecipes);
	}
	
}
