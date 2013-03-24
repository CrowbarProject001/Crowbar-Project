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
import cbproject.elements.entities.weapons.EntityHGrenade;
import cbproject.elements.items.weapons.Weapon_hgrenade;
import cbproject.elements.renderers.ammos.RenderUranium;
import cbproject.elements.renderers.weapons.RenderCrossbow;
import cbproject.elements.renderers.weapons.RendererHGrenade;

public class CBCRenderManager {

	public CBCRenderManager() {
		// TODO Auto-generated constructor stub
		Load();
	}
	
	private void Load(){
		//TODO:在这里注册渲染器
		RenderingRegistry.registerEntityRenderingHandler(EntityHGrenade.class, new RendererHGrenade(33));
		RenderingRegistry.registerEntityRenderingHandler(EntityAmmoUranium.class,new RenderUranium());
		MinecraftForgeClient.registerItemRenderer(CBCMod.cbcItems.weapon_crossbow.itemID, new RenderCrossbow());
		System.out.println("Renderer registered");
	}
}
