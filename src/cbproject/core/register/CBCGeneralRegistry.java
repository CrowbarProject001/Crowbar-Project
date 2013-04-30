package cbproject.core.register;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_9mmhandgun),500, new ItemStack(CBCItems.mat_pistol, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_357),500 ,new ItemStack(CBCItems.mat_pistol, 3), new ItemStack(CBCItems.mat_accessories, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_9mmAR) ,500, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_shotgun) ,500 , new ItemStack(CBCItems.mat_light, 5), new ItemStack(CBCItems.mat_accessories, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_crossbow), 500, new ItemStack(CBCItems.mat_light, 6) ,new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.ironBar, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.weapon_RPG), 500, new ItemStack(CBCItems.mat_heavy, 3), new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.mat_explosive, 2))
		};
		RecipeWeapons.addWeaponRecipe(0, wpnRecipes);
	}
	
}
