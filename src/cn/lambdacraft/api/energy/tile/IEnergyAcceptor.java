package cn.lambdacraft.api.energy.tile;

import net.minecraft.tileentity.TileEntity;
import cn.lambdacraft.api.LCDirection;


public abstract interface IEnergyAcceptor extends IEnergyTile{
	public abstract boolean acceptEnergyFrom(TileEntity paramTileEntity, LCDirection paramDirection);
}