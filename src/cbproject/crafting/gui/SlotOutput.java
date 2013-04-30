package cbproject.crafting.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;

public class SlotOutput extends Slot {

	public SlotOutput(TileEntityWeaponCrafter par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}
	
	@Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)   {
		((TileEntityWeaponCrafter)inventory).doItemCrafting(this.slotNumber);
    }

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
    	return true;
    }


}