/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.registry;

import cn.graphrevo.item.ItemFrozenWand;

/**
 * Mod中所有物品实例的摆放位置。
 * @author WeAthFolD
 */
public class GRItems {

	public static ItemFrozenWand frozenWand;
	
	public static void init() {
		frozenWand = new ItemFrozenWand(7777);
	}

}
