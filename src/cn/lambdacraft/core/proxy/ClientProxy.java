/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.proxy;

import org.lwjgl.input.Keyboard;

import api.player.client.ClientPlayerAPI;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.CBCPlayer;
import cn.lambdacraft.core.client.key.KeyUse;
import cn.lambdacraft.core.register.CBCKeyProcess;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.deathmatch.client.renderer.RenderEmptyBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * 客户端代理加载。
 * 
 * @author WeAthFolD, Mkpoli, HopeAsd
 * 
 */
public class ClientProxy extends Proxy {

	CBCSoundEvents events = new CBCSoundEvents();
	
	@Override
	public void init() {
		super.init();
		events.onSound(new SoundLoadEvent(Minecraft.getMinecraft().sndManager));
		ClientPlayerAPI.register("CBCPlayer", CBCPlayer.class);
		RenderingRegistry.registerBlockHandler(new RenderEmptyBlock());
		TickRegistry.registerTickHandler(new CBCKeyProcess(), Side.CLIENT);
		ClientProps.loadProps(CBCMod.config);
	}
	
	@Override
	public void preInit() {
		MinecraftForge.EVENT_BUS.register(events);
		CBCKeyProcess.addKey(new KeyBinding("key.cbcuse", Keyboard.KEY_F), true, new KeyUse());
	}

	@Override
	public void profilerStartSection(String section) {
		if (isRendering()) {
			Minecraft.getMinecraft().mcProfiler.startSection(section);
		} else {
			super.profilerStartSection(section);
		}
	}

	@Override
	public void profilerEndSection() {
		if (isRendering()) {
			Minecraft.getMinecraft().mcProfiler.endSection();
		} else {
			super.profilerEndSection();
		}

	}

	@Override
	public void profilerEndStartSection(String section) {
		if (isRendering()) {
			Minecraft.getMinecraft().mcProfiler.endStartSection(section);
		}
		super.profilerEndStartSection(section);
	}

}
