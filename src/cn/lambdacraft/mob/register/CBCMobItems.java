package cn.lambdacraft.mob.register;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.mob.entities.EntityHLZombie;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.entities.EntitySentry;
import cn.lambdacraft.mob.items.ItemBarnaclePlacer;
import cn.lambdacraft.mob.items.ItemSentrySyncer;
import cn.lambdacraft.mob.items.LCMobSpawner;
import net.minecraft.item.Item;

public class CBCMobItems {

	public static Item weapon_snark, headcrab0w0, barnacle, zombie, turret, sentrySyncer;

	public static void init(Config conf) {
		weapon_snark = new LCMobSpawner(GeneralRegistry.getItemId("snark",
				1));
		headcrab0w0 = new LCMobSpawner(GeneralRegistry.getItemId("headcrab", 1), EntityHeadcrab.class, "headcrab");
		barnacle = new ItemBarnaclePlacer(GeneralRegistry.getItemId("barnacle", 1));
		zombie = new LCMobSpawner(GeneralRegistry.getItemId("zombie", 1), EntityHLZombie.class, "zombie");
		turret = new LCMobSpawner(GeneralRegistry.getItemId("turret", 1), EntitySentry.class, "turret");
		sentrySyncer = new ItemSentrySyncer(GeneralRegistry.getItemId("syncer", 1));
		
		LanguageRegistry.addName(headcrab0w0, "Headcrab");
		LanguageRegistry.addName(barnacle, "Barnacle");
		LanguageRegistry.addName(zombie, "Zombie");
		LanguageRegistry.addName(turret, "Turret");
		LanguageRegistry.addName(sentrySyncer, "Sentry Syncer");
	}

}
