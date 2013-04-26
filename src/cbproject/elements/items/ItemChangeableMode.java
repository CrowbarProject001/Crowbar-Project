package cbproject.elements.items;

import net.minecraft.item.Item;

public class ItemChangeableMode extends Item {

	int maxModes;
	public ItemChangeableMode(int par1, int par2MaxModes) {
		super(par1);
		maxModes = par2MaxModes;
	}
}
