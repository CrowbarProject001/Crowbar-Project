package cbproject.mob.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cbproject.core.CBCMod;
import cbproject.mob.entities.EntitySnark;
import cbproject.mob.utils.MobSpawnHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Weapon_snark extends Item{

	public Weapon_snark(int par1) {
		super(par1);
		this.setCreativeTab(CBCMod.cct);
		setUnlocalizedName("snark");
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:snark");
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if(par2World.isRemote)
    		return par1ItemStack;
    	
    	MobSpawnHandler.spawnCreature(par2World, EntitySnark.class, par3EntityPlayer);
    	if(!(par3EntityPlayer.capabilities.isCreativeMode)){
    		par1ItemStack.splitStack(1);
    		if(par1ItemStack.stackSize <= 0)
    			par1ItemStack = null;
    	}
    	par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 10;
    }
}
