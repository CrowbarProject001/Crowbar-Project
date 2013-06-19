package cn.lambdacraft.deathmatch.keys;

import cn.lambdacraft.api.weapon.InformationWeapon;
import cn.lambdacraft.api.weapon.WeaponGeneral;
import cn.lambdacraft.core.register.IKeyProcess;
import cn.lambdacraft.deathmatch.network.NetDeathmatch;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class KeyMode implements IKeyProcess {

	@Override
	public void onKeyDown() {
		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		if (player == null)
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
		NetDeathmatch.sendModePacket((byte) stackInSlot, (byte) 0, (byte) mode);

	}

}
