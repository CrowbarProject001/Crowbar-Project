/**
 * 
 */
package cbproject.crafting.blocks;

import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.recipes.RecipeCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * @author HopeAsd
 *
 */
public class TileEntityGeneric extends TileEntity implements IInventory{
	
	public static int MAX_HEAT;
	
	public ItemStack[] inventory;
	public int scrollFactor = 0;
	public int page = 0;
	public int heat, burnTimeLeft, maxBurnTime;
	public RecipeCrafter currentRecipe;
	public CrafterIconType iconType;
	public long lastTime = 0;
	public boolean redraw, isProduce, isBurning;
	
	/**
	 * inventory: 1：材料存储  2:燃料槽  3:合成结果槽
	 * 
	 * 
	 */
	public TileEntityGeneric() {
		inventory = new ItemStack[3];
	}


	@Override
	public int getSizeInventory() {
		// TODO 自动生成的方法存根
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return this.inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(this.inventory[i] != null){
			if(this.inventory[i].stackSize <= j){
				ItemStack itemstack = this.inventory[i];
				this.inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = this.inventory[i].splitStack(j);
			if (this.inventory[i].stackSize == 0){
				this.inventory[i] = null;
			}
			return itemstack1;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public String getInvName() {
		// TODO 自动生成的方法存根
		return "lambdacraft:weaponcrafter";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自动生成的方法存根
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return entityplayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
				zCoord + 0.5) <= 64;
	}

	@Override
	public void openChest() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void closeChest() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		// TODO 自动生成的方法存根
		return false;
	}

}
