package cbproject.Proxy;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy{
	
	public final static String BLOCKS_TEXTURE_PATH = "/cbproject/Textures/Blocks.png"; //������ͼ·��
	public final static String ITEMS_TEXTURE_PATH = "/cbproject/Textures/Items.png"; //��Ʒ��ͼ·��
	@Override 
	public void init() { //���Ǳ��̳е� Proxy���е�init()����
		MinecraftForgeClient.preloadTexture(BLOCKS_TEXTURE_PATH); //����Forge�Դ���preLoadTexture����Ԥ������ͼ·��
		MinecraftForgeClient.preloadTexture(ITEMS_TEXTURE_PATH); //����Forge�Դ���preLoadTexture����Ԥ������ͼ·��
	}
}
