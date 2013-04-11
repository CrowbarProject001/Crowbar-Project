package cbproject.elements.renderers;

import net.minecraftforge.client.MinecraftForgeClient;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.*;
import cbproject.elements.renderers.weapons.*;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CBCRenderManager {

	public CBCRenderManager() {
		Load();
	}
	
	private void Load(){
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RendererHGrenade(33));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay());
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class, new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class, new RenderARGrenade());
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class, new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket());
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGauss.class, new RenderEmpty());
		RenderingRegistry.registerEntityRenderingHandler(EntityBulletGaussSec.class, new RenderEmpty());
		
		MinecraftForgeClient.registerItemRenderer(CBCMod.cbcItems.weapon_crossbow.itemID, new RenderCrossbow());
		MinecraftForgeClient.registerItemRenderer(CBCMod.cbcItems.weapon_satchel.itemID, new RenderItemSatchel());
		MinecraftForgeClient.registerItemRenderer(CBCMod.cbcItems.weapon_egon.itemID, new RenderEgon());
		
		RenderingRegistry.registerBlockHandler(new RenderTripmine());
		RenderingRegistry.registerBlockHandler(new RenderTripmineRay());
		
		System.out.println("LambdaCraft renderers registered");
		
	}
}
