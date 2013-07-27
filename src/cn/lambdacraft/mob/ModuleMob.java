package cn.lambdacraft.mob;

import java.util.HashMap;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.mob.proxy.Proxy;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.core.utils.BlockPos;
import cn.lambdacraft.mob.blocks.tile.TileSentryRay;
import cn.lambdacraft.mob.entities.EntityAlienSlave;
import cn.lambdacraft.mob.entities.EntityBarnacle;
import cn.lambdacraft.mob.entities.EntityHLZombie;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.entities.EntityHoundeye;
import cn.lambdacraft.mob.entities.EntitySentry;
import cn.lambdacraft.mob.entities.EntityShockwave;
import cn.lambdacraft.mob.entities.EntitySnark;
import cn.lambdacraft.mob.entities.EntityVortigauntRay;
import cn.lambdacraft.mob.network.NetSentrySync;
import cn.lambdacraft.mob.register.CBCMobBlocks;
import cn.lambdacraft.mob.register.CBCMobItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = "LambdaCraft|Living", name = "LambdaCraft Living", version = CBCMod.VERSION, dependencies = CBCMod.DEPENDENCY_CORE)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModuleMob {

	/**
	 * 玩家对应当前选定的Sentry的映射表。
	 */
	public static HashMap<EntityPlayer, EntitySentry> syncMap = new HashMap();
	/**
	 * 放置时临时采用的映射表。
	 */
	public static HashMap<EntityPlayer, TileSentryRay> tileMap = new HashMap();
	
	/**
	 * 放置者对应方块位置的映射表。
	 */
	public static HashMap<BlockPos, EntityPlayer> playerMap = new HashMap();

	@SidedProxy(clientSide = "cn.lambdacraft.mob.proxy.ClientProxy", serverSide = "cn.lambdacraft.mob.proxy.Proxy")
	public static cn.lambdacraft.mob.proxy.Proxy proxy;

	@Instance("LambdaCraft|Living")
	public static ModuleMob instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent Init) {
		proxy.preInit();
	}

	@Init
	public void init(FMLInitializationEvent Init) {
		CBCMobItems.init(CBCMod.config);
		CBCMobBlocks.init();

		EntityRegistry.addSpawn(EntityHeadcrab.class, 7, 0, 50,
				EnumCreatureType.monster, genericGen);
		EntityRegistry.addSpawn(EntityHoundeye.class, 15, 0, 70,
				EnumCreatureType.monster, genericGen);
		EntityRegistry.addSpawn(EntityBarnacle.class, 10, 0, 65,
				EnumCreatureType.monster, barnacleGen);

		CBCNetHandler.addChannel(GeneralProps.NET_ID_SENTRYSYNCER, new NetSentrySync());
		EntityRegistry.registerModEntity(EntitySnark.class, "snark",
				GeneralProps.ENT_ID_SNARK, CBCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityHeadcrab.class, "headcrab",
				GeneralProps.ENT_ID_HEADCRAB, CBCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityBarnacle.class, "barnacle",
				GeneralProps.ENT_ID_BARNACLE, CBCMod.instance, 84, 5, false);
		EntityRegistry.registerModEntity(EntityHLZombie.class, "hlzombie",
				GeneralProps.ENT_ID_ZOMBIE, CBCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntitySentry.class, "turret",
				GeneralProps.ENT_ID_TURRET, CBCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityHoundeye.class, "houndeye",
				GeneralProps.ENT_ID_HOUNDEYE, CBCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityShockwave.class, "shockwave",
				GeneralProps.ENT_ID_SHOCKWAVE, CBCMod.instance, 32, 3, false);
		EntityRegistry.registerModEntity(EntityAlienSlave.class, "vortigaunt",
				GeneralProps.ENT_ID_VORTIGAUNT, CBCMod.instance, 48, 3, true);
		EntityRegistry.registerModEntity(EntityVortigauntRay.class, "vor_ray",
				GeneralProps.ENT_ID_VOR_RAY, CBCMod.instance, 32, 3, false);
		proxy.init();
	}

	private static BiomeGenBase genericGen[] = { BiomeGenBase.beach,
			BiomeGenBase.desert, BiomeGenBase.desertHills,
			BiomeGenBase.extremeHills, BiomeGenBase.forest,
			BiomeGenBase.forestHills, BiomeGenBase.frozenOcean,
			BiomeGenBase.frozenRiver, BiomeGenBase.jungle,
			BiomeGenBase.swampland, BiomeGenBase.plains }, barnacleGen[] = {
			BiomeGenBase.beach, BiomeGenBase.desert, BiomeGenBase.desertHills,
			BiomeGenBase.extremeHills, BiomeGenBase.forest,
			BiomeGenBase.forestHills, BiomeGenBase.frozenOcean,
			BiomeGenBase.frozenRiver, BiomeGenBase.jungle,
			BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.hell };

	@PostInit
	public void postInit(FMLPostInitializationEvent Init) {
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}

}