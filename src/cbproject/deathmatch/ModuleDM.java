package cbproject.deathmatch;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.PlayerAPI;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.input.Keyboard;

import cbproject.core.CBCMod;
import cbproject.core.module.CBCSubModule;
import cbproject.core.module.ModuleInit;
import cbproject.core.module.ModuleInit.EnumInitType;
import cbproject.core.network.NetExplosion;
import cbproject.core.props.ClientProps;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCKeyProcess;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.CBCSoundEvents;
import cbproject.core.renderers.RenderCrossedProjectile;
import cbproject.core.renderers.RenderEmpty;
import cbproject.crafting.recipes.RecipeCrafter;
import cbproject.crafting.recipes.RecipeWeaponSpecial;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.crafting.register.CBCItems;
import cbproject.deathmatch.blocks.tileentities.TileEntityArmorCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.entities.CBCPlayer;
import cbproject.deathmatch.entities.EntityARGrenade;
import cbproject.deathmatch.entities.EntityBattery;
import cbproject.deathmatch.entities.EntityBullet;
import cbproject.deathmatch.entities.EntityBulletGauss;
import cbproject.deathmatch.entities.EntityBulletGaussSec;
import cbproject.deathmatch.entities.EntityCrossbowArrow;
import cbproject.deathmatch.entities.EntityHGrenade;
import cbproject.deathmatch.entities.EntityHornet;
import cbproject.deathmatch.entities.EntityMedkit;
import cbproject.deathmatch.entities.EntityRPGDot;
import cbproject.deathmatch.entities.EntityRocket;
import cbproject.deathmatch.entities.EntitySatchel;
import cbproject.deathmatch.entities.fx.EntityEgonRay;
import cbproject.deathmatch.entities.fx.EntityGaussRay;
import cbproject.deathmatch.entities.fx.EntityTrailFX;
import cbproject.deathmatch.gui.ElementArmorCharger;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.keys.KeyMode;
import cbproject.deathmatch.keys.KeyReload;
import cbproject.deathmatch.network.NetChargerClient;
import cbproject.deathmatch.network.NetChargerServer;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.register.DMBlocks;
import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.renderers.RenderBulletWeapon;
import cbproject.deathmatch.renderers.RenderCrossbow;
import cbproject.deathmatch.renderers.RenderEgon;
import cbproject.deathmatch.renderers.RenderEgonRay;
import cbproject.deathmatch.renderers.RenderGaussRay;
import cbproject.deathmatch.renderers.RenderHornet;
import cbproject.deathmatch.renderers.RenderItemSatchel;
import cbproject.deathmatch.renderers.RenderSatchel;
import cbproject.deathmatch.renderers.RenderTileCharger;
import cbproject.deathmatch.renderers.RenderTileTripmine;
import cbproject.deathmatch.renderers.RenderTrail;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;

@CBCSubModule("deathmatch")
public class ModuleDM
{
	
	@Instance("cbc.deathmatch")
	public static ModuleDM instance = new ModuleDM();
	
	@ModuleInit(EnumInitType.PREINIT)
	public void preInit(FMLPreInitializationEvent Init)
	{
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			for(String s : SOUND_WEAPONS)
				CBCSoundEvents.addSoundPath("cbc/weapons/" + s, "/cbproject/gfx/sounds/weapons/" + s);
			CBCKeyProcess.addKey(new KeyBinding("key.reload", Keyboard.KEY_R), false, new KeyReload());
			CBCKeyProcess.addKey(new KeyBinding("key.mode", Keyboard.KEY_V), false, new KeyMode());
		}
		
