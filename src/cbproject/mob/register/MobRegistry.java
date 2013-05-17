package cbproject.mob.register;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityList;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCSoundEvents;
import cbproject.mob.CBCMob;
import cbproject.mob.entities.EntitySnark;
import cbproject.mob.utils.EntitySelectorLiving;
import cpw.mods.fml.common.registry.EntityRegistry;

public class MobRegistry {
	
	public static IEntitySelector selectorLiving;
	static int startEntityId = 99;
	
	public static final String SND_MOBS[] = {
		"sqk_blast",
		"sqk_hunta",
		"sqk_huntb",
		"sqk_huntc",
		"sqk_die",
		"sqk_deploy"
	};
	
	public static void preRegister(){
		for(String s : SND_MOBS){
			CBCSoundEvents.addSoundPath("cbc/mobs/" + s, "/cbproject/gfx/sounds/mobs/" + s);
		}
	}
	
	public static int getUniqueEntityId() 
	{
		do 
		{
			startEntityId++;
		} 
		while (EntityList.IDtoClassMapping.get(startEntityId) != null);

	return startEntityId;
	}
	
	public static void register(){
		selectorLiving = new EntitySelectorLiving();
		EntityRegistry.registerModEntity(EntitySnark.class, "snark", GeneralProps.ENT_ID_SNARK, CBCMob.instance, 32, 3, true);
	}
}
