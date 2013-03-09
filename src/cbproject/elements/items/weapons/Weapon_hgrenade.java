package cbproject.elements.items.weapons;

import cbproject.elements.entities.weapons.EntityHGrenade;
import cbproject.elements.events.weapons.EventHGrenadePin;
import cbproject.proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cbproject.CBCMod;

public class Weapon_hgrenade extends Item {

	public Weapon_hgrenade(int par1ID) {
		super(par1ID);
		setItemName("weapon_hgrenade");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(1,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(4);
		this.maxStackSize = 4;
		// TODO Auto-generated constructor stub
	}
	
	
	/*
	 * 
	 * @see net.minecraft.item.Item#onPlayerStoppedUsing(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer, int)
	 */
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
		Event event = new EventHGrenadePin(par3EntityPlayer, par1ItemStack);
		MinecraftForge.EVENT_BUS.post( event );
		
		if( event.isCanceled() || par4 >= 395){
			return;
		}
		
		System.out.println("Item use duration : " + par4);
		int duration = (par4 > 340) ? 400 - par4 : 60 ; //used time: if large than 3s use 3s

		if ( par1ItemStack.stackSize > 0)
			par2World.spawnEntityInWorld(new EntityHGrenade(par2World, par3EntityPlayer, duration));
		
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }
		System.out.println("Stopped using grenade");
		
		return;
    }
	
	/*
	 * 
	 * @see net.minecraft.item.Item#onItemRightClick(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		Event event = new EventHGrenadePin(par3EntityPlayer, par1ItemStack);
		MinecraftForge.EVENT_BUS.post( event );
		
		if( event.isCanceled() ){
			return par1ItemStack;
		}
		
        par2World.playSoundAtEntity(par3EntityPlayer, "cbc.weapons.hgrenadepin", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
        
    }
	
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 400; //20s
    }
    
    
}
