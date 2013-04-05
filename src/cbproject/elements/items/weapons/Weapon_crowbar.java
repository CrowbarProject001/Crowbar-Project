package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class Weapon_crowbar extends ItemSword {

	public Weapon_crowbar(int item_id) {
		super(item_id, EnumToolMaterial.IRON); //铁剑的基本属性
		
		setItemName("weapon_crowbar");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(0, 2);
		setCreativeTab( CBCMod.cct );
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

}
