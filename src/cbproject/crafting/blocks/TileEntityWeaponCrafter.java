package cbproject.crafting.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.items.ItemMaterial;
import cbproject.crafting.recipes.RecipeWeaponEntry;
import cbproject.crafting.recipes.RecipeWeapons;

public class TileEntityWeaponCrafter extends TileEntity implements IInventory {

	public static int MAX_HEAT = 4000;
	
	public ItemStack[] inventory;
	public ItemStack[] craftingStacks;
	public int scrollFactor = 0;
	public int page = 0;
	public int heat, burnTimeLeft, maxBurnTime;
	public RecipeWeaponEntry currentRecipe;
	public CrafterIconType iconType;
	public long lastTime = 0;
	public boolean redraw, isCrafting, isBurning;

	/**
	 * 1-18 storage Inventory 19 furnace inventory 20 output inventory
	 * 
	 * craftingStacks:4*3 stacks.
	 */
	public TileEntityWeaponCrafter() {
		inventory = new ItemStack[20];
		craftingStacks = new ItemStack[12];
	}

	@Override
	public void updateEntity() {
		if(heat > 0)
			heat--;
		
		if(iconType == CrafterIconType.NOMATERIAL && worldObj.getWorldTime() - lastTime > 20){
			iconType = isCrafting? CrafterIconType.CRAFTING : CrafterIconType.NONE;
		}
		
		if(isCrafting){
			if(currentRecipe.heatRequired <= this.heat && hasEnoughMaterial(currentRecipe)){
				craftItem();
			}
        	if(!isBurning){
        		tryBurn();
        	}
        	if(worldObj.getWorldTime() - lastTime > 500){
        		isCrafting = false;
        	}
        }
		if(isBurning){
			burnTimeLeft--;
			if(heat < MAX_HEAT)
				heat+=3;
			if(burnTimeLeft <= 0)
				isBurning = false;
		}
		this.onInventoryChanged();
	}
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		if (i >= 12)
			return inventory[i - 12];
		return craftingStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}
	
	
	
	public void addScrollFactor(boolean isForward) {
		if (!RecipeWeapons.doesNeedWeaponScrollBar(page))
			return;
		if (isForward) {
			if (scrollFactor < RecipeWeapons.recipes[page].size() - 3) {
				scrollFactor++;
			}
		} else {
			if (scrollFactor > 0) {
				scrollFactor--;
			}
		}
		redraw = true;
		this.onInventoryChanged();
	}
	
	public void addPage(boolean isForward) {
		if (isForward) {
			if (page < RecipeWeapons.recipes.length - 1) {
				page++;
			}
		} else {
			if (page > 0) {
				page--;
			}
		}
		redraw = true;
		this.onInventoryChanged();
	}

    public void tryBurn(){
    	if(inventory[1] != null && TileEntityFurnace.getItemBurnTime(inventory[1]) > 0){
    		this.burnTimeLeft = TileEntityFurnace.getItemBurnTime(inventory[1]) / 2;
    		this.maxBurnTime = this.burnTimeLeft;
    		inventory[1].splitStack(1);
    		isBurning = true;
    	}
    }
	
	public void doItemCrafting(int slot) {
		RecipeWeaponEntry r = getRecipeBySlotAndScroll(slot, this.scrollFactor);

		if (hasEnoughMaterial(r)) {
			resetCraftingState();
			iconType = CrafterIconType.CRAFTING;
			this.isCrafting = true;
			this.currentRecipe = r;
		} else {
			iconType = CrafterIconType.NOMATERIAL;
		}
		lastTime = worldObj.getWorldTime();
		this.redraw = true;
		this.onInventoryChanged();
	    
	}
	
	public void craftItem(){
		if(this.currentRecipe == null){
			resetCraftingState();
			return;
		}
		if (inventory[0] != null) {
			if (!(inventory[0].itemID != currentRecipe.output.itemID || inventory[0]
					.isStackable()))
				return;
			if (inventory[0].isStackable()) {
				if (inventory[0].stackSize >= inventory[0].getMaxStackSize()){
					iconType = CrafterIconType.NOMATERIAL;
					return;
				}
				inventory[0].stackSize += currentRecipe.output.stackSize;
			}
		} else
			inventory[0] = currentRecipe.output.copy();
		consumeMaterial(currentRecipe);
		resetCraftingState();
	}
	
	public void resetCraftingState(){
		isCrafting = false;
		currentRecipe = null;
		iconType = CrafterIconType.NONE;
	}

	public boolean hasEnoughMaterial(RecipeWeaponEntry r) {
		ItemStack is;

		int left[] = new int[3];
		for (int j = 0; j < r.input.length; j++) {
			if (r.input[j] != null) {
				left[j] = r.input[j].stackSize;
			} else
				left[j] = 0;
		}

		for (int i = 2; i < 20; i++) {
			is = inventory[i];
			boolean flag = is != null;
			if (flag) {
				for (int j = 0; j < r.input.length; j++) {
					if (r.input[j].itemID == is.itemID) {
						if (left[j] < is.stackSize)
							left[j] = 0;
						else
							left[j] -= is.stackSize;
					}
				}
			}
		}
		Boolean flag = true;
		for (int i : left)
			if (i > 0)
				flag = false;
		return flag;

	}

	public void consumeMaterial(RecipeWeaponEntry r) {

		int left[] = new int[r.input.length];
		for (int j = 0; j < r.input.length; j++) {
			if (r.input[j] != null) {
				left[j] = r.input[j].stackSize;
			} else
				left[j] = 0;
		}

		for (int i = 2; i < 20; i++) {
			for (int j = 0; j < r.input.length; j++) {
				boolean flag = inventory[i] != null;
				if (flag && inventory[i].itemID == r.input[j].itemID) {
					if (inventory[i].stackSize > left[j]) {
						inventory[i].splitStack(left[j]);
						left[j] = 0;
					} else {
						left[j] -= inventory[i].stackSize;
						inventory[i] = null;
					}
				}
			}
		}

	}

	public RecipeWeaponEntry getRecipeBySlotAndScroll(int slot, int factor) {
		int i = 0;
		if (slot == 0)
			i = 0;
		if (slot == 4)
			i = 1;
		if (slot == 8)
			i = 2;
		return RecipeWeapons.getRecipe(page, factor + i);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public String getInvName() {
		return "lambdacraft:weaponcrafter";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		if (i > 12 && itemstack.getItem() instanceof ItemMaterial)
			return true;
		return false;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		if (i < 12)
			craftingStacks[i] = itemstack;
		else
			inventory[i - 12] = itemstack;
	}

}