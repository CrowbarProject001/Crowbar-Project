package cbproject.crafting.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.core.CBCMod;
import cbproject.crafting.items.ItemMaterial;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWeaponCrafter extends BlockContainer {

	public Icon iconSide, iconTop, iconBottom;
	public enum CrafterIconType{
		CRAFTING, NOMATERIAL, NONE;
	}
	
	public BlockWeaponCrafter(int par1) {
		super(par1, Material.iron);
		setCreativeTab(CBCMod.cct);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if(par1 < 1)
        	return iconBottom;
        if(par1 < 2)
        	return iconTop;
        return iconSide;
    }
    
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconSide = par1IconRegister.registerIcon("lambdacraft:crafter_side");
        iconTop = par1IconRegister.registerIcon("lambdacraft:crafter_top");
        iconBottom = par1IconRegister.registerIcon("lambdacraft:crafter_bottom");
        blockIcon = iconTop;
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
