package cbproject.mob;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLiving;
import cbproject.core.CBCMod;
import cbproject.core.module.CBCSubModule;
import cbproject.core.module.ModuleInit;
import cbproject.core.module.ModuleInit.EnumInitType;
import cbproject.core.props.GeneralProps;
import cbproject.core.proxy.ClientProxy;
import cbproject.core.register.CBCSoundEvents;
import cbproject.mob.entities.EntitySnark;
import cbproject.mob.register.CBCMobItems;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;



@CBCSubModule("mob")
public class CBCMob
{
	
	@Instance("cbc.mob")
	public static CBCMob instance;
	
	@ModuleInit(EnumInitType.PREINIT)
	public void preInit(FMLPreInitializationEvent Init)
	{
		if(ClientProxy.isRendering())
			for(String s : SND_MOBS){
				CBCSoundEvents.addSoundPath("cbc/mobs/" + s, "/cbproject/gfx/sounds/mobs/" + s);
			}
	}

	
	@ModuleInit(EnumInitType.INIT)
	public void init(FMLInitializationEvent Init){
		CBCMobItems.init(CBCMod.config);
		EntityRegistry.registerModEntity(EntitySnark.class, "snark", GeneralProps.ENT_ID_SNARK, CBCMod.instance, 32, 3, true);
	}

	@ModuleInit(EnumInitType.POSTINIT)
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ModuleInit(EnumInitType.SVINIT)
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
	@ModuleInit(EnumInitType.CLINIT)
	public void registerRenderingThings(){
		RenderingRegistry.registerEntityRenderingHandler(EntitySnark.class, new RenderLiving(new ModelSlime(1), 0.2F));
	}
	
	public static final String SND_MOBS[] = {
		"sqk_blast",
		"sqk_hunta",
		"sqk_huntb",
		"sqk_huntc",
		"sqk_die",
		"sqk_deploy"
	};
	
}