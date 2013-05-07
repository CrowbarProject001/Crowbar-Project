package cbproject.deathmatch.register;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.PlayerAPI;
import net.minecraftforge.common.MinecraftForge;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCItems;
import cbproject.core.register.CBCKeyProcess;
import cbproject.core.register.CBCPacketHandler;
import cbproject.core.register.CBCSoundEvents;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.gui.ElementCrafter;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeaponSpecial;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.deathmatch.entities.CBCPlayer;
import cbproject.deathmatch.events.CBCLivingAttackEvent;
import cbproject.deathmatch.keys.DMMode;
import cbproject.deathmatch.keys.DMReload;
import cbproject.deathmatch.network.NetDeathmatch;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class DMRegister {

	public final static String SOUND_WEAPONS[]={
		
		"hgrenadepin",
		"hgrenadebounce",
		"plgun_c",
		"nmmclipa",
		"explode_a",
		"explode_b",
		"g_bounceb",
		"gunjam_a",
		"hksa",
		"hksb",
		"hksc",
		"nmmarr",
		"pyt_shota",
		"pyt_shotb",
		"pyt_cocka",
		"pyt_reloada",
		"sbarrela",
		"sbarrela_a",
		"sbarrelb",
		"sbarrelb_a",
		"scocka",
		"cbar_hita",
		"cbar_hitb",
		"cbar_hitboda",
		"cbar_hitbodb",
		"cbar_hitbodc",
		"reloada",
		"reloadb",
		"reloadc",
		"gaussb",
		"gauss_charge",
		"gauss_windupa",
		"gauss_windupb",
		"gauss_windupc",
		"gauss_windupd",
		"rocketfirea",
		"xbow_fire",
		"xbow_reload",
		"egon_run",
		"egon_windup",
		"egon_off",
		"rocket",
		"rocketfire",
		"glauncher",
		"glauncherb"
		
	};
	
	public static void preRegister(){
		MinecraftForge.EVENT_BUS.register(new CBCLivingAttackEvent());
		PlayerAPI.register("CBCPlayer", CBCPlayer.class);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			for(String s : SOUND_WEAPONS)
				CBCSoundEvents.addSoundPath("cbc/weapons/" + s, "/cbproject/gfx/sounds/weapons/" + s);
			CBCKeyProcess.addKey(new KeyBinding("key.reload", Keyboard.KEY_R), false, new DMReload());
			CBCKeyProcess.addKey(new KeyBinding("key.mode", Keyboard.KEY_V), false, new DMMode());
		}
	}
	
	public static void register(){
		CBCPacketHandler.addChannel("CBCWeapons", new NetDeathmatch());
		CBCGuiHandler.addGuiElement(TileEntityWeaponCrafter.class, new ElementCrafter());
		
		String description[] = {"crafter.weapon", "crafter.ammo"};
		RecipeWeapons.InitializeRecipes(2, description);
		
		RecipeWeaponEntry wpnRecipes[] = {
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_crowbar),800, new ItemStack(CBCItems.ironBar, 2), new ItemStack(CBCItems.mat_accessories, 1), new ItemStack(Item.dyePowder, 1, 1)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_9mmhandgun),1000, new ItemStack(CBCItems.mat_pistol, 2)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_357),1000 ,new ItemStack(CBCItems.mat_pistol, 3), new ItemStack(CBCItems.mat_accessories, 2)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_9mmAR) ,1700, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_shotgun) ,1700 , new ItemStack(CBCItems.mat_light, 5), new ItemStack(CBCItems.mat_accessories, 3)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_crossbow), 1800, new ItemStack(CBCItems.mat_light, 6) ,new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.ironBar, 2)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_hgrenade, 10), 1600, new ItemStack(CBCItems.mat_light, 2), new ItemStack(CBCItems.mat_explosive, 4)),
				new RecipeWeaponEntry(new ItemStack(DMBlocks.blockTripmine, 15), 2000, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_tech, 1), new ItemStack(CBCItems.mat_explosive, 6)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_satchel, 15), 2000, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_tech, 1), new ItemStack(CBCItems.mat_explosive, 6)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_gauss), 2300, new ItemStack(CBCItems.mat_light, 8), new ItemStack(CBCItems.mat_tech, 3), new ItemStack(Block.glass, 5)),
				new RecipeWeaponEntry(new ItemStack(DMItems.weapon_egon), 2300, new ItemStack(CBCItems.mat_heavy, 5), new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.mat_tech, 4)),
				new RecipeWeaponEntry(new ItemStack(DMItems.armorHEVBoot), 3000, new ItemStack(CBCItems.mat_tech, 3), new ItemStack(CBCItems.mat_light, 6), new ItemStack(CBCItems.mat_accessories, 3))
		};
		
		RecipeWeaponEntry ammoRecipes[] = {
				new RecipeWeaponEntry(new ItemStack(CBCItems.bullet_9mm, 18), 600, new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_357, 12), 650, new ItemStack(CBCItems.mat_accessories, 2), new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_shotgun, 8), 850, new ItemStack(CBCItems.mat_ammunition, 4), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_9mm2, 1), 600, new ItemStack(CBCItems.mat_ammunition, 5), new ItemStack(CBCItems.mat_light, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.bullet_steelbow, 10), 650, new ItemStack(CBCItems.ironBar, 10), new ItemStack(CBCItems.mat_explosive, 1)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_bow, 1), 950, new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_argrenade, 5), 600, new ItemStack(CBCItems.mat_light, 1), new ItemStack(CBCItems.mat_explosive, 2)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_rpg, 6), 1500, new ItemStack(CBCItems.mat_heavy, 1), new ItemStack(CBCItems.mat_explosive, 3)),
				new RecipeWeaponEntry(new ItemStack(CBCItems.ammo_uranium, 1), 1500, new ItemStack(CBCItems.mat_box, 1), new ItemStack(CBCItems.ingotUranium, 3)),
				new RecipeWeaponSpecial(CBCItems.ammo_9mm, CBCItems.bullet_9mm),
				new RecipeWeaponSpecial(CBCItems.ammo_9mm2, CBCItems.bullet_9mm),
				new RecipeWeaponSpecial(CBCItems.ammo_bow, CBCItems.bullet_steelbow)
		};
		
		RecipeWeapons.addWeaponRecipe(0, wpnRecipes);
		RecipeWeapons.addWeaponRecipe(1, ammoRecipes);	
	}
}
