package cbproject.core.props;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProps {
	public static final int RENDER_TYPE_TRIPMINE = RenderingRegistry
			.getNextAvailableRenderId();;
	public static final int RENDER_TYPE_TRIPMINE_RAY = RenderingRegistry
			.getNextAvailableRenderId();;
	public static final int RENDER_TYPE_EMPTY = RenderingRegistry
			.getNextAvailableRenderId();

	public static final String GAUSS_BEAM_PATH = "/cbproject/textures/entities/gaussbeam.png";

	public static final String TRIPMINE_FRONT_PATH = "/cbproject/textures/blocks/tripmine_front.png";
	public static final String TRIPMINE_SIDE_PATH = "/cbproject/textures/blocks/tripmine_side.png";
	public static final String TRIPMINE_TOP_PATH = "/cbproject/textures/blocks/tripmine_top.png";
	public static final String TRIPMINE_RAY_PATH = "/cbproject/textures/blocks/tripmine_beam.png";

	public static final String SATCHEL_TOP_PATH = "/cbproject/textures/entities/satchel_top.png",
			SATCHEL_BOTTOM_PATH = "/cbproject/textures/entities/satchel_bottom.png",
			SATCHEL_SIDE_PATH = "/cbproject/textures/entities/satchel_side.png",
			SATCHEL_SIDE2_PATH = "/cbproject/textures/entities/satchel_side2.png";

	public static final String AR_GRENADE_PATH = "/cbproject/textures/entities/argrenade.png",
			RPG_ROCKET_PATH = "/cbproject/textures/entities/rpg_rocket.png",
			SHOTGUN_SHELL_PATH = "/cbproject/textures/entities/shotgun_shell.png",
			EGON_BEAM_PATH = "/cbproject/textures/entities/egon_beam.png",
			HGRENADE_PATH = "/cbproject/textures/items/weapon_hgrenade.png";

	public static final String EGON_EQUIPPED_PATH = "/cbproject/textures/items/weapon_egon0.png";

	public static final String GUI_WEAPONCRAFTER_PATH = "/cbproject/textures/gui/crafter.png";

	public static final String ITEM_SATCHEL_PATH[] = {
			"/cbproject/textures/items/weapon_satchel1.png",
			"/cbproject/textures/items/weapon_satchel2.png" };
	public static final String CROSSBOW_SIDE_PATH[] = {
			"/cbproject/textures/items/crossbow_side0.png",
			"/cbproject/textures/items/crossbow_side1.png",
			"/cbproject/textures/items/crossbow_side2.png",
			"/cbproject/textures/items/crossbow_side3.png",
			"/cbproject/textures/items/crossbow_side4.png",
			"/cbproject/textures/items/crossbow_side5.png" },
			CROSSBOW_FRONT_PATH[] = {
					"/cbproject/textures/items/crossbow_front0.png",
					"/cbproject/textures/items/crossbow_front1.png" };

	public static final String HEV_ARMOR_PATH[] = {
			"/cbproject/textures/armor/hev_1.png",
			"/cbproject/textures/armor/hev_2.png" };

}
