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
package cn.lambdacraft.core.props;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

/**
 * 客户端的一些信息。（贴图和渲染器)
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class ClientProps {

	public static final int RENDER_TYPE_TRIPMINE = RenderingRegistry
			.getNextAvailableRenderId();
	public static final int RENDER_TYPE_EMPTY = RenderingRegistry
			.getNextAvailableRenderId();

	public static final String GAUSS_BEAM_PATH = "/cn/lambdacraft/gfx/textures/entities/gaussbeam.png",
			TRIPMINE_FRONT_PATH = "/cn/lambdacraft/gfx/textures/blocks/tripmine_front.png",
			TRIPMINE_SIDE_PATH = "/cn/lambdacraft/gfx/textures/blocks/tripmine_side.png",
			TRIPMINE_TOP_PATH = "/cn/lambdacraft/gfx/textures/blocks/tripmine_top.png",
			TRIPMINE_RAY_PATH = "/cn/lambdacraft/gfx/textures/blocks/tripmine_beam.png",
			HEVCHARGER_MAIN = "/cn/lambdacraft/gfx/textures/blocks/ac_main.png",
			HEVCHARGER_SIDE = "/cn/lambdacraft/gfx/textures/blocks/ac_side.png",
			HEVCHARGER_TD = "/cn/lambdacraft/gfx/textures/blocks/ac_td.png",
			HEVCHARGER_BACK = "/cn/lambdacraft/gfx/textures/blocks/ac_back.png",
			SATCHEL_TOP_PATH = "/cn/lambdacraft/gfx/textures/entities/satchel_top.png",
			SATCHEL_BOTTOM_PATH = "/cn/lambdacraft/gfx/textures/entities/satchel_bottom.png",
			SATCHEL_SIDE_PATH = "/cn/lambdacraft/gfx/textures/entities/satchel_side.png",
			SATCHEL_SIDE2_PATH = "/cn/lambdacraft/gfx/textures/entities/satchel_side2.png",
			AR_GRENADE_PATH = "/cn/lambdacraft/gfx/textures/entities/argrenade.png",
			RPG_ROCKET_PATH = "/cn/lambdacraft/gfx/textures/entities/rpg_rocket.png",
			SHOTGUN_SHELL_PATH = "/cn/lambdacraft/gfx/textures/entities/shotgun_shell.png",
			CROSSBOW_BOW_PATH = "/cn/lambdacraft/gfx/textures/entities/steelbow.png",
			RED_DOT_PATH = "/cn/lambdacraft/gfx/textures/entities/reddot.png",
			GUI_ARMORCHARGER_PATH = "/cn/lambdacraft/gfx/textures/gui/armor_charger.png",
			GUI_WEAPONCRAFTER_PATH = "/cn/lambdacraft/gfx/textures/gui/crafter.png",
			GUI_MEDFILLER_PATH = "/cn/lambdacraft/gfx/textures/gui/medfiller.png",
			GUI_GENFIRE_PATH = "/cn/lambdacraft/gfx/textures/gui/genfire.png",
			GUI_GENSOLAR_PATH = "/cn/lambdacraft/gfx/textures/gui/gensolar.png",
			GUI_GENLAVA_PATH = "/cn/lambdacraft/gfx/textures/gui/genlava.png",
			GUI_HECHARGER_PATH = "/cn/lambdacraft/gfx/textures/gui/hecharger.png",
			GUI_BATBOX_PATH = "/cn/lambdacraft/gfx/textures/gui/batbox.png",
			GUI_ELCRAFTER_PATH = "/cn/lambdacraft/gfx/textures/gui/elcrafter.png",
			HORNET_TRAIL_PATH = "/cn/lambdacraft/gfx/textures/entities/ag_trail.png",
			BATTERY_PATH = "/cn/lambdacraft/gfx/textures/entities/battery.png",
			SQUEAK_MOB_PATH = "/cn/lambdacraft/gfx/textures/entities/squeak.png",
			WIRE_SIDE_PATH = "/mods/lambdacraft/textures/blocks/wire_side.png",
			WIRE_MAIN_PATH = "/mods/lambdacraft/textures/blocks/wire_main.png",
			WIRE_SIDE_PATH2 = "/mods/lambdacraft/textures/blocks/wire_side2.png",
			HECHARGER_MAIN_PATH = "/cn/lambdacraft/gfx/textures/blocks/hecharger_main.png",
			HECHARGER_SIDE_PATH = "/cn/lambdacraft/gfx/textures/blocks/hecharger_side.png",
			HECHARGER_TD_PATH = "/cn/lambdacraft/gfx/textures/blocks/hecharger_td.png",
			HECHARGER_BACK_PATH = "/cn/lambdacraft/gfx/textures/blocks/hecharger_back.png",
			LONGJUMP_ARMOR_PATH = "/cn/lambdacraft/gfx/textures/armor/longjump.png",
			MEDKIT_ENT_PATH = "/cn/lambdacraft/gfx/textures/entities/medkit.png",
			GLOW_PATH = "/cn/lambdacraft/gfx/textures/entities/glow.png",
			SPRY_PATH[] = { "/cn/lambdacraft/gfx/textures/sprays/spry0.png",
					"/cn/lambdacraft/gfx/textures/sprays/spry1.png" },
			ITEM_SATCHEL_PATH[] = {
					"/cn/lambdacraft/gfx/textures/items/weapon_satchel1.png",
					"/cn/lambdacraft/gfx/textures/items/weapon_satchel2.png" },
			CROSSBOW_FRONT_PATH[] = {
					"/cn/lambdacraft/gfx/textures/items/crossbow_front0.png",
					"/cn/lambdacraft/gfx/textures/items/crossbow_front1.png" },
			EGON_BEAM_PATH[] = {
					"/cn/lambdacraft/gfx/textures/entities/egon_beam.png",
					"/cn/lambdacraft/gfx/textures/entities/egon_ray2.png" },
			HEV_ARMOR_PATH[] = { "/cn/lambdacraft/gfx/textures/armor/hev_1.png",
					"/cn/lambdacraft/gfx/textures/armor/hev_2.png" },
			RPG_TRAIL_PATH[] = {
					"/cn/lambdacraft/gfx/textures/entities/rpg_trail.png",
					"/cn/lambdacraft/gfx/textures/entities/rpg_trail_tail.png" };

	/**
	 * 获取随机的一个火光贴图。
	 * 
	 * @return 贴图路径
	 */
	public static String getRandomMuzzleFlash() {
		int random = (int) (Math.random() * 7) + 1;
		String path = "/cn/lambdacraft/gfx/textures/muz/muz" + random + ".png";
		return path;
	}

}
