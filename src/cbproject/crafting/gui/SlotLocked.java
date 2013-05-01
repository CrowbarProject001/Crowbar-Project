package cbproject.crafting.gui;

import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLocked extends Slot {

	public SlotLocked(TileEntityWeaponCrafter par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)   {
		((TileEntityWeaponCrafter)inventory).redraw = true;
    }
	
	
    @Override
	public ItemStack decrStackSize(int par1){
        return null;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return false;
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
    	return false;
    }

}
