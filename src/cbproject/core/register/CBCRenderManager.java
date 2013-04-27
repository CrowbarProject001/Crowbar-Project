package cbproject.core.register;

import cbproject.core.CBCMod;
import cbproject.core.renderers.RenderEmpty;
import cbproject.deathmatch.blocks.BlockTripmineRay;
import cbproject.deathmatch.blocks.tileentities.*;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;
import cbproject.deathmatch.entities.*;
import cbproject.deathmatch.renderers.*;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CBCRenderManager {

	public CBCRenderManager() {
	}
	
	public void Load(){
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RendererHGrenade(CBCItems.weapon_hgrenade));
		RenderingRegistry.registerEntityRenderingHandler(EntityGaussRay.class, new RenderGaussRay());
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class, new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class, new RenderARGrenade());
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class, new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRocket.class, new RenderRocket());
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
