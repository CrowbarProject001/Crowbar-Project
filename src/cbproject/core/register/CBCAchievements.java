package cbproject.core.register;

import javax.swing.text.html.parser.Entity;

import cbproject.core.misc.Config;
import cbproject.crafting.CraftingHandler;

import com.google.common.base.CaseFormat;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

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