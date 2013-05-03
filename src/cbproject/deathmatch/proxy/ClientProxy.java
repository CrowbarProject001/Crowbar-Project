package cbproject.deathmatch.proxy;

import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.MinecraftForgeClient;
import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RenderEmpty;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmineRay;
import cbproject.deathmatch.entities.EntityARGrenade;
import cbproject.deathmatch.entities.EntityBullet;
import cbproject.deathmatch.entities.EntityBulletGauss;
import cbproject.deathmatch.entities.EntityBulletGaussSec;
import cbproject.deathmatch.entities.EntityCrossbowArrow;
import cbproject.deathmatch.entities.EntityEgonRay;
import cbproject.deathmatch.entities.EntityGaussRay;
import cbproject.deathmatch.entities.EntityHGrenade;
import cbproject.deathmatch.entities.EntityRocket;
import cbproject.deathmatch.entities.EntitySatchel;
import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.renderers.RenderCrossbow;
import cbproject.deathmatch.renderers.RenderCrossedProjectile;
import cbproject.deathmatch.renderers.RenderEgon;
import cbproject.deathmatch.renderers.RenderEgonRay;
import cbproject.deathmatch.renderers.RenderGaussRay;
import cbproject.deathmatch.renderers.RenderItemSatchel;
import cbproject.deathmatch.renderers.RenderSatchel;
import cbproject.deathmatch.renderers.RenderTileTripmine;
import cbproject.deathmatch.renderers.RenderTileTripmineRay;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends Proxy {
	@Override
	public void init(){
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RenderSnowball(DMItems.weapon_hgrenade));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay());
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class, new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class, new RenderCrossedProjectile(0.4, 0.1235, ClientProps.AR_GRENADE_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class, new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderCrossedProjectile(0.8, 0.27, ClientProps.RPG_ROCKET_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowArrow.class, new RenderCrossedProjectile(0.6, 0.12, ClientProps.CROSSBOW_BOW_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGauss.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGaussSec.class, new RenderEmpty());
		
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_crossbow.itemID, new RenderCrossbow());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_satchel.itemID, new RenderItemSatchel());
		MinecraftForgeClient.registerItemRenderer(DMItems.weapon_egon.itemID, new RenderEgon());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTripmine.class, new RenderTileTripmine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTripmineRay.class, new RenderTileTripmineRay());
		
		//Texture preloading
		MinecraftForgeClient.preloadTexture(ClientProps.GAUSS_BEAM_PATH); 
		
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_FRONT_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_TOP_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_RAY_PATH);
		
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_TOP_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_BOTTOM_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_SIDE2_PATH);
		
		MinecraftForgeClient.preloadTexture(ClientProps.AR_GRENADE_PATH );
		MinecraftForgeClient.preloadTexture(ClientProps.RPG_ROCKET_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SHOTGUN_SHELL_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.EGON_BEAM_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.CROSSBOW_BOW_PATH);
		
		MinecraftForgeClient.preloadTexture(ClientProps.EGON_EQUIPPED_PATH);
		
		for(String s:ClientProps.CROSSBOW_FRONT_PATH)
			MinecraftForgeClient.preloadTexture(s);
		for(String s:ClientProps.CROSSBOW_SIDE_PATH)
			MinecraftForgeClient.preloadTexture(s);
		for(String s:ClientProps.HEV_ARMOR_PATH)
			MinecraftForgeClient.preloadTexture(s);
		
	}
}
