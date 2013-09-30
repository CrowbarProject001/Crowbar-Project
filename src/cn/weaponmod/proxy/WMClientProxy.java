package cn.weaponmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cn.weaponmod.client.WMClientTickHandler;
import cn.weaponmod.client.keys.KeyClicking;
import cn.weaponmod.client.keys.KeyReload;
import cn.weaponmod.client.render.RenderBullet;
import cn.weaponmod.entities.EntityBullet;
import cn.weaponmod.events.WMKeys;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class WMClientProxy extends WMCommonProxy{

	Minecraft mc = Minecraft.getMinecraft();
	public static WMClientTickHandler cth = new WMClientTickHandler();
	
	@Override
	public void preInit() { 
		super.preInit();
	}
	
	@Override
	public void init() {
		WMKeys.addKey(mc.gameSettings.keyBindAttack, false, new KeyClicking(true));
		WMKeys.addKey(mc.gameSettings.keyBindUseItem, false, new KeyClicking(false));
		WMKeys.addKey(new KeyBinding("reload", Keyboard.KEY_R), false, new KeyReload());
		
		TickRegistry.registerTickHandler(new WMKeys(), Side.CLIENT);
		TickRegistry.registerTickHandler(cth, Side.CLIENT);
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet(0.5, 0.015, 1.0F, 1.0F, 1.0F).setIgnoreLight(true));
		
		super.init();
	}
	
}
