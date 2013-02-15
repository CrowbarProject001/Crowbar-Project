package cbproject.elements.Items.weapons;

import cbproject.proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class Weapon_hgrenade extends Item {

	public Weapon_hgrenade(int par1ID) {
		super(par1ID);
		setItemName("weapon_hgrenade");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(2,0);
		setCreativeTab(CreativeTabs.tabTools);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 放开使用键时的行为
	 * @see net.minecraft.item.Item#onPlayerStoppedUsing(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer, int)
	 */
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
		return;
    }
	
	/*
	 * 右键点击的行为
	 * @see net.minecraft.item.Item#onItemRightClick(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.entity.player.EntityPlayer)
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        // ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
        //MinecraftForge.EVENT_BUS.post(event);

		
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
}
