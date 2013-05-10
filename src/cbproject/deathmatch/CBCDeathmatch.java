package cbproject.deathmatch;

import cbproject.core.CBCMod;
import cbproject.core.register.CBCPacketHandler;
import cbproject.deathmatch.register.DMBlocks;
import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.register.DMRegister;
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
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

@Mod(modid="lcdm",name="LambdaCraft DM Module",version="0.9.9 pre")
@NetworkMod(clientSideRequired=true,serverSideRequired=false,
serverPacketHandlerSpec = @SidedPacketHandler(channels = {"CBCWeapons"}, packetHandler = CBCPacketHandler.class )
)
public class CBCDeathmatch
{
	
	@Instance("lcdm")
	public static CBCDeathmatch instance;
	
	@SidedProxy(clientSide="cbproject.deathmatch.proxy.ClientProxy",serverSide="cbproject.deathmatch.proxy.Proxy")
	public static cbproject.deathmatch.proxy.Proxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent Init)
	{
		DMRegister.preRegister();
	}

	
	@Init
	public void init(FMLInitializationEvent Init){
		DMItems.init(CBCMod.config);
		DMBlocks.init(CBCMod.config);
		proxy.init();
		DMRegister.register();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
}
