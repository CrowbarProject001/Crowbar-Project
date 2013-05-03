package cbproject.core.proxy;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cbproject.core.register.CBCKeyProcess;
import cbproject.deathmatch.renderers.RenderEmptyBlock;

public class ClientProxy extends Proxy{

	@Override 
	public void init() { 
		super.init();
		RenderingRegistry.registerBlockHandler(new RenderEmptyBlock());	
		KeyBindingRegistry.registerKeyBinding(new CBCKeyProcess());
	}
	
}
