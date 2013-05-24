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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.core.props;

import cbproject.core.misc.Config;

/**
 * Mod的一些固定信息。
 * @author WeAthFolD
 */
public class GeneralProps {
	
	public static Boolean ignoreBlockDestroy = false,
			doWeaponUplift = true;
	
	public static final int ENT_ID_GAUSS1 = 0, ENT_ID_EGON_RAY = 1, 
			ENT_ID_TRAIL = 2, ENT_ID_ARGRENADE = 3, ENT_ID_ARROW = 4,
			ENT_ID_HGRENADE = 5, ENT_ID_HORNET = 6, ENT_ID_ROCKET = 7,
			ENT_ID_DOT = 8, ENT_ID_SATCHEL = 9, ENT_ID_SNARK = 10, ENT_ID_BULLET = 11, ENT_ID_BOW = 12,
			ENT_ID_MEDKIT = 13, ENT_ID_BATTERY = 14, ENT_ID_GAUSS2 = 15;
	
	public static final String NET_CHANNEL_CLIENT = "CBCClient", NET_CHANNEL_SERVER = "CBCServer";
	
	public static final byte NET_ID_EXPLOSION = 0, NET_ID_DM = 1, NET_ID_CRAFTER_CL = 2, NET_ID_CHARGER_CL = 3,
			NET_ID_CHARGER_SV = 4, NET_ID_CRAFTER_SV = 5;
	
	public static final int GUI_ID_CRAFTER = 0, GUI_ID_CHARGER = 1;
	public static void loadProps(Config config){
		try {
			ignoreBlockDestroy = config.getBoolean("ignoreBlockDestroy", false);
			doWeaponUplift = config.getBoolean("weaponUplift", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
