package cbproject.mob;

import cbproject.core.CBCMod;
import cbproject.mob.register.CBCMobItems;
import cbproject.mob.register.MobRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;



@Mod(modid="lcmob",name="LambdaCraft Mob Module",version="0.9.9 pre")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class CBCMob
{
	
	@Instance("lcmob")
	public static CBCMob instance;
	
	@SidedProxy(clientSide="cbproject.mob.proxy.ClientProxy",serverSide="cbproject.mob.proxy.Proxy")
	public static cbproject.mob.proxy.Proxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		MobRegistry.preRegister();
	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		CBCMobItems.init(CBCMod.config);
		
		proxy.init();
		MobRegistry.register();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
}