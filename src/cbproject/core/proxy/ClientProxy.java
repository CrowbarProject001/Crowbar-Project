package cbproject.core.proxy;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cbproject.core.CBCMod;
import cbproject.core.props.*;
import cbproject.core.register.CBCKeyProcess;
import cbproject.deathmatch.renderers.RenderEmptyBlock;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy{

	@Override 
	public void init() { 
		RenderingRegistry.registerBlockHandler(new RenderEmptyBlock());	
		KeyBindingRegistry.registerKeyBinding(new CBCKeyProcess());
	}
	
}
