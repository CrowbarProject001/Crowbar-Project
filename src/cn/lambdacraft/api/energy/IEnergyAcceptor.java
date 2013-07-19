package cn.lambdacraft.api.energy;

import net.minecraft.tileentity.TileEntity;
import cn.lambdacraft.api.LCDirection;


public abstract interface IEnergyAcceptor extends IEnergyTile{
	public abstract boolean acceptEnergyFrom(TileEntity paramTileEntity, LCDirection paramDirection);
}