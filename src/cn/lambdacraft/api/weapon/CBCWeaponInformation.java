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
package cn.lambdacraft.api.weapon;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * 武器信息的公用存储。
 * 
 * @author WeAthFolD
 */
public final class CBCWeaponInformation {

	private static HashMap<Double, InformationSet> infPool = new HashMap();

	public static InformationSet addToList(double uniqueID, InformationSet inf) {
		infPool.put(uniqueID, inf);
		return inf;
	}

	public static InformationSet getInformation(double uniqueID) {
		return infPool.get(uniqueID);
	}

	public static InformationSet getInformation(ItemStack itemStack) {
		if (itemStack.getTagCompound() == null) {
			itemStack.stackTagCompound = new NBTTagCompound();
			return null;
		}
		double uniqueID = itemStack.getTagCompound().getDouble("uniqueID");
		return infPool.get(uniqueID);
	}

}
