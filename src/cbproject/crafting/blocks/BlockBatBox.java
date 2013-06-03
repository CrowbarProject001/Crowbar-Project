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
package cbproject.crafting.blocks;

import cbproject.core.props.GeneralProps;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class BlockBatBox extends BlockElectricalBase {

	private final int type;
	
	/**
	 * @param par1
	 * @param mat
	 */
	public BlockBatBox(int par1, int typ) {
		super(par1, Material.rock);
		this.setUnlocalizedName("batbox" + typ);
		type = typ;
		this.setGuiId(GeneralProps.GUI_ID_BATBOX);
		if(type != 0 && type != 1)
			throw new RuntimeException();
		
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileBatBox(type);
	}

}
