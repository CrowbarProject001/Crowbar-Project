package cbproject.elements.blocks.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cbproject.elements.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.elements.items.craft.ItemMaterial;
import cbproject.elements.recipes.RecipeWeaponEntry;
import cbproject.elements.recipes.RecipeWeapons;

public class TileEntityWeaponCrafter extends TileEntity implements IInventory {

	public ItemStack[] inventory;
	public ItemStack[] craftingStacks;
	public int scrollFactor = 0;
	public CrafterIconType iconType;
	public long lastTime = 0;
	public boolean redraw;

	/**
	 * 1-18 storage Inventory 19 furnace inventory 20 output inventory
	 * 
	 * craftingStacks:4*3 stacks.
	 */
	public TileEntityWeaponCrafter() {
		inventory = new ItemStack[20];
		craftingStacks = new ItemStack[12];
	}

	public void addScrollFactor(boolean isForward) {
		if (!RecipeWeapons.doesNeedScrollBar())
			return;
		if (isForward) {
			if (scrollFactor < RecipeWeapons.recipeList.size() - 3) {
				scrollFactor++;
			}
		} else {
			if (scrollFactor > 0) {
				scrollFactor--;
			}
		}
	}

	@Override
	public void updateEntity() {
		this.onInventoryChanged();
	}

	public void doItemCrafting(int slot) {
		
		RecipeWeaponEntry r = getRecipeBySlotAndScroll(slot, this.scrollFactor);

		if (hasEnoughMaterial(r)) {
			if (inventory[0] != null) {
				if (!(inventory[0].itemID != r.output.itemID || inventory[0]
						.isStackable()))
					return;
				if (inventory[0].isStackable()) {
					if (inventory[0].stackSize >= inventory[0]
							.getMaxStackSize())
						return;
					inventory[0].stackSize += r.output.stackSize;
				}
			} else
				inventory[0] = r.output.copy();
			consumeMaterial(r);
			iconType = CrafterIconType.SUCCESSFUL;
		} else {
			iconType = CrafterIconType.NOMATERIAL;
		}
		lastTime = worldObj.getWorldTime();
		this.redraw = true;
		this.onInventoryChanged();
	    
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

	public RecipeWeaponEntry getRecipeBySlotAndScroll(int slot, int factor) {
		int i = 0;
		if (slot == 0)
			i = 0;
		if (slot == 4)
			i = 1;
		if (slot == 8)
			i = 2;
		return RecipeWeapons.getRecipe(factor + i);
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