		CBCNetHandler.addChannel(GeneralProps.NET_ID_DM, new NetDeathmatch());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_CHARGER_CL, new NetChargerClient());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_CHARGER_SV, new NetChargerServer());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_CHARGER, new ElementArmorCharger());
	
	}

	
	@ModuleInit(EnumInitType.INIT)
	public void init(FMLInitializationEvent Init){
		DMItems.init(CBCMod.config);
		DMBlocks.init(CBCMod.config);
		
		EntityRegistry.registerModEntity(EntityGaussRay.class, "gauss", GeneralProps.ENT_ID_GAUSS1, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityEgonRay.class, "egonray", GeneralProps.ENT_ID_EGON_RAY, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityARGrenade.class, "argrenade", GeneralProps.ENT_ID_ARGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHGrenade.class, "hgrenade", GeneralProps.ENT_ID_HGRENADE, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityHornet.class, "hornet", GeneralProps.ENT_ID_HORNET, CBCMod.instance, 32, 3, true);
		EntityRegistry.registerModEntity(EntityRocket.class, "rocket", GeneralProps.ENT_ID_ROCKET, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityRPGDot.class, "dot", GeneralProps.ENT_ID_DOT, CBCMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntitySatchel.class, "satchel", GeneralProps.ENT_ID_SATCHEL, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityCrossbowArrow.class, "arrow", GeneralProps.ENT_ID_ARROW, CBCMod.instance, 32, 2, true);
		EntityRegistry.registerModEntity(EntityMedkit.class, "medkit", GeneralProps.ENT_ID_MEDKIT, CBCMod.instance, 32, 5, true);
		EntityRegistry.registerModEntity(EntityBattery.class, "battery", GeneralProps.ENT_ID_BATTERY, CBCMod.instance, 32, 5, true);
		
		String description[] = {"crafter.weapon", "crafter.ammo"},
				advds [] = {"crafter.advweapon", "crafter.armor"};
		RecipeWeapons.InitializeRecipes(2, description);
		RecipeWeapons.InitializeAdvRecipes(2, advds);
		
		RecipeCrafter wpnRecipes[] = {
				new RecipeCrafter(new ItemStack(DMItems.weapon_crowbar),800, new ItemStack(CBCItems.ironBar, 2), new ItemStack(CBCItems.mat_accessories, 1), new ItemStack(Item.dyePowder, 1, 1)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_9mmhandgun),1000, new ItemStack(CBCItems.mat_pistol, 2)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_357),1000 ,new ItemStack(CBCItems.mat_pistol, 3), new ItemStack(CBCItems.mat_accessories, 2)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_9mmAR) ,1700, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_shotgun) ,1700 , new ItemStack(CBCItems.mat_light, 5), new ItemStack(CBCItems.mat_accessories, 3)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_crossbow), 1800, new ItemStack(CBCItems.mat_light, 6) ,new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.ironBar, 2)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_hgrenade, 10), 1600, new ItemStack(CBCItems.mat_light, 2), new ItemStack(CBCItems.mat_explosive, 4)),
				
		};
		
		RecipeCrafter ammoRecipes[] = {
				new RecipeCrafter(new ItemStack(CBCItems.bullet_9mm, 18), 600, new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_357, 12), 650, new ItemStack(CBCItems.mat_accessories, 2), new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_shotgun, 8), 850, new ItemStack(CBCItems.mat_ammunition, 4), new ItemStack(CBCItems.mat_accessories, 1)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_9mm2, 1), 600, new ItemStack(CBCItems.mat_ammunition, 5), new ItemStack(CBCItems.mat_light, 1)),
				new RecipeCrafter(new ItemStack(CBCItems.bullet_steelbow, 10), 650, new ItemStack(CBCItems.ironBar, 10), new ItemStack(CBCItems.mat_explosive, 1)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_bow, 1), 950, new ItemStack(CBCItems.mat_ammunition, 3)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_argrenade, 5), 600, new ItemStack(CBCItems.mat_light, 1), new ItemStack(CBCItems.mat_explosive, 2)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_rpg, 6), 1500, new ItemStack(CBCItems.mat_heavy, 1), new ItemStack(CBCItems.mat_explosive, 3)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_uranium, 1), 1500, new ItemStack(CBCItems.mat_box, 1), new ItemStack(CBCItems.ingotUranium, 3)),
				new RecipeWeaponSpecial(CBCItems.ammo_9mm, CBCItems.bullet_9mm),
				new RecipeWeaponSpecial(CBCItems.ammo_9mm2, CBCItems.bullet_9mm),
				new RecipeWeaponSpecial(CBCItems.ammo_bow, CBCItems.bullet_steelbow)
		};
		
		RecipeCrafter advWeapons[] = {
				new RecipeCrafter(new ItemStack(DMBlocks.blockTripmine, 15), 2000, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_tech, 1), new ItemStack(CBCItems.mat_explosive, 6)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_satchel, 15), 2000, new ItemStack(CBCItems.mat_light, 3), new ItemStack(CBCItems.mat_tech, 1), new ItemStack(CBCItems.mat_explosive, 6)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_gauss), 2300, new ItemStack(CBCItems.mat_light, 8), new ItemStack(CBCItems.mat_tech, 3), new ItemStack(Block.glass, 5)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_egon), 2300, new ItemStack(CBCItems.mat_heavy, 5), new ItemStack(CBCItems.mat_accessories, 3), new ItemStack(CBCItems.mat_tech, 4))
		},
		armors[] = {
				new RecipeCrafter(new ItemStack(DMItems.armorHEVBoot), 3000, new ItemStack(CBCItems.mat_tech, 3), new ItemStack(CBCItems.mat_light, 6), new ItemStack(CBCItems.mat_accessories, 3)),
				new RecipeCrafter(new ItemStack(DMItems.armorHEVLeggings), 3000, new ItemStack(CBCItems.mat_tech, 3), new ItemStack(CBCItems.mat_light, 6), new ItemStack(CBCItems.mat_accessories, 3)),
				new RecipeCrafter(new ItemStack(DMItems.armorHEVChestplate), 3000, new ItemStack(CBCItems.mat_tech, 4), new ItemStack(CBCItems.mat_light, 7), new ItemStack(CBCItems.mat_accessories, 4)),
				new RecipeCrafter(new ItemStack(DMItems.armorHEVHelmet), 3000, new ItemStack(CBCItems.mat_tech, 2), new ItemStack(CBCItems.mat_light, 4), new ItemStack(CBCItems.mat_accessories, 2))
		};
		
		RecipeWeapons.addWeaponRecipe(0, wpnRecipes);
		RecipeWeapons.addWeaponRecipe(1, ammoRecipes);	
		RecipeWeapons.addAdvWeaponRecipe(0, advWeapons);
		RecipeWeapons.addAdvWeaponRecipe(1, armors);
		
	}

	@ModuleInit(EnumInitType.POSTINIT)
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ModuleInit(EnumInitType.SVINIT)
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
	@ModuleInit(EnumInitType.CLINIT)
	public void loadRenderingThings(){
		CBCNetHandler.addChannel(GeneralProps.NET_ID_EXPLOSION, new NetExplosion());
		PlayerAPI.register("CBCPlayer", CBCPlayer.class);

		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RenderSnowball(DMItems.weapon_hgrenade));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay());
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class, new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class, new RenderCrossedProjectile(0.4, 0.1235, ClientProps.AR_GRENADE_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class, new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderCrossedProjectile(0.8, 0.27, ClientProps.RPG_ROCKET_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowArrow.class, new RenderCrossedProjectile(0.6, 0.12, ClientProps.CROSSBOW_BOW_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityRPGDot.class, new RenderSnowball(DMItems.weapon_RPG));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGauss.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGaussSec.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityTrailFX.class, new RenderTrail());
		RenderingRegistry.registerEntityRenderingHandler(EntityHornet.class, new RenderHornet());
		
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_crossbow.itemID, new RenderCrossbow());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_satchel.itemID, new RenderItemSatchel());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_egon.itemID, new RenderEgon());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmhandgun.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_9mmhandgun, 0.08F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_357.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_357, 0.08F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmAR.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_9mmAR, 0.10F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_shotgun.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_shotgun, 0.12F));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTripmine.class, new RenderTileTripmine(DMBlocks.blockTripmine));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArmorCharger.class, new RenderTileCharger(DMBlocks.armorCharger));
	}
	
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
		"glauncherb",
		"ag_firea",
		"ag_fireb",
		"ag_firec"
		
	};
	
}
