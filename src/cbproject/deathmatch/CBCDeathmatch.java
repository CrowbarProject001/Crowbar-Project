package cbproject.deathmatch;

import net.minecraft.command.CommandHandler;
import net.minecraft.creativetab.CreativeTabs;
import cbproject.core.CBCMod;
import cbproject.core.configure.Config;
import cbproject.core.misc.CBCBlocks;
import cbproject.core.misc.CBCCreativeTab;
import cbproject.core.misc.CBCGeneralRegistry;
import cbproject.core.register.CBCEventHandler;
import cbproject.core.register.CBCGuiHandler;
import cbproject.core.register.CBCItems;
import cbproject.core.register.CBCLanguage;
import cbproject.core.register.CBCPacketHandler;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.core.world.CBCOreGenerator;
import cbproject.crafting.recipes.RecipeWeapons;
import cbproject.deathmatch.register.DMBlocks;
import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.register.DMRegister;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid="lcdm",name="LambdaCraft DM Module",version="0.8.5.5a")
@NetworkMod(clientSideRequired=true,serverSideRequired=false,
serverPacketHandlerSpec = @SidedPacketHandler(channels = {"CBCWeapons"}, packetHandler = CBCPacketHandler.class ))
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
		DMBlocks.init(CBCMod.config);
		DMItems.init(CBCMod.config);
		DMRegister.register();
		proxy.init();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init){
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}
	
}
