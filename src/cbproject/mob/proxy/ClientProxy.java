package cbproject.mob.proxy;

import cbproject.mob.entities.EntitySnark;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends Proxy {

	@Override
	public void init(){
		super.init();
		RenderingRegistry.registerEntityRenderingHandler(EntitySnark.class, new RenderLiving(new ModelSlime(1), 0.2F));
	}
}
