package cbproject.elements.gui;

import cbproject.elements.blocks.BlockWeaponCrafter.TileEntityWeaponCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotResult extends Slot {

	public SlotResult(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
    	return true;
    }

}
