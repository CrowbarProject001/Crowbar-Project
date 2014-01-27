/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.item;

import cn.graphrevo.GraphRevo;
import cn.graphrevo.entity.EntityFrozen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * ⑨的冰冻法杖。可以发出一个冰球实体，冷冻水面，造成范围伤害。
 */
public class ItemFrozenWand extends GRGenericItem {

	/**
	 * 法杖物品的构造器。
	 */
	public ItemFrozenWand(int par1) {
		super(par1);
		this.setIAndU("graphrevo:frozen_wand"); //设置图标名和内部名称
		this.addLocalization("Frozen Wand"); //添加物品名称
		this.setCreativeTab(GraphRevo.cct);
		this.setMaxStackSize(1); //不可堆叠
		this.setMaxDamage(15); //最高可使用15次
	}
	
	//----------------功能函数---------------------
    /**
     * 当按下右键时被调用的函数。
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
    	entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack)); //设置为当前使用物品
        return itemStack;
    }
    
    /**
     * 玩家松开右键时被调用，释放实体。
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     */
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer entityPlayer, int par4) {
    	
    	if(!world.isRemote) { //仅在服务端生成
    		par4 = getMaxItemUseDuration(itemStack) - par4;
    		
    		int damage = par4 / 5 + 8;
    		if(damage > 15) damage = 15; //计算伤害
    	
    		world.spawnEntityInWorld(new EntityFrozen(world, entityPlayer, damage));
    	}
    	
    }
    
    /**
     * 获取最长的使用时间（ticks)
     */
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
    	return 200; //200ticks=10s
    }

}
