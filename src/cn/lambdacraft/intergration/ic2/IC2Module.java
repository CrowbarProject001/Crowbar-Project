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
package cn.lambdacraft.intergration.ic2;

import cn.lambdacraft.core.props.GeneralProps;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.crafting.register.CBCBlocks;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.blocks.BlockMedkitFiller;
import cn.lambdacraft.deathmatch.items.ArmorHEV.EnumAttachment;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.lambdacraft.intergration.ic2.item.ArmorHEVIC2;
import cn.lambdacraft.intergration.ic2.item.ItemBatteryIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockArmorChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockElCrafterIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockHealthChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.BlockMedkitFillerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileArmorChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileElCrafterIC2;
import cn.lambdacraft.intergration.ic2.tile.TileHealthChargerIC2;
import cn.lambdacraft.intergration.ic2.tile.TileMedkitFillerIC2;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;

/**
 * @author WeAthFolD
 *
 */
public class IC2Module {

	public static boolean init() {
		Class c;
		try {
			c = Class.forName("ic2.api.energy.tile.IEnergySink");
		} catch(Exception e) {
			return false;
		}
		if(c == null)
			return false;
		IC2Registration.registerBlocks();
		IC2Registration.registerItems();
		return true;
	}
	

	
}
