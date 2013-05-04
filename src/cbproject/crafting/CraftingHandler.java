package cbproject.crafting;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;

import cbproject.core.register.CBCBlocks;
import cbproject.core.register.CBCItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.register.CBCAchievements;

public class CraftingHandler implements ICraftingHandler {

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item,
			IInventory craftMatrix) {
		}


	@Override
	@SideOnly(Side.CLIENT)
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub
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
