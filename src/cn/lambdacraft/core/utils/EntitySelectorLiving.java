package cn.lambdacraft.core.utils;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class EntitySelectorLiving implements IEntitySelector {

	@Override
	public boolean isEntityApplicable(Entity entity) {
		boolean b = IEntitySelector.selectAnything.isEntityApplicable(entity)
				&& entity instanceof EntityLiving && !entity.isEntityInvulnerable();
		if(entity instanceof EntityPlayer) {
			b &= !((EntityPlayer)entity).capabilities.isCreativeMode;
		}
		return b;
	}

}
