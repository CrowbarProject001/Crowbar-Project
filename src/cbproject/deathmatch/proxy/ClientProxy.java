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
package cbproject.deathmatch.proxy;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.MinecraftForgeClient;
import cbproject.core.network.NetExplosion;
import cbproject.core.props.ClientProps;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.renderers.RenderCrossedProjectile;
import cbproject.core.renderers.RenderEmpty;
import cbproject.core.renderers.RenderIcon;
import cbproject.core.renderers.RenderModel;
import cbproject.deathmatch.blocks.TileArmorCharger;
import cbproject.deathmatch.blocks.TileHealthCharger;
import cbproject.deathmatch.blocks.TileTripmine;
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
import cbproject.deathmatch.entities.fx.EntityGaussRayColored;
import cbproject.deathmatch.entities.fx.EntityTrailFX;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.register.DMBlocks;
import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.renderers.RenderBulletWeapon;
import cbproject.deathmatch.renderers.RenderCrossbow;
import cbproject.deathmatch.renderers.RenderEgon;
import cbproject.deathmatch.renderers.RenderEgonRay;
import cbproject.deathmatch.renderers.RenderGaussRay;
import cbproject.deathmatch.renderers.RenderHornet;
import cbproject.deathmatch.renderers.RenderSatchel;
import cbproject.deathmatch.renderers.RenderTileCharger;
import cbproject.deathmatch.renderers.RenderTileHeCharger;
import cbproject.deathmatch.renderers.RenderTileTripmine;
import cbproject.deathmatch.renderers.RenderTrail;
import cbproject.deathmatch.renderers.models.ModelBattery;
import cbproject.deathmatch.renderers.models.ModelMedkit;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 *
 */
public class ClientProxy extends Proxy {

	@Override
	public void init(){ 
		CBCNetHandler.addChannel(GeneralProps.NET_ID_EXPLOSION, new NetExplosion());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RenderSnowball(DMItems.weapon_hgrenade));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay(false));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRayColored.class, new RenderGaussRay(true));
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class, new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class, new RenderCrossedProjectile(0.4, 0.1235, ClientProps.AR_GRENADE_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class, new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderCrossedProjectile(0.8, 0.27, ClientProps.RPG_ROCKET_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowArrow.class, new RenderCrossedProjectile(0.6, 0.12, ClientProps.CROSSBOW_BOW_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityRPGDot.class, new RenderIcon(ClientProps.RED_DOT_PATH).setBlend(0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGauss.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGaussSec.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityTrailFX.class, new RenderTrail());
		RenderingRegistry.registerEntityRenderingHandler(EntityHornet.class, new RenderHornet());
		RenderingRegistry.registerEntityRenderingHandler(EntityBattery.class, new RenderModel(new ModelBattery(), ClientProps.BATTERY_PATH, 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMedkit.class, new RenderModel(new ModelMedkit(), ClientProps.MEDKIT_ENT_PATH, 1.0F));
		
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_crossbow.itemID, new RenderCrossbow());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_egon.itemID, new RenderEgon());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmhandgun.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_9mmhandgun, 0.08F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_357.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_357, 0.08F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_9mmAR.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_9mmAR, 0.10F));
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_shotgun.itemID, new RenderBulletWeapon((WeaponGeneralBullet) DMItems.weapon_shotgun, 0.12F));
		ClientRegistry.bindTileEntitySpecialRenderer(TileTripmine.class, new RenderTileTripmine(DMBlocks.blockTripmine));
		ClientRegistry.bindTileEntitySpecialRenderer(TileArmorCharger.class, new RenderTileCharger(DMBlocks.armorCharger));
		ClientRegistry.bindTileEntitySpecialRenderer(TileHealthCharger.class, new RenderTileHeCharger(DMBlocks.healthCharger));
		
	}
}
