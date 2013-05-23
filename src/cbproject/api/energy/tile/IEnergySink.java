package cbproject.api.energy.tile;

import cbproject.api.LCDirection;

public abstract interface IEnergySink extends IEnAcceptor
{
  public abstract int demandsEnergy();

  public abstract int injectEnergy(LCDirection paramDirection, int paramInt);

  public abstract int getMaxSafeInput();
}