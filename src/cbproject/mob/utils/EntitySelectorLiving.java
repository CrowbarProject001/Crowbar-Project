package cbproject.mob.utils;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntitySelectorLiving implements IEntitySelector {

	@Override
	public boolean isEntityApplicable(Entity entity) {
		return IEntitySelector.selectAnything.isEntityApplicable(entity) && entity instanceof EntityLiving;
	}

}
