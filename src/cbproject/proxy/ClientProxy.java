package cbproject.proxy;

import cbproject.CBCMod;
import cbproject.elements.renderers.CBCRenderManager;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy{
	
	public final static String GAUSS_BEAM_PATH = "/cbproject/textures/entities/gaussbeam.png";
	
	public final static String TRIPMINE_FRONT_PATH = "/cbproject/textures/blocks/tripmine_front.png";
	public final static String TRIPMINE_SIDE_PATH = "/cbproject/textures/blocks/tripmine_side.png";
	public final static String TRIPMINE_TOP_PATH = "/cbproject/textures/blocks/tripmine_top.png";
	public final static String TRIPMINE_RAY_PATH = "/cbproject/textures/blocks/tripmine_beam.png";
	
	public final static String SATCHEL_TOP_PATH = "/cbproject/textures/entities/satchel_top.png",
			SATCHEL_BOTTOM_PATH = "/cbproject/textures/entities/satchel_bottom.png", 
			SATCHEL_SIDE_PATH = "/cbproject/textures/entities/satchel_side.png",
			SATCHEL_SIDE2_PATH = "/cbproject/textures/entities/satchel_side2.png";
	
	public final static String AR_GRENADE_PATH = "/cbproject/textures/entities/argrenade.png",
			RPG_ROCKET_PATH = "/cbproject/textures/entities/rpg_rocket.png",
			SHOTGUN_SHELL_PATH = "/cbproject/textures/entities/shotgun_shell.png",
			EGON_BEAM_PATH = "/cbproject/textures/entities/egon_beam.png",
			HGRENADE_PATH = "/cbproject/textures/items/weapon_hgrenade.png";

	public final static String EGON_EQUIPPED_PATH = "/cbproject/textures/items/weapon_egon0.png";
	
	public final static String GUI_WEAPONCRAFTER_PATH = "/cbproject/textures/gui/crafter.png";
	
	public final static String ITEM_SATCHEL_PATH[] = {"/cbproject/textures/items/weapon_satchel1.png",
			"/cbproject/textures/item/weapon_satchel2.png"};
	public final static String CROSSBOW_SIDE_PATH[] = {
		"/cbproject/textures/items/crossbow_side0.png",
		"/cbproject/textures/items/crossbow_side1.png",
		"/cbproject/textures/items/crossbow_side2.png",
		"/cbproject/textures/items/crossbow_side3.png",
		"/cbproject/textures/items/crossbow_side4.png",
		"/cbproject/textures/items/crossbow_side5.png"},
			CROSSBOW_FRONT_PATH[] = {"/cbproject/textures/items/crossbow_front0.png",
		"/cbproject/textures/items/crossbow_front1.png"};
	
	public final static String HEV_ARMOR_PATH[] = {"/cbproject/textures/armor/hev_1.png",
		"/cbproject/textures/armor/hev_2.png"};
	@Override 
	public void init() { 
		
		CBCMod.renderManager = new CBCRenderManager();
		
		MinecraftForgeClient.preloadTexture(GAUSS_BEAM_PATH); 
		
		MinecraftForgeClient.preloadTexture(TRIPMINE_FRONT_PATH);
		MinecraftForgeClient.preloadTexture(TRIPMINE_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(TRIPMINE_TOP_PATH);
		MinecraftForgeClient.preloadTexture(TRIPMINE_RAY_PATH);
		
		MinecraftForgeClient.preloadTexture(SATCHEL_TOP_PATH);
		MinecraftForgeClient.preloadTexture(SATCHEL_BOTTOM_PATH);
		MinecraftForgeClient.preloadTexture(SATCHEL_SIDE_PATH);
		MinecraftForgeClient.preloadTexture(SATCHEL_SIDE2_PATH);
		
		MinecraftForgeClient.preloadTexture(AR_GRENADE_PATH );
		MinecraftForgeClient.preloadTexture(RPG_ROCKET_PATH);
		MinecraftForgeClient.preloadTexture(SHOTGUN_SHELL_PATH);
		MinecraftForgeClient.preloadTexture(EGON_BEAM_PATH);
		MinecraftForgeClient.preloadTexture(HGRENADE_PATH);
		
		MinecraftForgeClient.preloadTexture(EGON_EQUIPPED_PATH);
		
		for(String s:CROSSBOW_FRONT_PATH)
			MinecraftForgeClient.preloadTexture(s);
		for(String s:CROSSBOW_SIDE_PATH)
			MinecraftForgeClient.preloadTexture(s);
		for(String s:HEV_ARMOR_PATH)
			MinecraftForgeClient.preloadTexture(s);
		
	}
}
