package cbproject.proxy;

import cbproject.CBCMod;
import cbproject.elements.renderers.CBCRenderManager;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy{
	
	public final static String BLOCKS_TEXTURE_PATH = "/cbproject/gfx/textures/blocks.png";
	public final static String ITEMS_TEXTURE_PATH = "/cbproject/gfx/textures/items.png";
	@Override 
	public void init() { 
		
		CBCMod.renderManager = new CBCRenderManager();
		MinecraftForgeClient.preloadTexture(BLOCKS_TEXTURE_PATH);
		MinecraftForgeClient.preloadTexture(ITEMS_TEXTURE_PATH); 
		
	}
}
