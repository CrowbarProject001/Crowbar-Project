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
package cbproject.core.props;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;

/**
 * 客户端的一些信息。（贴图和渲染器）
 * 
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class ClientProps {

	public static final int RENDER_TYPE_TRIPMINE = RenderingRegistry
			.getNextAvailableRenderId();
	public static final int RENDER_TYPE_EMPTY = RenderingRegistry
			.getNextAvailableRenderId();

	public static final String GAUSS_BEAM_PATH = "/cbproject/gfx/textures/entities/gaussbeam.png",
			TRIPMINE_FRONT_PATH = "/cbproject/gfx/textures/blocks/tripmine_front.png",
			TRIPMINE_SIDE_PATH = "/cbproject/gfx/textures/blocks/tripmine_side.png",
			TRIPMINE_TOP_PATH = "/cbproject/gfx/textures/blocks/tripmine_top.png",
			TRIPMINE_RAY_PATH = "/cbproject/gfx/textures/blocks/tripmine_beam.png",
			HEVCHARGER_MAIN = "/cbproject/gfx/textures/blocks/ac_main.png",
			HEVCHARGER_SIDE = "/cbproject/gfx/textures/blocks/ac_side.png",
			HEVCHARGER_TD = "/cbproject/gfx/textures/blocks/ac_td.png",
			HEVCHARGER_BACK = "/cbproject/gfx/textures/blocks/ac_back.png",
			SATCHEL_TOP_PATH = "/cbproject/gfx/textures/entities/satchel_top.png",
			SATCHEL_BOTTOM_PATH = "/cbproject/gfx/textures/entities/satchel_bottom.png",
			SATCHEL_SIDE_PATH = "/cbproject/gfx/textures/entities/satchel_side.png",
			SATCHEL_SIDE2_PATH = "/cbproject/gfx/textures/entities/satchel_side2.png",
			AR_GRENADE_PATH = "/cbproject/gfx/textures/entities/argrenade.png",
			RPG_ROCKET_PATH = "/cbproject/gfx/textures/entities/rpg_rocket.png",
			SHOTGUN_SHELL_PATH = "/cbproject/gfx/textures/entities/shotgun_shell.png",
			CROSSBOW_BOW_PATH = "/cbproject/gfx/textures/entities/steelbow.png",
			RED_DOT_PATH = "/cbproject/gfx/textures/entities/reddot.png",
			EGON_EQUIPPED_PATH = "/cbproject/gfx/textures/items/weapon_egon0.png",
			GUI_ARMORCHARGER_PATH = "/cbproject/gfx/textures/gui/armor_charger.png",
			GUI_WEAPONCRAFTER_PATH = "/cbproject/gfx/textures/gui/crafter.png",
			GUI_MEDFILLER_PATH = "/cbproject/gfx/textures/gui/medfiller.png",
			HORNET_TRAIL_PATH = "/cbproject/gfx/textures/entities/ag_trail.png",
			BATTERY_PATH = "/cbproject/gfx/textures/entities/battery.png",
			SQUEAK_MOB_PATH = "/cbproject/gfx/textures/entities/squeak.png",
			WIRE_SIDE_PATH = "/cbproject/gfx/textures/blocks/tripmine_beam.png",
			WIRE_MAIN_PATH = "/cbproject/gfx/textures/blocks/tripmine_beam.png",
			SPRY_PATH[] = {
					"/cbproject/gfx/textures/sprays/spry0.png",
					"/cbproject/gfx/textures/sprays/spry1.png"},
			ITEM_SATCHEL_PATH[] = {
					"/cbproject/gfx/textures/items/weapon_satchel1.png",
					"/cbproject/gfx/textures/items/weapon_satchel2.png" },
			CROSSBOW_SIDE_PATH[] = {
					"/cbproject/gfx/textures/items/crossbow_side0.png",
					"/cbproject/gfx/textures/items/crossbow_side1.png",
					"/cbproject/gfx/textures/items/crossbow_side2.png",
					"/cbproject/gfx/textures/items/crossbow_side3.png",
					"/cbproject/gfx/textures/items/crossbow_side4.png",
					"/cbproject/gfx/textures/items/crossbow_side5.png" },
			CROSSBOW_FRONT_PATH[] = {
					"/cbproject/gfx/textures/items/crossbow_front0.png",
					"/cbproject/gfx/textures/items/crossbow_front1.png" },
			EGON_BEAM_PATH[] = {
					"/cbproject/gfx/textures/entities/egon_beam.png",
					"/cbproject/gfx/textures/entities/egon_ray2.png" },
			HEV_ARMOR_PATH[] = { "/cbproject/gfx/textures/armor/hev_1.png",
					"/cbproject/gfx/textures/armor/hev_2.png" },
			RPG_TRAIL_PATH[] = {
					"/cbproject/gfx/textures/entities/rpg_trail.png",
					"/cbproject/gfx/textures/entities/rpg_trail_tail.png" };

	/**
	 * 获取随机的一个火光贴图。
	 * 
	 * @return 贴图路径
	 */
	public static String getRandomMuzzleFlash() {
		int random = (int) (Math.random() * 7) + 1;
		String path = "/cbproject/gfx/textures/muz/muz" + random + ".png";
		return path;
	}

}
