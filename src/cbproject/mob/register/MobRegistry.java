package cbproject.mob.register;

import cpw.mods.fml.common.registry.EntityRegistry;
import cbproject.core.CBCMod;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCSoundEvents;
import cbproject.mob.entities.EntitySnark;
import cbproject.mob.utils.EntitySelectorLiving;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityList;
import net.minecraft.src.ModLoader;

public class MobRegistry {
	
	public static int ENTITY_ID_SNARK;
	public static IEntitySelector selectorLiving;
	
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
	
	public static void register(){
		ENTITY_ID_SNARK = EntityRegistry.findGlobalUniqueEntityId();
		selectorLiving = new EntitySelectorLiving();
		EntityRegistry.registerModEntity(EntitySnark.class, "Snark", ENTITY_ID_SNARK, CBCMod.instance, 64, 2, true);
		EntityList.addMapping(EntitySnark.class, "Snark", ENTITY_ID_SNARK, 0xffffff, 0x000000);
	}
}
