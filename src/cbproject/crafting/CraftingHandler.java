package cbproject.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;
import cbproject.core.register.CBCAchievements;
import cbproject.crafting.register.CBCItems;

public class CraftingHandler implements ICraftingHandler {

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		}


	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		if (!player.worldObj.isRemote) {
			if (item.itemID == CBCItems.ingotSteel.itemID) {
				CBCAchievements.getAchievement(player,
						CBCAchievements.ohMyTeeth);
			} else if (item.itemID == CBCItems.ingotUranium.itemID) {
				CBCAchievements.getAchievement(player,
						CBCAchievements.radioactiveBeryl);
			}
		}
	}

}
