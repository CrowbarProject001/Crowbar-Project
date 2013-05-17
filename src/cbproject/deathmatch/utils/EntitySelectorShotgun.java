package cbproject.deathmatch.utils;

import cbproject.deathmatch.entities.EntityBulletSG;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

public class EntitySelectorShotgun implements IEntitySelector {

	@Override
	public boolean isEntityApplicable(Entity entity) {
		return !(entity instanceof EntityBulletSG);
	}

}
