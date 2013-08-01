package cn.lambdacraft.api.energy;

import cn.lambdacraft.api.LCDirection;

public abstract interface IEnergySink extends IEnergyAcceptor
{
  public abstract int demandsEnergy();

  public abstract int injectEnergy(LCDirection paramDirection, int paramInt);

  public abstract int getMaxSafeInput();
}