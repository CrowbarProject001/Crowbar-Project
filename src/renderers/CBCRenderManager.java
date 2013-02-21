package renderers;

import renderers.weapons.RenderHGrenade;
import net.minecraft.client.renderer.entity.RenderManager;
import cbproject.configure.Config;
import cbproject.elements.items.weapons.Weapon_hgrenade;

public class CBCRenderManager {
	RenderManager man=RenderManager.instance;
	
	public CBCRenderManager(Config par1) {
		// TODO Auto-generated constructor stub
	}
	
	public void Load(){
		//TODO:在这里注册渲染器
		man.entityRenderMap.put(Weapon_hgrenade.class,new RenderHGrenade(2));
		System.out.println("Renderer registered");
	}
}
