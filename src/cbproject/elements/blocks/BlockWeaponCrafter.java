package cbproject.elements.blocks;

import java.util.Random;

import cbproject.CBCMod;
import cbproject.elements.items.craft.ItemMaterial;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWeaponCrafter extends BlockContainer {

	public class TileEntityWeaponCrafter extends TileEntity implements IInventory{

		public ItemStack[] inventory;
		
		public TileEntityWeaponCrafter(){
			inventory = new ItemStack[18];
		}
		
		@Override
		public int getSizeInventory() {
			return inventory.length;
		}

		@Override
		public ItemStack getStackInSlot(int i) {
			return inventory[i];
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
			return entityplayer.getDistanceSq(xCoord+0.5, yCoord+0.5, zCoord+0.5) <= 64;
		}

		@Override
		public void openChest() {}

		@Override
		public void closeChest() {}

		@Override
		public boolean isStackValidForSlot(int i, ItemStack itemstack) {
			if(itemstack.getItem() instanceof ItemMaterial)
				return true;
			return false;
		}

		@Override
		public void setInventorySlotContents(int i, ItemStack itemstack) {
			inventory[i] = itemstack;
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
            //code to open gui explained later
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
            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack item = inventory.getStackInSlot(i);

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
