package cbproject.core.utils;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EntitySelectorPlayer implements IEntitySelector {
	
	EntityPlayer exclusion;
	
	public EntitySelectorPlayer(EntityPlayer playerToExclude){
		exclusion = playerToExclude;
	}
	
	public EntitySelectorPlayer(){
		
	}
	
	@Override
	public boolean isEntityApplicable(Entity entity) {
		if(entity instanceof EntityPlayer && entity != exclusion)
			return true;
		return false;
	}
}
