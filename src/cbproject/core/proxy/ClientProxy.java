package cbproject.core.proxy;

import cbproject.core.CBCMod;
import cbproject.core.register.CBCRenderManager;
import cbproject.core.props.*;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy{

	@Override 
	public void init() { 
		
		CBCMod.renderManager = new CBCRenderManager();
		
		MinecraftForgeClient.preloadTexture(ClientProps.GAUSS_BEAM_PATH); 
		
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_FRONT_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_TOP_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.TRIPMINE_RAY_PATH);
		
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_TOP_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_BOTTOM_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SATCHEL_SIDE2_PATH);
		
		MinecraftForgeClient.preloadTexture(ClientProps.AR_GRENADE_PATH );
		MinecraftForgeClient.preloadTexture(ClientProps.RPG_ROCKET_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.SHOTGUN_SHELL_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.EGON_BEAM_PATH);
		MinecraftForgeClient.preloadTexture(ClientProps.HGRENADE_PATH);
		
		MinecraftForgeClient.preloadTexture(ClientProps.EGON_EQUIPPED_PATH);
		
		for(String s:ClientProps.CROSSBOW_FRONT_PATH)
			MinecraftForgeClient.preloadTexture(s);
		for(String s:ClientProps.CROSSBOW_SIDE_PATH)
			MinecraftForgeClient.preloadTexture(s);
		for(String s:ClientProps.HEV_ARMOR_PATH)
			MinecraftForgeClient.preloadTexture(s);
		
	}
	
}
