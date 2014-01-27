/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.misc;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * 自定义CreativeTab。
 * @author WeAthFolD
 */
public class CCTGraphRevo extends CreativeTabs {

	/**
	 * @param label 标签名称
	 */
	public CCTGraphRevo(String label) {
		super(label);
	}
	
	@Override
	public int getTabIconItemIndex() {
		return Block.enchantmentTable.blockID;
	}

}
