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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.core.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.core.CBCMod;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCNetHandler;
import cbproject.crafting.network.NetCrafterClient;
import net.minecraft.util.StringTranslate;

/**
 * 加载代理。
 * @author WeAthFOlD, Mkpoli, HopeAsd
 *
 */
public class Proxy {
	
	public static void profilerStartSection(String section){
		
	}

	public static void profilerEndSection(){
		
	}
	
	public static void profilerEndStartSection(String section){
		
	}
	
	public static boolean isRendering(){
		return !isSimulating();
	}
	private static boolean isSimulating() {
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
	
	public void init() {
		String currentLang = StringTranslate.getInstance().getCurrentLanguage();
		if(currentLang != "en_US")
			LanguageRegistry.instance().loadLocalization("/cbproject/lang/" + currentLang + ".properties", currentLang, false);
		else LanguageRegistry.instance().loadLocalization("/cbproject/lang/en_US.properties", "en_US", false);
	}
	
}
