/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.proxy;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * 加载代理（Server端）
 */
public class GRCommonProxy {

	/**
	 * 执行第1阶段的加载行为。
	 */
	public void preInit() {}
	
	/**
	 * 执行第2阶段的加载行为。
	 */
	public void init() {
		LanguageRegistry.instance().addStringLocalization("ItemGroup.graph-revo", "Graphics Revolution"); //创造模式栏的名称
	}
	
	/**
	 * 执行第3阶段的加载行为。
	 */
	public void postInit() {}

}
