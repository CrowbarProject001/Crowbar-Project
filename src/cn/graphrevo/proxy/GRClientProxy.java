/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.proxy;

import cn.graphrevo.client.render.RenderFrozen;
import cn.graphrevo.client.render.RenderTripmine;
import cn.graphrevo.entity.EntityFrozen;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * 客户端的加载代理。
 */
public class GRClientProxy extends GRCommonProxy {

	public static int RENDER_ID_TRIPMINE = 233;
	
	public void preInit() {
		super.preInit();
	}
	
	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntityFrozen.class, new RenderFrozen());
		RenderingRegistry.registerBlockHandler(new RenderTripmine());
		super.init();
	}
	
	public void postInit() {
		super.postInit();
	}

}
