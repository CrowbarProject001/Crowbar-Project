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
package cbproject.crafting.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cbproject.core.register.IGuiElement;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;

/**
 * 武器合成机的GUI注册。
 * @author WeAthFolD
 */
public class ElementCrafter implements IGuiElement {

	@Override
	public Object getServerContainer(EntityPlayer player, World world,
			int x, int y, int z) {
		return new ContainerWeaponCrafter(player.inventory, (TileEntityWeaponCrafter) world.getBlockTileEntity(x, y, z));
	}

	@Override
	public Object getClientGui(EntityPlayer player, World world, int x,
			int y, int z) {
		return new GuiWeaponCrafter(player.inventory, (TileEntityWeaponCrafter) world.getBlockTileEntity(x, y, z));
	}

}
