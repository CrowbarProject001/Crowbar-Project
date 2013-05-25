package cbproject.deathmatch.keys;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cbproject.core.register.IKeyProcess;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.utils.InformationWeapon;

public class KeyMode implements IKeyProcess {

	@Override
	public void onKeyDown() {
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		if(player == null)
			return;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if (currentItem != null
				&& currentItem.getItem() instanceof WeaponGeneral) {
			WeaponGeneral wpn = (WeaponGeneral) currentItem.getItem();
			InformationWeapon inf = wpn.loadInformation(currentItem, player);
			onModeChange(currentItem, inf, player, wpn.maxModes);
		}
	}

	@Override
	public void onKeyUp() {
	}

	private void onModeChange(ItemStack itemStack, InformationWeapon inf,
			EntityPlayer player, int maxModes) {
		if (!player.worldObj.isRemote)
			return;

		WeaponGeneral wpn = (WeaponGeneral) itemStack.getItem();
		int stackInSlot = -1;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (player.inventory.mainInventory[i] == itemStack) {
				stackInSlot = i;
				break;
			}
		}
		if (stackInSlot == -1)
			return;

		if (inf == null)
			return;
		int mode = wpn.getMode(itemStack);
		mode = (maxModes - 1 <= mode) ? 0 : mode + 1;
		NetDeathmatch.sendModePacket(stackInSlot, (short) 0, mode);

	}

}
