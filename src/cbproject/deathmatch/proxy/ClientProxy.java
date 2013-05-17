package cbproject.deathmatch.proxy;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.src.PlayerAPI;
import net.minecraftforge.client.MinecraftForgeClient;
import cbproject.core.props.ClientProps;
import cbproject.core.register.CBCPacketHandler;
import cbproject.core.renderers.RenderEmpty;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.entities.CBCPlayer;
import cbproject.deathmatch.entities.EntityARGrenade;
import cbproject.deathmatch.entities.EntityBullet;
import cbproject.deathmatch.entities.EntityBulletGauss;
import cbproject.deathmatch.entities.EntityBulletGaussSec;
import cbproject.deathmatch.entities.EntityCrossbowArrow;
import cbproject.deathmatch.entities.EntityHGrenade;
import cbproject.deathmatch.entities.EntityHornet;
import cbproject.deathmatch.entities.EntityRPGDot;
import cbproject.deathmatch.entities.EntityRocket;
import cbproject.deathmatch.entities.EntitySatchel;
import cbproject.deathmatch.entities.fx.EntityEgonRay;
import cbproject.deathmatch.entities.fx.EntityGaussRay;
import cbproject.deathmatch.entities.fx.EntityTrailFX;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.network.NetTripmine;
import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.renderers.RenderBulletWeapon;
import cbproject.deathmatch.renderers.RenderCrossbow;
import cbproject.deathmatch.renderers.RenderCrossedProjectile;
import cbproject.deathmatch.renderers.RenderEgon;
import cbproject.deathmatch.renderers.RenderEgonRay;
import cbproject.deathmatch.renderers.RenderGaussRay;
import cbproject.deathmatch.renderers.RenderHornet;
import cbproject.deathmatch.renderers.RenderItemSatchel;
import cbproject.deathmatch.renderers.RenderSatchel;
import cbproject.deathmatch.renderers.RenderTileTripmine;
import cbproject.deathmatch.renderers.RenderTrail;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends Proxy {
	@Override
	public void init(){
		CBCPacketHandler.addChannel("CBCExplosion", new NetTripmine());
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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTripmine.class, new RenderTileTripmine());
		
		//Texture preloading:DEPRECATED
	}
}
