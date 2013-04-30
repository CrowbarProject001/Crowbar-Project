package cbproject.core.register;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RenderEmpty;
import cbproject.deathmatch.blocks.tileentities.*;
import cbproject.deathmatch.entities.*;
import cbproject.deathmatch.renderers.*;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CBCRenderManager {

	public CBCRenderManager() {
	}
	
	public void Load(){
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RenderSnowball(CBCItems.weapon_hgrenade));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay());
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class, new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class, new RenderCrossedProjectile(0.4, 0.1235, ClientProps.AR_GRENADE_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class, new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderCrossedProjectile(0.8, 0.27, ClientProps.RPG_ROCKET_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowArrow.class, new RenderCrossedProjectile(0.8, 0.2, ClientProps.CROSSBOW_BOW_PATH));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGauss.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGaussSec.class, new RenderEmpty());
		
		MinecraftForgeClient.registerItemRenderer(CBCItems.weapon_crossbow.itemID, new RenderCrossbow());
		MinecraftForgeClient.registerItemRenderer(CBCItems.weapon_satchel.itemID, new RenderItemSatchel());
		MinecraftForgeClient.registerItemRenderer(CBCItems.weapon_egon.itemID, new RenderEgon());
		
		RenderingRegistry.registerBlockHandler(new RenderEmptyBlock());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTripmine.class, new RenderTileTripmine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTripmineRay.class, new RenderTileTripmineRay());
		
	}
}
