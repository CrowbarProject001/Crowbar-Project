package cn.lambdacraft.mob.register;

import net.minecraft.item.Item;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.mob.entities.EntityAlienSlave;
import cn.lambdacraft.mob.entities.EntityHLZombie;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.entities.EntityHoundeye;
import cn.lambdacraft.mob.entities.EntitySentry;
import cn.lambdacraft.mob.items.ItemBarnaclePlacer;
import cn.lambdacraft.mob.items.ItemDNAFragment;
import cn.lambdacraft.mob.items.ItemSentrySyncer;
import cn.lambdacraft.mob.items.LCMobSpawner;

public class CBCMobItems {

	public static Item weapon_snark, headcrab0w0, barnacle, zombie, turret, sentrySyncer, houndeye, vortigaunt;
	public static ItemDNAFragment dna;
	
	public static void init(Config conf) {
		weapon_snark = new LCMobSpawner(GeneralRegistry.getItemId("snark",
				1));
		headcrab0w0 = new LCMobSpawner(GeneralRegistry.getItemId("headcrab", 1), EntityHeadcrab.class, "headcrab").setIconName("egg5");
		barnacle = new ItemBarnaclePlacer(GeneralRegistry.getItemId("barnacle", 1)).setIconName("egg0");
		zombie = new LCMobSpawner(GeneralRegistry.getItemId("zombie", 1), EntityHLZombie.class, "hlzombie").setIconName("egg3");
		turret = new LCMobSpawner(GeneralRegistry.getItemId("turret", 1), EntitySentry.class, "turret");
		houndeye = new LCMobSpawner(GeneralRegistry.getItemId("houndeye", 1), EntityHoundeye.class, "houndeye").setIconName("egg1");
		vortigaunt = new LCMobSpawner(GeneralRegistry.getItemId("vortigaunt", 1), EntityAlienSlave.class, "vortigaunt").setIconName("egg4");
		sentrySyncer = new ItemSentrySyncer(GeneralRegistry.getItemId("syncer", 1));
		dna = new ItemDNAFragment(GeneralRegistry.getItemId("dna", 1));

	}

}
