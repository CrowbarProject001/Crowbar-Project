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
package cn.lambdacraft.core.proxy;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * 加载代理。
 * 
 * @author WeAthFOlD, Mkpoli, HopeAsd
 * 
 */
public class Proxy {

	public static Set<String> languages = new HashSet();

	static {
		languages.add("zh_CN");
		languages.add("en_US");
		languages.add("zh_TW");
	}

	public static void profilerStartSection(String section) {

	}

	public static void profilerEndSection() {

	}

	public static void profilerEndStartSection(String section) {

	}

	public static boolean isRendering() {
		return !isSimulating();
	}

	private static boolean isSimulating() {
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	public void init() {
		for (String lang : languages) {
			LanguageRegistry.instance().loadLocalization(
					"/cbproject/lang/" + lang + ".properties", lang, false);
		}
	}

}
