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
package cn.lambdacraft.mob.proxy;

import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.mob.blocks.tile.TileSentryRay;
import cn.lambdacraft.mob.client.models.ModelHLZombie;
import cn.lambdacraft.mob.client.models.ModelHeadcrab;
import cn.lambdacraft.mob.client.models.ModelHoundeye;
import cn.lambdacraft.mob.client.models.ModelSnark;
import cn.lambdacraft.mob.client.render.RenderBarnacle;
import cn.lambdacraft.mob.client.render.RenderHoundeye;
import cn.lambdacraft.mob.client.render.RenderSentryRay;
import cn.lambdacraft.mob.client.render.RenderShockwave;
import cn.lambdacraft.mob.client.render.RenderTurret;
import cn.lambdacraft.mob.client.render.RenderVortigauntRay;
import cn.lambdacraft.mob.entities.EntityBarnacle;
import cn.lambdacraft.mob.entities.EntityHLZombie;
import cn.lambdacraft.mob.entities.EntityHeadcrab;
import cn.lambdacraft.mob.entities.EntityHoundeye;
import cn.lambdacraft.mob.entities.EntitySentry;
import cn.lambdacraft.mob.entities.EntityShockwave;
import cn.lambdacraft.mob.entities.EntitySnark;
import cn.lambdacraft.mob.entities.EntityVortigauntRay;
import net.minecraft.client.renderer.entity.RenderLiving;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author WeAthFolD
 * 
 */
public class ClientProxy extends Proxy {

	@Override
	public void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntitySnark.class,
				new RenderLiving(new ModelSnark(), 0.2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeadcrab.class,
				new RenderLiving(new ModelHeadcrab(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBarnacle.class, new RenderBarnacle());
		RenderingRegistry.registerEntityRenderingHandler(EntityHLZombie.class, new RenderLiving(new ModelHLZombie(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHoundeye.class, new RenderHoundeye());
		RenderingRegistry.registerEntityRenderingHandler(EntitySentry.class, new RenderTurret());
		RenderingRegistry.registerEntityRenderingHandler(EntityShockwave.class, new RenderShockwave());
		RenderingRegistry.registerEntityRenderingHandler(EntityVortigauntRay.class, new RenderVortigauntRay());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSentryRay.class,
				new RenderSentryRay());
	}
	
	@Override
	public void preInit() {
		for (String s : SND_MOBS) {
			CBCSoundEvents.addSoundPath("cbc/mobs/" + s,
					"/mods/lambdacraft/sounds/mobs/" + s);
		}
		CBCSoundEvents.addSoundWithVariety("cbc/mobs/he_alert", "/mods/lambdacraft/sounds/houndeye/he_alert", 3);
		CBCSoundEvents.addSoundWithVariety("cbc/mobs/he_attack", "/mods/lambdacraft/sounds/houndeye/he_attack", 3);
		CBCSoundEvents.addSoundWithVariety("cbc/mobs/he_blast", "/mods/lambdacraft/sounds/houndeye/he_blast", 3);
		CBCSoundEvents.addSoundWithVariety("cbc/mobs/he_die", "/mods/lambdacraft/sounds/houndeye/he_die", 3);
		CBCSoundEvents.addSoundWithVariety("cbc/mobs/he_hunt", "/mods/lambdacraft/sounds/houndeye/he_hunt", 4);
		CBCSoundEvents.addSoundWithVariety("cbc/mobs/he_idle", "/mods/lambdacraft/sounds/houndeye/he_idle", 4);
		CBCSoundEvents.addSoundWithVariety("cbc/mobs/he_pain", "/mods/lambdacraft/sounds/houndeye/he_pain", 5);
	}
	
	public static final String SND_MOBS[] = { "sqk_blast", "sqk_hunta",
		"sqk_huntb", "sqk_huntc", "sqk_die", "sqk_deploy", "hc_attacka", "hc_attackb",
		"hc_attackc", "hc_idlea", "hc_idleb", "hc_idlec", "hc_idled", "hc_idlee",
		"hc_diea", "hc_dieb", "hc_paina", "hc_painb", "hc_painc",
		"bcl_tongue", "bcl_chewa", "bcl_chewb", "bcl_chewc", "bcl_alert", "bcl_bite", "zo_alerta",
		"zo_alertb", "zo_alertc", "zo_attacka", "zo_attackb", "zo_claw_missa", "zo_claw_missb", 
		"zo_claw_strikea", "zo_claw_strikeb", "zo_claw_strikec", "zo_idlea", "zo_idleb", "zo_idlec",
		"zo_paina", "zo_painb", "zo_moan_loopa", "zo_moan_loopb", "zo_moan_loopc", "zo_moan_loopd", "zo_diea", "zo_dieb", "zo_diec"};
	
}
