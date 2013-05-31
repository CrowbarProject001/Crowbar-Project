/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.crafting.blocks;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.items.ItemMaterial;
import cbproject.crafting.recipes.RecipeCrafter;
import cbproject.crafting.recipes.RecipeRepair;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.crafting.register.CBCBlocks;
import cbproject.deathmatch.utils.AmmoManager;

/**
 * 武器合成机和高级武器合成机的TileEntity类。
 * @author WeAthFolD
 */
public class TileEntityWeaponCrafter extends TileEntity implements IInventory {

	/**
	 * 最大存储热量。
	 */
	public static int MAX_HEAT;
	
	public ItemStack[] inventory;
	public ItemStack[] craftingStacks;
	public int scrollFactor = 0;
	public int page = 0;
	public int heat, burnTimeLeft, maxBurnTime;
	public RecipeCrafter currentRecipe;
	public CrafterIconType iconType;
	public long lastTime = 0;
	public boolean redraw, isCrafting, isBurning;
	public boolean isAdvanced = false;
	public boolean isLoad = false;
	
	/**
	 * inventory: 1-18：材料存储  19:燃料槽  20:合成结果槽
	 * 
	 * craftingStacks: 4*3  的合成表显示槽。
	 */
	public TileEntityWeaponCrafter() {
		inventory = new ItemStack[20];
		craftingStacks = new ItemStack[12];
	}

