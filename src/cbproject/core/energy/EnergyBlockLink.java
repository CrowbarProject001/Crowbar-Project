package cbproject.core.energy;

import cbproject.api.LCDirection;

public class EnergyBlockLink {

	LCDirection lcdirection;
	double loss;

	EnergyBlockLink(LCDirection direction, double loss) {
		this.lcdirection = direction;
		this.loss = loss;
	}

}
