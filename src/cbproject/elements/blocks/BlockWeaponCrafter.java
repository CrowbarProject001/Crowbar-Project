package cbproject.elements.blocks;

import java.util.Random;

import cbproject.CBCMod;
import cbproject.elements.items.craft.ItemMaterial;
import cbproject.elements.recipes.RecipeWeaponEntry;
import cbproject.elements.recipes.RecipeWeapons;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWeaponCrafter extends BlockContainer {

	public enum CrafterIconType{
		CRAFTING, NOMATERIAL, SUCCESSFUL, NONE;
	}
	
	public class TileEntityWeaponCrafter extends TileEntity implements IInventory{

		public ItemStack[] inventory;
		public ItemStack[] craftingStacks;
		public int scrollFactor = 0;
		public CrafterIconType iconType;
		public long lastTime = 0;
		/**
		 * 1-18 storage Inventory
		 * 19 furnace inventory
		 * 20 output inventory
		 * 
		 * craftingStacks:4*3 stacks.
		 */
		public TileEntityWeaponCrafter(){
			inventory = new ItemStack[20];
			craftingStacks = new ItemStack[12];
		}
		
		public void addScrollFactor(boolean isForward){
			if(!RecipeWeapons.doesNeedScrollBar())
				return;
			if(isForward){
				if(scrollFactor < RecipeWeapons.recipeList.size() - 3){
					scrollFactor++;
				}
			} else {
				if(scrollFactor > 0){
					scrollFactor--;
				}
			}
			System.out.println(worldObj.isRemote + "new scrollfactor : " + scrollFactor);
		}
		
		@Override
		 public void updateEntity()
	    {
	        if (true)
	        {
	            this.onInventoryChanged();
	        }
	    }
		
        public void doItemCrafting(int slot){
        	RecipeWeaponEntry r = getRecipeBySlotAndScroll(slot, this.scrollFactor);
        	System.out.println(worldObj.isRemote + "scrollFactor : " + this.scrollFactor);
        	System.out.println(r);
        	
        	if(hasEnoughMaterial(r)){
        		if(inventory[0] != null){
        			if(!(inventory[0].itemID != r.output.itemID || inventory[0].isStackable()))
        				return;
        			if(inventory[0].isStackable()){
            			if(inventory[0].stackSize >= inventory[0].getMaxStackSize())
            				return;
            			inventory[0].stackSize += r.output.stackSize;
        			}
        		} else inventory[0] = r.output.copy();
        		consumeMaterial(r);
        		iconType = CrafterIconType.SUCCESSFUL;
        	} else{
        		iconType = CrafterIconType.NOMATERIAL;
        	}
        	lastTime = worldObj.getWorldTime();
        	
        }
        
        public boolean hasEnoughMaterial(RecipeWeaponEntry r){
        	ItemStack is;
        	
        	int left[] = new int[3];
    		for(int j = 0; j < r.input.length; j++){
    			if(r.input[j] != null){
    				left[j] = r.input[j].stackSize;
    			} else left[j] = 0;
    		}
    		
        	for(int i = 2; i < 20; i++){
        		is = inventory[i];
        		boolean flag = is != null;
        		if(flag){
        			for(int j = 0; j < r.input.length; j++){
        				if(r.input[j].itemID == is.itemID){
        					if(left[j] < is.stackSize)
        						left[j] = 0;
        					else left[j] -= is.stackSize;
        				}
        			}
        		}
        	}
        	Boolean flag = true;
        	for(int i:left)
        		if(i > 0)flag = false;
        	return flag;
        	
        }
        
        public void consumeMaterial(RecipeWeaponEntry r){
        	
        	int left[] = new int[r.input.length];
    		for(int j = 0; j < r.input.length; j++){
    			if(r.input[j] != null){
    				left[j] = r.input[j].stackSize;
    			} else left[j] = 0;
    		}
    		
        	for(int i = 2; i < 20; i++){
        		for(int j = 0; j < r.input.length; j++){
        			boolean flag = inventory[i] != null;
        			if(flag && inventory[i].itemID == r.input[j].itemID){
        				if(inventory[i].stackSize > left[j]){
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
			if(i >= 12)
				return inventory[i-12];
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

        public RecipeWeaponEntry getRecipeBySlotAndScroll(int slot,int factor){
        	int i = 0;
        	if(slot == 0)
        		i = 0;
        	if(slot == 4)
        		i = 1;
        	if(slot == 8)
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
			return entityplayer.getDistanceSq(xCoord+0.5, yCoord+0.5, zCoord+0.5) <= 64;
		}

		@Override
		public void openChest() {}

		@Override
		public void closeChest() {}

		@Override
		public boolean isStackValidForSlot(int i, ItemStack itemstack) {
			if(i > 12 && itemstack.getItem() instanceof ItemMaterial)
				return true;
			return false;
		}

		@Override
		public void setInventorySlotContents(int i, ItemStack itemstack) {
			if(i < 12)
				craftingStacks[i] = itemstack;
			else inventory[i-12] = itemstack;
		}

	}
	
	public BlockWeaponCrafter(int par1) {
		super(par1, Material.iron);
		setCreativeTab(CBCMod.cct);
	}
	
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                    EntityPlayer player, int idk, float what, float these, float are) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if (tileEntity == null || player.isSneaking()) {
                    return false;
            }
            player.openGui(CBCMod.cbcMod, 0, world, x, y, z);
            return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
            dropItems(world, x, y, z);
            super.breakBlock(world, x, y, z, par5, par6);
    }

    private void dropItems(World world, int x, int y, int z){
            Random rand = new Random();

            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if (!(tileEntity instanceof IInventory)) {
                    return;
            }
            TileEntityWeaponCrafter inventory = (TileEntityWeaponCrafter) tileEntity;

            for (ItemStack item : inventory.inventory) {

                    if (item != null && item.stackSize > 0) {
                            float rx = rand.nextFloat() * 0.8F + 0.1F;
                            float ry = rand.nextFloat() * 0.8F + 0.1F;
                            float rz = rand.nextFloat() * 0.8F + 0.1F;

                            EntityItem entityItem = new EntityItem(world,
                                            x + rx, y + ry, z + rz,
                                            new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                            if (item.hasTagCompound()) {
                                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                            }

                            float factor = 0.05F;
                            entityItem.motionX = rand.nextGaussian() * factor;
                            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                            entityItem.motionZ = rand.nextGaussian() * factor;
                            world.spawnEntityInWorld(entityItem);
                            item.stackSize = 0;
                    }
            }
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWeaponCrafter();
	}

}
