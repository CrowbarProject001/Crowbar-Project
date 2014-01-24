/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.item;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

/**
 * @author WeAthFolD
 */
public class GRGenericItem extends Item {
	
	private String iconName = "";

	public GRGenericItem(int par1) {
		super(par1);
	}
	
	protected void setIconName(String str) {
		iconName = str;
	}
	
	/**
	 * 方便的快速设置函数。
	 * @param str
	 */
	protected void setIAndU(String str) {
		setUnlocalizedName(str);
		setIconName(str);
	}
	
	/**
	 * 为物品添加名字的方便函数。请务必在设置了unlocalizedName后调用。
	 */
	protected final void addLocalization(String str) {
		LanguageRegistry.addName(this, str);
	}
	
	/**
	 * 进行物品贴图的注册。
	 */
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
    	if(!iconName.equals(""))
    		itemIcon = iconRegister.registerIcon(iconName);
    }

}
