/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.Property;

import com.google.common.base.Charsets;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.Configurable;
import cn.lambdacraft.core.register.GeneralRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

/**
 * 客户端的一些信息。（贴图和渲染器)
 * 
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class ClientProps {

	@Configurable(category = "graphics", key = "HUD_drawInLeftCorner", comment = "Draws the HEV hud in the left corner, like the Half-Life does.", defValue = "false")
	public static boolean HUD_drawInLeftCorner = false;
	
	@Configurable(category = "graphics", key = "alwaysCustomCrosshair", comment = "Always draw custom crosshair regardless of player wearing HEV or not.", defValue = "false")
	public static boolean alwaysCustomCrossHair = false;

	public static final int RENDER_TYPE_TRIPMINE = RenderingRegistry.getNextAvailableRenderId();
	public static final int RENDER_TYPE_EMPTY = RenderingRegistry.getNextAvailableRenderId(),
			RENDER_ID_XENPORTAL = RenderingRegistry.getNextAvailableRenderId();

	@Configurable(category = "graphics", key = "CrossHair_R", comment = "The R color value of your custom crosshair.", defValue = "255")
	public static int xHairR = 255;

	@Configurable(category = "graphics", key = "CrossHair_G", comment = "The G color value of your custom crosshair.", defValue = "255")
	public static int xHairG = 255;

	@Configurable(category = "graphics", key = "CrossHair_B", comment = "The B color value of your custom crosshair.", defValue = "255")
	public static int xHairB = 255;
	
	@Configurable(category = "graphics", key = "Spray_R", comment = "The R color value of your custom spray.", defValue = "255")
	public static int sprayR = 255;

	@Configurable(category = "graphics", key = "Spray_G", comment = "The G color value of your custom spray.", defValue = "255")
	public static int sprayG = 255;

	@Configurable(category = "graphics", key = "Spray_B", comment = "The B color value of your custom spray.", defValue = "255")
	public static int sprayB = 255;
	
	@Configurable(category = "graphics", key = "Spray_A", comment = "The alpha value of your custom spray.", defValue = "255")
	public static int sprayA = 255;

	@Configurable(category = "graphics", key = "Spray_ID", comment = "The id value of your custom spray, ranging from 0-9.", defValue = "0")
	private static int sprayID = 255;
	
	public static Properties crosshairProps;
	public static Properties sprayProps;
	public static final String NAMESPACE = "lambdacraft:";
	
	private static final Random RNG = new Random();
	

	public static final String GAUSS_BEAM_PATH = "lambdacraft:textures/entities/gaussbeam.png",
			TRIPMINE_FRONT_PATH = "lambdacraft:textures/blocks/tripmine_front.png",
			TRIPMINE_SIDE_PATH = "lambdacraft:textures/blocks/tripmine_side.png",
			TRIPMINE_TOP_PATH = "lambdacraft:textures/blocks/tripmine_top.png",
			TRIPMINE_RAY_PATH = "lambdacraft:textures/blocks/tripmine_beam.png",
			HEVCHARGER_MAIN = "lambdacraft:textures/blocks/ac_main.png",
			HEVCHARGER_SIDE = "lambdacraft:textures/blocks/ac_side.png",
			HEVCHARGER_TD = "lambdacraft:textures/blocks/ac_td.png",
			HEVCHARGER_BACK = "lambdacraft:textures/blocks/ac_back.png",
			SATCHEL_TOP_PATH = "lambdacraft:textures/entities/satchel_top.png",
			SATCHEL_BOTTOM_PATH = "lambdacraft:textures/entities/satchel_bottom.png",
			SATCHEL_SIDE_PATH = "lambdacraft:textures/entities/satchel_side.png",
			SATCHEL_SIDE2_PATH = "lambdacraft:textures/entities/satchel_side2.png",
			AR_GRENADE_PATH = "lambdacraft:textures/entities/grenade.png",
			RPG_ROCKET_PATH = "lambdacraft:textures/entities/rpgrocket.png",
			SHOTGUN_SHELL_PATH = "lambdacraft:textures/entities/shotgun_shell.png",
			CROSSBOW_BOW_PATH = "lambdacraft:textures/entities/steelbow.png",
			RED_DOT_PATH = "lambdacraft:textures/entities/reddot.png",
			GUI_ARMORCHARGER_PATH = "lambdacraft:textures/gui/armor_charger.png",
			GUI_WEAPONCRAFTER_PATH = "lambdacraft:textures/gui/crafter.png",
			GUI_MEDFILLER_PATH = "lambdacraft:textures/gui/medfiller.png",
			GUI_GENFIRE_PATH = "lambdacraft:textures/gui/genfire.png",
			GUI_GENSOLAR_PATH = "lambdacraft:textures/gui/gensolar.png",
			GUI_GENLAVA_PATH = "lambdacraft:textures/gui/genlava.png",
			GUI_HECHARGER_PATH = "lambdacraft:textures/gui/hecharger.png",
			GUI_BATBOX_PATH = "lambdacraft:textures/gui/batbox.png",
			GUI_ELCRAFTER_PATH = "lambdacraft:textures/gui/elcrafter.png",
			HORNET_TRAIL_PATH = "lambdacraft:textures/entities/ag_trail.png",
			BATTERY_PATH = "lambdacraft:textures/entities/battery.png",
			SQUEAK_MOB_PATH = "lambdacraft:textures/entities/squeak.png",
			WIRE_SIDE_PATH = "lambdacraft:textures/blocks/wire_side.png",
			WIRE_MAIN_PATH = "lambdacraft:textures/blocks/wire_main.png",
			WIRE_SIDE_PATH2 = "lambdacraft:textures/blocks/wire_side2.png",
			HECHARGER_MAIN_PATH = "lambdacraft:textures/blocks/hecharger_main.png",
			HECHARGER_SIDE_PATH = "lambdacraft:textures/blocks/hecharger_side.png",
			HECHARGER_TD_PATH = "lambdacraft:textures/blocks/hecharger_td.png",
			HECHARGER_BACK_PATH = "lambdacraft:textures/blocks/hecharger_back.png",
			AC_NOENERGY = "lambdacraft:textures/blocks/ac_noenergy.png",
			LONGJUMP_ARMOR_PATH = "lambdacraft:textures/armor/longjump.png",
			HOUNDEYE_PATH = "lambdacraft:textures/entities/houndeye.png",
			MEDKIT_ENT_PATH = "lambdacraft:textures/entities/medkit.png",
			GLOW_PATH = "lambdacraft:textures/entities/glow.png",
			HEV_MASK_PATH = "lambdacraft:textures/gui/hud_mask.png",
			HEV_HUD_PATH = "lambdacraft:textures/gui/hev_hud.png",
			HEADCRAB_MOB_PATH = "lambdacraft:textures/entities/headcrab.png",
			BARNACLE_PATH = "lambdacraft:textures/entities/barnacle.png",
			BARNACLE_TENTACLE_PATH = "lambdacraft:textures/entities/barnacle_tentacle.png",
			LIGHT_BALL_PATH = "lambdacraft:textures/entities/lightball.png",
			ZOMBIE_MOB_PATH = "lambdacraft:textures/entities/zombie.png",
			TURRET_PATH = "lambdacraft:textures/entities/turret.png",
			SHOCKWAVE_PATH = "lambdacraft:textures/entities/shockwave.png",
			VORTIGAUNT_PATH = "lambdacraft:textures/entities/vortigaunt.png",
			GAUSS_ITEM_PATH = "lambdacraft:textures/entities/gauss.png",
			HLSPRAY_DIC_PATH = "lambdacraft:spray/",
			SKYBOX_PATH = "lambdacraft:textures/sky/xen%s.png",
			AMETHYST_PATH = "lambdacraft:textures/blocks/amethyst_model.png", 
			XENLIGHT_PATH = "lambdacraft:textures/blocks/xenlight_model.png", 
			EGON_BEAM_PATH[] = {"lambdacraft:textures/entities/plasma0.png", 
		"lambdacraft:textures/entities/plasma1.png", 
		"lambdacraft:textures/entities/plasma2.png"},
			SS_SIDE_PATH[] = {
					"lambdacraft:textures/blocks/ss_side0.png",
					"lambdacraft:textures/blocks/ss_side1.png" },
			SS_MAIN_PATH[] = { "lambdacraft:textures/blocks/ss_0.png",
					"lambdacraft:textures/blocks/ss_1.png" },
			SPRY_PATH[] = { "lambdacraft:textures/sprays/spry0.png",
					"lambdacraft:textures/sprays/spry1.png" },
			ITEM_SATCHEL_PATH[] = {
					"lambdacraft:textures/items/weapon_satchel1.png",
					"lambdacraft:textures/items/weapon_satchel2.png" },
			CROSSBOW_FRONT_PATH[] = {
					"lambdacraft:textures/items/crossbow_front0.png",
					"lambdacraft:textures/items/crossbow_front1.png" },
			EGON_BEAM_PATH1 = "lambdacraft:textures/entities/egon_ray2.png",
			HEV_ARMOR_PATH[] = { "lambdacraft:textures/armor/hev_1.png",
					"lambdacraft:textures/armor/hev_2.png" },
			RPG_TRAIL_PATH[] = {
					"lambdacraft:textures/entities/rpg_trail.png",
					"lambdacraft:textures/entities/rpg_trail_tail.png" },
			VORTIGAUNT_RAY_PATH[] = {
					"lambdacraft:textures/entities/ltn0.png",
					"lambdacraft:textures/entities/ltn1.png",
					"lambdacraft:textures/entities/ltn2.png" };

	public static final String xhair_path = "lambdacraft:crosshairs/",
			DEFAULT_XHAIR_PATH = xhair_path + "xhair1.png";
	
	public static final String spry_path = "lambdacraft:spray/";

	public static String PORTAL_PATH[] = new String[10];
	static {
		for(int i = 0; i < 10; i++) {
			PORTAL_PATH[i] = "lambdacraft:textures/blocks/xen_portal" + (i + 1) + ".png";
		}
	}
	
	private static String mf = "lambdacraft:textures/muz/muz";
	public static final String MUZZLEFLASH[] = {
		mf + "1.png", mf + "2.png", mf + "3.png"
	};
	/**
	 * 获取随机的一个火光贴图。
	 * 
	 * @return 贴图路径
	 */
	public static String getRandomMuzzleFlash() {
		int random = (int) (RNG.nextFloat() * 7F) + 1;
		String path = "lambdacraft:textures/muz/muz" + random + ".png";
		return path;
	}

	public static void loadProps(Config config) {
		GeneralRegistry.loadConfigurableClass(CBCMod.config, ClientProps.class);
		
		crosshairProps = new Properties();
		final String absPath = "/assets/lambdacraft/";
		URL src = Minecraft.class.getResource(absPath + "crosshairs/crosshairs.properties");
		InputStream langStream = null;
		
		sprayProps = new Properties();
		URL src2 = Minecraft.class.getResource(absPath + "spray/sprays.properties");
		InputStream langStream2 = null;

		try {
			langStream = src.openStream();
			langStream2 = src2.openStream();
			crosshairProps.load(new InputStreamReader(langStream, Charsets.UTF_8));
			sprayProps.load(new InputStreamReader(langStream2, Charsets.UTF_8));
		} catch (IOException e) {
			CBCMod.log.log(Level.SEVERE,"Unable to load crossfire/spray props from file %s", src);
		} catch (NullPointerException e) {
			CBCMod.log.log(Level.SEVERE, "Unable to find crossfire/spray props file.");
		} finally {
			try {
				if (langStream != null) {
					langStream.close();
				}
			} catch (IOException ex) {
				// HUSH
			}
		}
	}

	public static String getCrosshairPath(String wpnName) {
		try {
			String s = crosshairProps.getProperty(wpnName);
			if (s == null)
				return null;
			return xhair_path + s + ".png";
		} catch (NullPointerException e) {
		}
		return null;
	}
	
	public static String getSprayPath(int id) {
		try {
			String s = sprayProps.getProperty(String.valueOf(id));
			if (s == null)
				return null;
			return spry_path + s;
		} catch (NullPointerException e) {
		}
		return null;
	}
	
	public static void setSprayId(int id) {
		Property prop;
		try {
			prop = CBCMod.config.getProperty("graphics", "Spray_ID", "0");
			prop.set(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sprayID = id;
	}
	
	public static void setSprayColor(int r, int g, int b, int a) {
		r = r > 255 ? 255 : (r < 0 ? 0 : r);
		g = g > 255 ? 255 : (g < 0 ? 0 : g);
		b = b > 255 ? 255 : (b < 0 ? 0 : b);
		a = a > 255 ? 255 : (a < 0 ? 0 : a);
		sprayR = r;
		sprayG = g;
		sprayB = b;
		sprayA = a;
		Property prop;
		try {
			prop = CBCMod.config.getProperty("graphics", "Spray_R", "255");
			prop.set(r);
			
			prop = CBCMod.config.getProperty("graphics", "Spray_G", "255");
			prop.set(g);
			
			prop = CBCMod.config.getProperty("graphics", "Spray_B", "255");
			prop.set(b);
			
			prop = CBCMod.config.getProperty("graphics", "Spray_A", "255");
			prop.set(a);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setCrosshairColor(int r, int g, int b) {
		r = r > 255 ? 255 : (r < 0 ? 0 : r);
		g = g > 255 ? 255 : (g < 0 ? 0 : g);
		b = b > 255 ? 255 : (b < 0 ? 0 : b);
		xHairR = r;
		xHairG = g;
		xHairB = b;
		Property prop;
		try {
			prop = CBCMod.config.getProperty("graphics", "CrossHair_R", "255");
			prop.set(r);
			
			prop = CBCMod.config.getProperty("graphics", "CrossHair_G", "255");
			prop.set(g);
			
			prop = CBCMod.config.getProperty("graphics", "CrossHair_B", "255");
			prop.set(b);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int getSprayId() {
		return sprayID > 9 ? 9 : (sprayID < 0 ? 0 : sprayID);
	}

}
