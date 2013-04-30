package cbproject.crafting.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeapons;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerWeaponCrafter extends Container {

	public TileEntityWeaponCrafter tileEntity;
	public int scrollFactor;
	
	public ContainerWeaponCrafter(InventoryPlayer inventoryPlayer, TileEntityWeaponCrafter te) {
		tileEntity = te;
		
		//Crafting recipe slot
		for(int i = 0; i < 3; i++){
			//output:0 4 8
			Slot s = addSlotToContainer(new SlotOutput(te, 9 + i, 88, 19 + 22*i ));
			//input :123 567 9.10.11
			for(int j = 0; j < 3; j++){
				addSlotToContainer(new SlotLocked(te, j + i*3, 12+22*j, 19 + 22*i ));
			}
			
		}
		
		addSlotToContainer(new Slot(te, 13, 136, 63));
		Slot s = addSlotToContainer(new SlotResult(te, 12, 136, 19));
		//Block Storage
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 9; j++){
				addSlotToContainer(new Slot(te, 14 + 9*i + j, 8 + 21*j, 100 + 22*i));
			}
		}
		
		bindPlayerInventory(inventoryPlayer);
		
		scrollFactor = te.scrollFactor;
		writeRecipeInfoToSlot();
	}
	
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                	addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 21, 156 + i * 22));
                }
        }
        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 21, 229));
        }
    }
	
    @Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            icrafting.sendProgressBarUpdate(this, 0, (tileEntity.redraw? 1 : -1) *tileEntity.scrollFactor);
            if(tileEntity.redraw)
            	tileEntity.redraw = false;
        }
    }

	
    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int par1, int par2) {
    	if(par1 == 0){
    		scrollFactor = Math.abs(par2);
    		if(par2 >= 0){
    			writeRecipeInfoToSlot();
    		}
    	}
    }

	
	private void writeRecipeInfoToSlot(){
		clearRecipeInfo();
		int length = RecipeWeapons.getRecipeLength(tileEntity.page);
		for(int i = 0; i < length&& i < 3; i++){
			RecipeWeaponEntry r = RecipeWeapons.getRecipe(tileEntity.page, i + scrollFactor);
			for(int j = 0; j < 3; j++){
				if(r.input.length > j)
					tileEntity.setInventorySlotContents(j + i*3, r.input[j]);
			}
			tileEntity.setInventorySlotContents(9 + i, r.output);
		}
	}
	
	private void clearRecipeInfo() {
		for(int i = 0; i < 12; i++){
				tileEntity.setInventorySlotContents(i, null);
		}
	}
	
    @Override
    public boolean canInteractWith(EntityPlayer player) {
            return tileEntity.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
            ItemStack stack = null;
            Slot slotObject = (Slot) inventorySlots.get(slot);
            //null checks and checks if the item can be stacked (maxStackSize > 1)
            if (slotObject != null && slotObject.getHasStack()) {
                    ItemStack stackInSlot = slotObject.getStack();
                    stack = stackInSlot.copy();

                    
                    //places it into the tileEntity is possible since its in the player inventory
                    if (slot >= 32) {
                            if (!this.mergeItemStack(stackInSlot, 14, 32, true)) {
                                    return null;
                            }
                    }
                    //merges the item into player inventory since its in the tileEntity
                    else if(slot >= 12) {
                    	if (!this.mergeItemStack(stackInSlot, 32, 67, false)) 
                            return null;
                    }

                    if (stackInSlot.stackSize == 0) {
                            slotObject.putStack(null);
                    } else {
                            slotObject.onSlotChanged();
                    }

                    if (stackInSlot.stackSize == stack.stackSize) {
                            return null;
                    }
                    slotObject.onPickupFromSlot(player, stackInSlot);
            }
            return stack;
    }
}
