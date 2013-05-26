/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.core.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cbproject.api.energy.item.ICustomEnItem;

/**
 * @author WeAthFolD
 *
 */
public class EnergyUtils {

	/**
	 * TODO:NEEDS INTERGRATION.
	 * Charges the HEV armor wearing on the player with the amount of par2.
	 * @param player the player entity
	 * @return amount of the energy received from armor
	 */
	public static int tryChargeArmor(EntityPlayer player, int energy, int tier, boolean ignoreTransferLimit){
		int amount = 0;
		List<ICustomEnItem> armor = new ArrayList();
		List<ItemStack> stack = new ArrayList();
		for(ItemStack s : player.inventory.armorInventory){
			if(s != null && (s.getItem() instanceof ICustomEnItem)){
				amount++;
				ICustomEnItem i = (ICustomEnItem)s.getItem();
				armor.add(i);
				stack.add(s);
			}
		}
		int received = 0;
		for(int i = 0; i < stack.size(); i++){
			received += armor.get(i).charge(stack.get(i), energy / amount, tier, ignoreTransferLimit, false);
		}
		return received;
	}
}
