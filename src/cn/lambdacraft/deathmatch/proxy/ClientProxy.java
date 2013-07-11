/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.proxy;

import org.lwjgl.input.Keyboard;

import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.client.render.RenderCrossedProjectile;
import cn.lambdacraft.core.client.render.RenderEmpty;
import cn.lambdacraft.core.client.render.RenderIcon;
import cn.lambdacraft.core.client.render.RenderModel;
import cn.lambdacraft.core.network.NetExplosion;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.register.CBCKeyProcess;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.deathmatch.blocks.TileArmorCharger;
import cn.lambdacraft.deathmatch.blocks.TileHealthCharger;
import cn.lambdacraft.deathmatch.blocks.TileTripmine;
import cn.lambdacraft.deathmatch.client.keys.KeyMode;
import cn.lambdacraft.deathmatch.client.keys.KeyReload;
import cn.lambdacraft.deathmatch.client.models.ModelBattery;
import cn.lambdacraft.deathmatch.client.models.ModelMedkit;
import cn.lambdacraft.deathmatch.client.render.RenderBulletWeapon;
import cn.lambdacraft.deathmatch.client.render.RenderCrossbow;
import cn.lambdacraft.deathmatch.client.render.RenderEgon;
import cn.lambdacraft.deathmatch.client.render.RenderEgonRay;
import cn.lambdacraft.deathmatch.client.render.RenderGaussRay;
import cn.lambdacraft.deathmatch.client.render.RenderGlow;
import cn.lambdacraft.deathmatch.client.render.RenderHornet;
import cn.lambdacraft.deathmatch.client.render.RenderItemElCrowbar;
import cn.lambdacraft.deathmatch.client.render.RenderSatchel;
import cn.lambdacraft.deathmatch.client.render.RenderTileCharger;
import cn.lambdacraft.deathmatch.client.render.RenderTileHeCharger;
import cn.lambdacraft.deathmatch.client.render.RenderTileTripmine;
import cn.lambdacraft.deathmatch.client.render.RenderTrail;
import cn.lambdacraft.deathmatch.entities.EntityARGrenade;
import cn.lambdacraft.deathmatch.entities.EntityBattery;
import cn.lambdacraft.deathmatch.entities.EntityBullet;
import cn.lambdacraft.deathmatch.entities.EntityBulletGauss;
import cn.lambdacraft.deathmatch.entities.EntityBulletGaussSec;
import cn.lambdacraft.deathmatch.entities.EntityBulletSG;
import cn.lambdacraft.deathmatch.entities.EntityCrossbowArrow;
import cn.lambdacraft.deathmatch.entities.EntityHGrenade;
import cn.lambdacraft.deathmatch.entities.EntityHornet;
import cn.lambdacraft.deathmatch.entities.EntityMedkit;
import cn.lambdacraft.deathmatch.entities.EntityRPGDot;
import cn.lambdacraft.deathmatch.entities.EntityRocket;
import cn.lambdacraft.deathmatch.entities.EntitySatchel;
import cn.lambdacraft.deathmatch.entities.fx.EntityCrossbowStill;
import cn.lambdacraft.deathmatch.entities.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.entities.fx.EntityGaussRay;
import cn.lambdacraft.deathmatch.entities.fx.EntityGaussRayColored;
import cn.lambdacraft.deathmatch.entities.fx.EntityTrailFX;
import cn.lambdacraft.deathmatch.entities.fx.GaussParticleFX;
import cn.lambdacraft.deathmatch.flashlight.ClientTickHandler;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.lambdacraft.mob.blocks.TileSentryRay;
import cn.lambdacraft.mob.client.render.RenderSentryRay;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * @author WeAthFolD
 * 
 */
public class ClientProxy extends Proxy {

	public static ClientTickHandler cth;