	@Override
	public void updateEntity() {
		if(!isLoad){
			if(blockType == null)
				return;
			isAdvanced = this.blockType.blockID == CBCBlocks.weaponCrafter.blockID? false : true;
			TileEntityWeaponCrafter.MAX_HEAT = isAdvanced ? 7000 : 4000;
			isLoad = true;
		}
		
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
        	if(worldObj.getWorldTime() - lastTime > 1000){
        		isCrafting = false;
        	}
        }
		if(isBurning){
			burnTimeLeft--;
			if(heat < MAX_HEAT)
				heat+=3;
			if(burnTimeLeft <= 0){
				isBurning = false;
				blockType.setLightValue(0.0F);
			}
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
	
    /**
     * Reads a tile entity from NBT.
     */
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        isAdvanced = nbt.getBoolean("advanced");
        for(int i = 0; i < 20; i++){
        	short id = nbt.getShort("id" + i), damage = nbt.getShort("damage" + i);
        	byte count = nbt.getByte("count" + i);
        	if(id == 0)
        		continue;
        	ItemStack is = new ItemStack(id, count, damage);
        	inventory[i] = is;
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("isAdvanced", isAdvanced);
        for(int i = 0; i < 20; i++){
        	if(inventory[i] == null)
        		continue;
        	nbt.setShort("id"+i, (short) inventory[i].itemID);
        	nbt.setByte("count"+i, (byte) inventory[i].stackSize);
        	nbt.setShort("damage"+i, (short)inventory[i].getItemDamage());
        }
    }
    
	public TileEntityWeaponCrafter setAdvanced(boolean is){
		isAdvanced = is;
		if(is)
			MAX_HEAT = 8000;
		else MAX_HEAT = 4000;
		return this;
	}
	
	public void addScrollFactor(boolean isForward) {
		if(!isAdvanced){
			if (!RecipeWeapons.doesNeedScrollBar(page))
				return;
		} else {
			if (!RecipeWeapons.doesAdvNeedScrollBar(page))
				return;
		}
		ArrayList<RecipeCrafter> recipes[] = (!isAdvanced? RecipeWeapons.recipes : RecipeWeapons.advancedRecipes);
		if (isForward) {
			if (scrollFactor < recipes[page].size() - 3) {
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
		ArrayList<RecipeCrafter> recipes[] = (!isAdvanced? RecipeWeapons.recipes : RecipeWeapons.advancedRecipes);
		if (isForward) {
			if (page < recipes.length - 1) {
				page++;
			}
		} else {
			if (page > 0) {
				page--;
			}
		}
		scrollFactor = 0;
		redraw = true;
		this.onInventoryChanged();
	}

    public void tryBurn(){
    	if(inventory[1] != null && TileEntityFurnace.getItemBurnTime(inventory[1]) > 0){
    		this.burnTimeLeft = TileEntityFurnace.getItemBurnTime(inventory[1]) / 2;
    		this.maxBurnTime = this.burnTimeLeft;
    		inventory[1].splitStack(1);
    		if(inventory[1].stackSize <= 1)
    			inventory[1] = null;
    		isBurning = true;
    		blockType.setLightValue(0.4F);
    	}
    }
	
	public void attemptItemCrafting(int slot) {
		RecipeCrafter r = getRecipeBySlotAndScroll(slot, this.scrollFactor);

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
		if(!hasEnoughMaterial(currentRecipe))
			return;
		if(!(currentRecipe instanceof RecipeRepair)){
			if (inventory[0] != null) {
				if (!(inventory[0].itemID == currentRecipe.output.itemID && inventory[0].isStackable()))
					return;
				if (inventory[0].stackSize >= inventory[0].getMaxStackSize()){
					lastTime = worldObj.getWorldTime();
					iconType = CrafterIconType.NOMATERIAL;
					return;
				}
				inventory[0].stackSize += currentRecipe.output.stackSize;
			} else {
				inventory[0] = currentRecipe.output.copy();
			} 
			consumeMaterial(currentRecipe);
		} else {
			if(inventory[0] != null){
				iconType = CrafterIconType.NOMATERIAL;
				lastTime = worldObj.getWorldTime();
				return;
			}
			RecipeRepair rs = (RecipeRepair) currentRecipe;
			int bulletCount = 0;
			int slotWeapon = 0;
			for(int i = 2; i < 20; i++){
				if(inventory[i] == null)
					continue;
				if(slotWeapon == 0 && inventory[i].getItem() == rs.inputA && inventory[i].getItemDamage() > 0)
					slotWeapon = i;
				if(inventory[i].getItem() == rs.inputB)
					bulletCount += inventory[i].stackSize;
			}
			if(slotWeapon == 0 || bulletCount == 0){
				lastTime = worldObj.getWorldTime();
				iconType = CrafterIconType.NOMATERIAL;
				return;
			}
			int damage = inventory[slotWeapon].getItemDamage() - bulletCount;
			int bulletToConsume = (damage<0) ? inventory[slotWeapon].getItemDamage() : bulletCount;
			damage = damage < 0? 0 : damage;
			AmmoManager.consumeInventoryItem(inventory, rs.inputB.itemID, bulletToConsume, 2);
			inventory[slotWeapon] = null;
			inventory[0] = new ItemStack(rs.inputA, 1, damage);
		}
		resetCraftingState();
	}
	
	public void resetCraftingState(){
		isCrafting = false;
		currentRecipe = null;
		iconType = CrafterIconType.NONE;
	}
	

	public boolean hasEnoughMaterial(RecipeCrafter r) {
		ItemStack is;

		if(r instanceof RecipeRepair){
			RecipeRepair rs = (RecipeRepair) r;
			boolean flag1 = false, flag2 = false;
			for(int i = 2; i < 20; i++){
				is = inventory[i];
				if(is == null)
					continue;
				if(is.getItem() == rs.inputA){
					if(is.getItemDamage() > 0)
						flag1 = true;
				} else if(is.getItem() == rs.inputB){
					flag2 = true;
				}
			}
			if(flag1 && flag2)
				return true;
			return false;
		}
		
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

	public void consumeMaterial(RecipeCrafter r) {

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

	public RecipeCrafter getRecipeBySlotAndScroll(int slot, int factor) {
		int i = 0;
		if (slot == 0)
			i = 0;
		if (slot == 4)
			i = 1;
		if (slot == 8)
			i = 2;
		if(!isAdvanced)
			return RecipeWeapons.getRecipe(page, factor + i);
		else return RecipeWeapons.getAdvRecipe(page, factor + i);
	}

}