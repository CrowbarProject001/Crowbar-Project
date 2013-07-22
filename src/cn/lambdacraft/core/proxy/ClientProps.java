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

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.Configurable;
import cn.lambdacraft.core.register.GeneralRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

/**
 * 客户端的一些信息。（贴图和渲染器)
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class ClientProps {

	@Configurable(category = "graphics", key = "HUD_drawInLeftCorner", comment = "Draws the HEV hud in the left corner, like the Half-Life does.", defValue = "false")
	public static boolean HUD_drawInLeftCorner = false;
	
	public static final int RENDER_TYPE_TRIPMINE = RenderingRegistry
			.getNextAvailableRenderId();
	public static final int RENDER_TYPE_EMPTY = RenderingRegistry
			.getNextAvailableRenderId(), 
			RENDER_ID_XENPORTAL = RenderingRegistry
					.getNextAvailableRenderId();;

	public static final String GAUSS_BEAM_PATH = "/mods/lambdacraft/textures/entities/gaussbeam.png",
			TRIPMINE_FRONT_PATH = "/mods/lambdacraft/textures/blocks/tripmine_front.png",
			TRIPMINE_SIDE_PATH = "/mods/lambdacraft/textures/blocks/tripmine_side.png",
			TRIPMINE_TOP_PATH = "/mods/lambdacraft/textures/blocks/tripmine_top.png",
			TRIPMINE_RAY_PATH = "/mods/lambdacraft/textures/blocks/tripmine_beam.png",
			HEVCHARGER_MAIN = "/mods/lambdacraft/textures/blocks/ac_main.png",
			HEVCHARGER_SIDE = "/mods/lambdacraft/textures/blocks/ac_side.png",
			HEVCHARGER_TD = "/mods/lambdacraft/textures/blocks/ac_td.png",
			HEVCHARGER_BACK = "/mods/lambdacraft/textures/blocks/ac_back.png",
			SATCHEL_TOP_PATH = "/mods/lambdacraft/textures/entities/satchel_top.png",
			SATCHEL_BOTTOM_PATH = "/mods/lambdacraft/textures/entities/satchel_bottom.png",
			SATCHEL_SIDE_PATH = "/mods/lambdacraft/textures/entities/satchel_side.png",
			SATCHEL_SIDE2_PATH = "/mods/lambdacraft/textures/entities/satchel_side2.png",
			AR_GRENADE_PATH = "/mods/lambdacraft/textures/entities/argrenade.png",
			RPG_ROCKET_PATH = "/mods/lambdacraft/textures/entities/rpg_rocket.png",
			SHOTGUN_SHELL_PATH = "/mods/lambdacraft/textures/entities/shotgun_shell.png",
			CROSSBOW_BOW_PATH = "/mods/lambdacraft/textures/entities/steelbow.png",
			RED_DOT_PATH = "/mods/lambdacraft/textures/entities/reddot.png",
			GUI_ARMORCHARGER_PATH = "/mods/lambdacraft/textures/gui/armor_charger.png",
			GUI_WEAPONCRAFTER_PATH = "/mods/lambdacraft/textures/gui/crafter.png",
			GUI_MEDFILLER_PATH = "/mods/lambdacraft/textures/gui/medfiller.png",
			GUI_GENFIRE_PATH = "/mods/lambdacraft/textures/gui/genfire.png",
			GUI_GENSOLAR_PATH = "/mods/lambdacraft/textures/gui/gensolar.png",
			GUI_GENLAVA_PATH = "/mods/lambdacraft/textures/gui/genlava.png",
			GUI_HECHARGER_PATH = "/mods/lambdacraft/textures/gui/hecharger.png",
			GUI_BATBOX_PATH = "/mods/lambdacraft/textures/gui/batbox.png",
			GUI_ELCRAFTER_PATH = "/mods/lambdacraft/textures/gui/elcrafter.png",
			HORNET_TRAIL_PATH = "/mods/lambdacraft/textures/entities/ag_trail.png",
			BATTERY_PATH = "/mods/lambdacraft/textures/entities/battery.png",
			SQUEAK_MOB_PATH = "/mods/lambdacraft/textures/entities/squeak.png",
			WIRE_SIDE_PATH = "/mods/lambdacraft/textures/blocks/wire_side.png",
			WIRE_MAIN_PATH = "/mods/lambdacraft/textures/blocks/wire_main.png",
			WIRE_SIDE_PATH2 = "/mods/lambdacraft/textures/blocks/wire_side2.png",
			HECHARGER_MAIN_PATH = "/mods/lambdacraft/textures/blocks/hecharger_main.png",
			HECHARGER_SIDE_PATH = "/mods/lambdacraft/textures/blocks/hecharger_side.png",
			HECHARGER_TD_PATH = "/mods/lambdacraft/textures/blocks/hecharger_td.png",
			HECHARGER_BACK_PATH = "/mods/lambdacraft/textures/blocks/hecharger_back.png",
			AC_NOENERGY = "/mods/lambdacraft/textures/blocks/ac_noenergy.png",
			LONGJUMP_ARMOR_PATH = "/mods/lambdacraft/textures/armor/longjump.png",
			HOUNDEYE_PATH = "/mods/lambdacraft/textures/entities/houndeye.png",
			MEDKIT_ENT_PATH = "/mods/lambdacraft/textures/entities/medkit.png",
			GLOW_PATH = "/mods/lambdacraft/textures/entities/glow.png",
			HEV_MASK_PATH = "/mods/lambdacraft/textures/gui/hud_mask.png",
			HEV_HUD_PATH = "/mods/lambdacraft/textures/gui/hev_hud.png",
			HEADCRAB_MOB_PATH = "/mods/lambdacraft/textures/entities/headcrab.png",
			BARNACLE_PATH = "/mods/lambdacraft/textures/entities/barnacle.png",
			BARNACLE_TENTACLE_PATH = "/mods/lambdacraft/textures/entities/barnacle_tentacle.png",
			LIGHT_BALL_PATH = "/mods/lambdacraft/textures/entities/lightball.png",
			ZOMBIE_MOB_PATH = "/mods/lambdacraft/textures/entities/zombie.png",
			TURRET_PATH = "/mods/lambdacraft/textures/entities/turret.png",
			SHOCKWAVE_PATH = "/mods/lambdacraft/textures/entities/shockwave.png",
			VORTIGAUNT_PATH = "/mods/lambdacraft/textures/entities/vortigaunt.png",
			HLSPRAY_DIC_PATH = "/spray/",
			SS_SIDE_PATH[] = {"/mods/lambdacraft/textures/blocks/ss_side0.png", "/mods/lambdacraft/textures/blocks/ss_side1.png"},
			SS_MAIN_PATH[] = {"/mods/lambdacraft/textures/blocks/ss_0.png", "/mods/lambdacraft/textures/blocks/ss_1.png"},
			SPRY_PATH[] = { "/mods/lambdacraft/textures/sprays/spry0.png",
					"/mods/lambdacraft/textures/sprays/spry1.png" },
			ITEM_SATCHEL_PATH[] = {
					"/mods/lambdacraft/textures/items/weapon_satchel1.png",
					"/mods/lambdacraft/textures/items/weapon_satchel2.png" },
			CROSSBOW_FRONT_PATH[] = {
					"/mods/lambdacraft/textures/items/crossbow_front0.png",
					"/mods/lambdacraft/textures/items/crossbow_front1.png" },
			EGON_BEAM_PATH[] = {
					"/mods/lambdacraft/textures/entities/egon_beam.png",
					"/mods/lambdacraft/textures/entities/egon_ray2.png" },
			HEV_ARMOR_PATH[] = { "/mods/lambdacraft/textures/armor/hev_1.png",
					"/mods/lambdacraft/textures/armor/hev_2.png" },
			RPG_TRAIL_PATH[] = {
					"/mods/lambdacraft/textures/entities/rpg_trail.png",
					"/mods/lambdacraft/textures/entities/rpg_trail_tail.png" },
			VORTIGAUNT_RAY_PATH[] = { 
					"/mods/lambdacraft/textures/entities/ltn0.png",
					"/mods/lambdacraft/textures/entities/ltn1.png",
					"/mods/lambdacraft/textures/entities/ltn2.png"
					};

	/**
	 * 获取随机的一个火光贴图。
	 * 
	 * @return 贴图路径
	 */
	public static String getRandomMuzzleFlash() {
		int random = (int) (Math.random() * 7) + 1;
		String path = "/mods/lambdacraft/textures/muz/muz" + random + ".png";
		return path;
	}
	
	public static void loadProps(Config config) {
		GeneralRegistry.loadConfigurableClass(CBCMod.config, ClientProps.class);
	}

}
