package cbproject.Proxy;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy{
	
	public final static String BLOCKS_TEXTURE_PATH = "/cbproject/Textures/Blocks.png"; //方块贴图路径
	public final static String ITEMS_TEXTURE_PATH = "/cbproject/Textures/Items.png"; //物品贴图路径
	@Override 
	public void init() { //覆盖被继承的 Proxy类中的init()方法
		MinecraftForgeClient.preloadTexture(BLOCKS_TEXTURE_PATH); //利用Forge自带的preLoadTexture方法预加载贴图路径
		MinecraftForgeClient.preloadTexture(ITEMS_TEXTURE_PATH); //利用Forge自带的preLoadTexture方法预加载贴图路径
	}
}
