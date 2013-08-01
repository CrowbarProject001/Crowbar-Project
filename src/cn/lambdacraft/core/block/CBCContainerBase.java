package cn.lambdacraft.core.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class CBCContainerBase extends Container{
	
	public final IInventory base;
	
	public CBCContainerBase(IInventory base){
		this.base = base;
	}
	public void updateProgressBar(int index , int value){};
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO 自动生成的方法存根
		return this.base.isUseableByPlayer(entityplayer);
	}

}
