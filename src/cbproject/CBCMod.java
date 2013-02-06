/**
 * 
 */
/**
 * @author Administrator
 *
 */
//This packet is the main frame of the mod.
package cbproject;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.src.BaseMod;

@Mod(modid="cbc",name="CrowbarCraft",version="0.0.0.1")
@NetworkMod(clientSideRequired=true,serverSideRequired=true)

public class CBCMod
{
	@Instance("cbc")
	public static CBCMod CBCMod;
	@SidedProxy(clientSide="cbproject.main.ClientProxy",serverSide="chproject.main.SideProxy")
	public static cbproject.main.Proxy Proxy;
	@Init
	public void init(FMLInitializationEvent Init){};
	
}