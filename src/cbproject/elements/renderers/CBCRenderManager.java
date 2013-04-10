package cbproject.elements.renderers;

import java.util.Map;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.MinecraftForgeClient;
import cbproject.CBCMod;
import cbproject.configure.Config;
import cbproject.elements.entities.ammos.EntityAmmoUranium;
import cbproject.elements.entities.weapons.*;
import cbproject.elements.items.weapons.Weapon_hgrenade;
import cbproject.elements.renderers.ammos.RenderUranium;
import cbproject.elements.renderers.weapons.*;

public class CBCRenderManager {

	public CBCRenderManager() {
		// TODO Auto-generated constructor stub
		Load();
	}
	
	private void Load(){
		//TODO:在这里注册渲染器
		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RendererHGrenade(33));
		RenderingRegistry.registerEntityRenderingHandler(EntityAmmoUranium.class,new RenderUranium());
		RenderingRegistry.registerEntityRenderingHandler(EntityGauss.class, new RenderGaussRay());
		RenderingRegistry.registerEntityRenderingHandler(EntitySatchel.class, new RenderSatchel());
		RenderingRegistry.registerEntityRenderingHandler(EntityARGrenade.class, new RenderARGrenade());
		RenderingRegistry.registerEntityRenderingHandler(EntityEgonRay.class, new RenderEgonRay());
		RenderingRegistry.registerEntityRenderingHandler(EntityRPG.class, new RenderRPGRocket());
		
		MinecraftForgeClient.registerItemRenderer(CBCMod.cbcItems.weapon_crossbow.itemID, new RenderCrossbow());
		MinecraftForgeClient.registerItemRenderer(CBCMod.cbcItems.weapon_satchel.itemID, new RenderItemSatchel());
		MinecraftForgeClient.registerItemRenderer(CBCMod.cbcItems.weapon_egon.itemID, new RenderEgon());
		
		RenderingRegistry.registerBlockHandler(new RenderTripmine());
		RenderingRegistry.registerBlockHandler(new RenderTripmineRay());
		
		System.out.println("Renderer registered");
	}
}