	public final static String SOUND_WEAPONS[] = {

	"hgrenadepin", "hgrenadebounce", "plgun_c", "nmmclipa", "explode_a",
			"explode_b", "g_bounceb", "gunjam_a", "hksa", "hksb", "hksc",
			"nmmarr", "pyt_shota", "pyt_shotb", "pyt_cocka", "pyt_reloada",
			"sbarrela", "sbarrela_a", "sbarrelb", "sbarrelb_a", "scocka",
			"cbar_hita", "cbar_hitb", "cbar_hitboda", "cbar_hitbodb",
			"cbar_hitbodc", "reloada", "reloadb", "reloadc", "gaussb",
			"gauss_charge", "gauss_windupa", "gauss_windupb", "gauss_windupc",
			"gauss_windupd", "rocketfirea", "xbow_fire", "xbow_reload",
			"egon_run", "egon_windup", "egon_off", "rocket", "rocketfire",
			"glauncher", "glauncherb", "ag_firea", "ag_fireb", "ag_firec"

	};

	public static final String SND_ENTITIES[] = { "medkit", "battery",
			"suitcharge", "suitchargeno", "suitchargeok", "medshot",
			"medshotno", "medcharge" };

	
	@Override
	public void preInit() {
		for (String s : SOUND_WEAPONS)
			CBCSoundEvents.addSoundPath("cbc/weapons/" + s,
					"/mods/lambdacraft/sounds/weapons/" + s);
		for (String s : SND_ENTITIES)
			CBCSoundEvents.addSoundPath("cbc/entities/" + s,
					"/mods/lambdacraft/sounds/entities/" + s);
		CBCKeyProcess.addKey(new KeyBinding("key.reload", Keyboard.KEY_R),
				false, new KeyReload());
		CBCKeyProcess.addKey(new KeyBinding("key.mode", Keyboard.KEY_V),
				false, new KeyMode());
	}
	
	@Override
	public void init() {
		CBCNetHandler.addChannel(GeneralProps.NET_ID_EXPLOSION,
				new NetExplosion());

		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RenderSnowball(DMItems.weapon_hgrenade));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay(false));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRayColored.class, new RenderGaussRay(true));
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class,new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class,new RenderCrossedProjectile(0.4, 0.1235,ClientProps.AR_GRENADE_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class,	new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class,new RenderCrossedProjectile(0.8, 0.27,ClientProps.RPG_ROCKET_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowArrow.class, new RenderCrossedProjectile(0.6,0.12, ClientProps.CROSSBOW_BOW_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowStill.class, new RenderCrossedProjectile(0.6,0.12, ClientProps.CROSSBOW_BOW_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityRPGDot.class,new RenderIcon(ClientProps.RED_DOT_PATH).setBlend(0.8F).setEnableDepth(false));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class,new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGauss.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGaussSec.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityTrailFX.class, new RenderTrail());
		RenderingRegistry.registerEntityRenderingHandler(EntityHornet.class, new RenderHornet());
		RenderingRegistry.registerEntityRenderingHandler(EntityBattery.class,new RenderModel(new ModelBattery(), ClientProps.BATTERY_PATH,0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMedkit.class,new RenderModel(new ModelMedkit(), ClientProps.MEDKIT_ENT_PATH,1.0F));
		RenderingRegistry.registerEntityRenderingHandler(GaussParticleFX.class, new RenderGlow());
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderCrossedProjectile(0.5, 0.015, 1.0F, 1.0F, 1.0F).setIgnoreLight(true));
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletSG.class, new RenderCrossedProjectile(0.5, 0.015, 1.0F, 1.0F, 1.0F).setIgnoreLight(true));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_crossbow.itemID, new RenderCrossbow());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_egon.itemID,new RenderEgon());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmhandgun.itemID,new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_9mmhandgun,0.08F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_357.itemID,new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_357, 0.08F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmAR.itemID,new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_9mmAR, 0.10F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_shotgun.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_shotgun, 0.12F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_RPG.itemID, new RenderBulletWeapon(DMItems.weapon_RPG, 0.15F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_crowbar_el.itemID, new RenderItemElCrowbar());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTripmine.class,new RenderTileTripmine(DMBlocks.blockTripmine));
		ClientRegistry.bindTileEntitySpecialRenderer(TileArmorCharger.class,new RenderTileCharger(DMBlocks.armorCharger));
		ClientRegistry.bindTileEntitySpecialRenderer(TileHealthCharger.class,new RenderTileHeCharger(DMBlocks.healthCharger));
		cth = new ClientTickHandler();
		TickRegistry.registerTickHandler(cth, Side.CLIENT);
	}
}
