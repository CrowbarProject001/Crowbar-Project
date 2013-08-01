package cn.lambdacraft.api.energy;

public abstract interface IEnergySource extends IEnergyEmitter {
	public abstract int getMaxEnergyOutput();
}