package cn.lambdacraft.deathmatch.client.keys;

import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.register.IKeyProcess;
import cn.lambdacraft.deathmatch.network.NetDeathmatch;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class KeyReload implements IKeyProcess {

	@Override
	public void onKeyDown() {

		EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
		if (player == null)
			return;
		ItemStack currentItem = player.inventory.getCurrentItem();
		if (currentItem != null
				&& currentItem.getItem() instanceof WeaponGeneralBullet) {
			WeaponGeneralBullet wpn = (WeaponGeneralBullet) currentItem
					.getItem();
			InformationBullet inf = wpn.loadInformation(currentItem, player);
			onReload(currentItem, inf, player);
		}

	}

	@Override
	public void onKeyUp() {
	}

	private void onReload(ItemStack is, InformationBullet inf,
			EntityPlayer player) {
		if (!player.worldObj.isRemote)
			return;

		WeaponGeneralBullet wpn = (WeaponGeneralBullet) is.getItem();
		int stackInSlot = -1;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (player.inventory.mainInventory[i] == is) {
				stackInSlot = i;
				break;
			}
		}
		if (stackInSlot == -1)
			return;

		if (wpn.onSetReload(is, player))
			NetDeathmatch
					.sendModePacket((byte) stackInSlot, (byte) 1, (byte) 0);
	}
}
