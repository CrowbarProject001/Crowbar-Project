package cbproject.deathmatch.keys;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cbproject.core.register.IKeyProcess;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.utils.InformationBullet;

public class DMReload implements IKeyProcess {

	@Override
	public void onKeyDown() {
		
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		if(player == null)
			return;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if(currentItem!= null && currentItem.getItem() instanceof WeaponGeneralBullet){
			WeaponGeneralBullet wpn = (WeaponGeneralBullet) currentItem.getItem();
			InformationBullet inf = wpn.loadInformation(currentItem, player);
			onReload(currentItem, inf, player);
		}
		
	}

	@Override
	public void onKeyUp() {
	}
	
	private void onReload(ItemStack is, InformationBullet inf, EntityPlayer player){
		if(!player.worldObj.isRemote)
			return;

		WeaponGeneralBullet wpn = (WeaponGeneralBullet) is.getItem();
		int stackInSlot = -1;
		for(int i = 0; i < player.inventory.mainInventory.length; i++){
			if(player.inventory.mainInventory[i] == is){
				stackInSlot = i;
				break;
			}
		}
		if(stackInSlot == -1)
			return;
		
		if(wpn.onSetReload(is, player))
			NetDeathmatch.sendModePacket(stackInSlot, (short) 1, 0);
	}
}
