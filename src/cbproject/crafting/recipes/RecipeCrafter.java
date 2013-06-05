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
package cbproject.crafting.recipes;

import net.minecraft.command.WrongUsageException;
import net.minecraft.item.ItemStack;

public class RecipeCrafter {
	public ItemStack[] input;
	public ItemStack output;
	public int heatRequired;
	
	public RecipeCrafter(ItemStack out, int heat, ItemStack...in) {
		if(in == null)
			throw new WrongUsageException("dont register null!");
		if(in.length > 3)
			throw new WrongUsageException("length must be within 3", input[0]);
		
		output = out;
		input = in;
		heatRequired = heat;
	}

	@Override
	public String toString(){
		StringBuilder out = new StringBuilder("[recipe:");
		for(ItemStack s : input)
			if(s != null)
				out.append(" " + s.getDisplayName());
		out.append(" -> " + output.getDisplayName() + "]");
		return out.toString();
	}
}
