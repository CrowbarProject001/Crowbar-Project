package cbproject.api.energy.tile;

/**
 * 能源导线的能源属性
 * @author WeAthFolD，HopeAsd
 *
 */
public interface IEnConductor {
	 public abstract double getConductionLoss();

	 public abstract int getInsulationEnergyAbsorption();

	 public abstract int getInsulationBreakdownEnergy();

	 public abstract int getConductorBreakdownEnergy();

	 public abstract void removeInsulation();

	 public abstract void removeConductor();
}
