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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开原协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.core.register;

import cbproject.core.misc.Config;
import cbproject.crafting.CraftingHandler;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

/**
 * Mod的成就类。
 * @author Mkpoli
 *
 */
public class CBCAchievements {

	/* Nuclear Raw Material */
	public static Achievement nuclearRawMaterial;

	/* Radioactive Beryl */
	public static Achievement radioactiveBeryl;

	/* Oh my teeth! */
	public static Achievement ohMyTeeth;

	/* The Page of Achs */
	public static AchievementPage achpage;
	
	/* Activer */
	public static CraftingHandler craftHandler;

	public static void init(Config conf) {
		try {

			nuclearRawMaterial = (new Achievement(conf.getInteger("nuclearRawMaterial",99), "nuclearRawMaterial", 0,
					0, CBCBlocks.blockUraniumOre, (Achievement) null))
					.registerAchievement();
			radioactiveBeryl = (new Achievement(conf.getInteger("radioactiveBeryl",100), "radioactiveBeryl", 0, 1,
					CBCItems.ingotUranium, nuclearRawMaterial))
					.registerAchievement();
			ohMyTeeth = (new Achievement(conf.getInteger("ohMyTeeth",101), "ohMyTeeth", 1, 0,
					CBCItems.ingotSteel, (Achievement) null))
					.registerAchievement();
			achpage = new AchievementPage("LambdaCraft", nuclearRawMaterial,
					radioactiveBeryl, ohMyTeeth);
			AchievementPage.registerAchievementPage(achpage);
			craftHandler = new CraftingHandler();
			GameRegistry.registerCraftingHandler(craftHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CBCAchievements() {

	}

	public static void getAchievement(EntityPlayer player, Achievement ach) {
		player.addStat(ach, 1);
	}
}