package cbproject.proxy;

import cbproject.CBCMod;
import cbproject.elements.renderers.CBCRenderManager;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy{
	
	public final static String BLOCKS_TEXTURE_PATH = "/cbproject/gfx/textures/blocks.png";
	public final static String ITEMS_TEXTURE_PATH = "/cbproject/gfx/textures/items.png";
	public final static String ITEMS_MOTION1_PATH = "/cbproject/gfx/textures/moving_a.png";
	
	public final static String GAUSS_BEAM_PATH = "/cbproject/gfx/textures/gaussbeam.png";
	
	public final static String TRIPMINE_FRONT_PATH = "/cbproject/gfx/textures/tripmine_front.png";
	public final static String TRIPMINE_SIDE_PATH = "/cbproject/gfx/textures/tripmine_side.png";
	public final static String TRIPMINE_TOP_PATH = "/cbproject/gfx/textures/tripmine_top.png";
	public final static String TRIPMINE_RAY_PATH = "/cbproject/gfx/textures/tripmine_beam.png";
	
	public final static String SATCHEL_TOP_PATH = "/cbproject/gfx/textures/satchel_top.png",
			SATCHEL_BOTTOM_PATH = "/cbproject/gfx/textures/satchel_bottom.png", 
			SATCHEL_SIDE_PATH = "/cbproject/gfx/textures/satchel_side.png",
			SATCHEL_SIDE2_PATH = "/cbproject/gfx/textures/satchel_side2.png";
	
	public final static String AR_GRENADE_PATH = "/cbproject/gfx/textures/argrenade.png",
			RPG_ROCKET_PATH = "/cbproject/gfx/textures/rpg_rocket.png",
			SHOTGUN_SHELL_PATH = "/cbproject/gfx/textures/shotgun_shell.png",
			EGON_BEAM_PATH = "/cbproject/gfx/textures/egon_beam.png";
	
	@Override 
	public void init() { 
		
		CBCMod.renderManager = new CBCRenderManager();
		
		MinecraftForgeClient.preloadTexture(BLOCKS_TEXTURE_PATH);
		MinecraftForgeClient.preloadTexture(ITEMS_TEXTURE_PATH); 
		MinecraftForgeClient.preloadTexture(ITEMS_MOTION1_PATH); 
		
		MinecraftForgeClient.preloadTexture(GAUSS_BEAM_PATH); 
		
		MinecraftForgeClient.preloadTexture(TRIPMINE_FRONT_PATH);
		MinecraftForgeClient.preloadTexture(TRIPMINE_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(TRIPMINE_TOP_PATH);
		MinecraftForgeClient.preloadTexture(TRIPMINE_RAY_PATH);
		
		MinecraftForgeClient.preloadTexture(SATCHEL_TOP_PATH);
		MinecraftForgeClient.preloadTexture(SATCHEL_BOTTOM_PATH);
		MinecraftForgeClient.preloadTexture(SATCHEL_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(SATCHEL_SIDE2_PATH);
		
		MinecraftForgeClient.preloadTexture(AR_GRENADE_PATH );
		MinecraftForgeClient.preloadTexture(RPG_ROCKET_PATH);
		MinecraftForgeClient.preloadTexture(SHOTGUN_SHELL_PATH);
		MinecraftForgeClient.preloadTexture(EGON_BEAM_PATH);
	}
}
