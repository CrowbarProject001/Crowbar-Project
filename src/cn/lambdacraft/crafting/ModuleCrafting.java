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
package cn.lambdacraft.crafting;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.proxy.Proxy;
import cn.lambdacraft.core.register.CBCAchievements;
import cn.lambdacraft.core.register.CBCGuiHandler;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.CBCSoundEvents;
import cn.lambdacraft.crafting.blocks.container.CRGuiElements;
import cn.lambdacraft.crafting.entities.EntitySpray;
import cn.lambdacraft.crafting.items.ItemMaterial.EnumMaterial;
import cn.lambdacraft.crafting.network.NetCrafterClient;
import cn.lambdacraft.crafting.recipes.RecipeCrafter;
import cn.lambdacraft.crafting.recipes.RecipeRepair;
import cn.lambdacraft.crafting.recipes.RecipeWeapons;
import cn.lambdacraft.crafting.register.CBCBlocks;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.crafting.world.CBCWorldGen;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.lambdacraft.mob.register.CBCMobItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
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
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Administrator
 * 
 */
@Mod(modid = "LambdaCraft|World", name = "LambdaCraft World", version = CBCMod.VERSION, dependencies = CBCMod.DEPENDENCY_CORE)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ModuleCrafting {
	
	@SidedProxy(clientSide = "cn.lambdacraft.crafting.proxy.ClientProxy", serverSide = "cn.lambdacraft.crafting.proxy.Proxy")
	public static cn.lambdacraft.crafting.proxy.Proxy proxy;

	@Instance("LambdaCraft|World")
	public static ModuleCrafting instance;

	@PreInit
	public void preInit(FMLPreInitializationEvent Init) {
		GameRegistry.registerWorldGenerator(new CBCWorldGen());
		if (Proxy.isRendering()) {
			for (String s : SND_ENTITIES) {
				CBCSoundEvents.addSoundPath("cbc/entities/" + s,
						"/mods/lambdacraft/sounds/entities/" + s);
			}
			MinecraftForge.EVENT_BUS.register(this);
		}
	}

	@Init
	public void init(FMLInitializationEvent Init) {
		CBCBlocks.init(CBCMod.config);
		CBCItems.init(CBCMod.config);
		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(LiquidDictionary.getOrCreateLiquid(CBCBlocks.radioActiveStill.getLocalizedName(),
				new LiquidStack(CBCBlocks.radioActiveStill.blockID,1000,0)), new ItemStack(CBCItems.bucketRadioactive.itemID,1, 0), new ItemStack(Item.bucketEmpty)));
		CBCAchievements.init(CBCMod.config);
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_CRAFTER,
				new CRGuiElements.ElementCrafter());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_GENFIRE,
				new CRGuiElements.ElementGenFire());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_GENLAVA,
				new CRGuiElements.ElementGenLava());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_GENSOLAR,
				new CRGuiElements.ElementGenSolar());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_BATBOX,
				new CRGuiElements.ElementBatbox());
		CBCGuiHandler.addGuiElement(GeneralProps.GUI_ID_EL_CRAFTER,
				new CRGuiElements.ElementElCrafter());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_CRAFTER_CL,
				new NetCrafterClient());
		EntityRegistry.registerModEntity(EntitySpray.class, "entityart", GeneralProps.ENT_ID_ART,
				CBCMod.instance, 250, 5, true);

		proxy.init();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent Init) {

		CBCItems.addItemRecipes();

		// TODO:在这里添加武器合成机的合成表
		// 普通武器合成机：十字弩以下级别的所有武器和弹药
		// 高级武器合成机：十字弩以上级别的所有武器和弹药
		// 以上两个都会自动加入电力合成机的列表中
		// 电力武器合成机只需特别加入盔甲和装备（目前只有medkit）的合成即可
		// 现在的热量和各种信息基本过时，建议完整重写一遍
		String description[] = { "crafter.weapon", "crafter.ammo" };
		String ecDescription[] = { "crafter.equipments" };
		RecipeWeapons.InitializeRecipes(2, description);
		RecipeWeapons.initliazeECRecipes(1, ecDescription);

		// 这个是普通合成机的武器合成表
		RecipeCrafter wpnRecipes[] = {
				new RecipeCrafter(new ItemStack(DMItems.weapon_crowbar), 700,new ItemStack(CBCItems.ironBar, 2),CBCItems.materials.newStack(1, EnumMaterial.ACCESSORIES),new ItemStack(Item.dyePowder, 1, 1)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_9mmhandgun),1200, CBCItems.materials.newStack(2,EnumMaterial.PISTOL)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_357), 1400,CBCItems.materials.newStack(3, EnumMaterial.PISTOL),CBCItems.materials.newStack(2, EnumMaterial.ACCESSORIES)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_9mmAR), 2700,CBCItems.materials.newStack(3, EnumMaterial.LIGHT),CBCItems.materials.newStack(1, EnumMaterial.ACCESSORIES)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_shotgun), 3000,CBCItems.materials.newStack(5, EnumMaterial.LIGHT),CBCItems.materials.newStack(3, EnumMaterial.ACCESSORIES)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_hgrenade, 10),1600,CBCItems.materials.newStack(2, EnumMaterial.LIGHT),CBCItems.materials.newStack(4, EnumMaterial.EXPLOSIVE)), }, 
					ammoRecipes[] = {
				new RecipeCrafter(new ItemStack(CBCItems.bullet_9mm, 18), 600,CBCItems.materials.newStack(3, EnumMaterial.AMMUNITION)),new RecipeCrafter(new ItemStack(CBCItems.ammo_9mm), 700, CBCItems.materials.newStack(2, EnumMaterial.PISTOL), CBCItems.materials.newStack(1, EnumMaterial.ACCESSORIES)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_357, 12), 700,CBCItems.materials.newStack(2, EnumMaterial.ACCESSORIES),CBCItems.materials.newStack(3, EnumMaterial.AMMUNITION)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_shotgun, 8),850,CBCItems.materials.newStack(4, EnumMaterial.AMMUNITION),CBCItems.materials.newStack(1, EnumMaterial.ACCESSORIES)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_9mm2, 1),1050,CBCItems.materials.newStack(5, EnumMaterial.AMMUNITION),CBCItems.materials.newStack(1, EnumMaterial.LIGHT)),
				new RecipeRepair(CBCItems.ammo_9mm, CBCItems.bullet_9mm),
				new RecipeRepair(CBCItems.ammo_9mm2, CBCItems.bullet_9mm) }, 
				advWeapons[] = {
				new RecipeCrafter(new ItemStack(DMBlocks.blockTripmine, 15),3200,CBCItems.materials.newStack(3, EnumMaterial.LIGHT),CBCItems.materials.newStack(1, EnumMaterial.TECH),CBCItems.materials.newStack(6, EnumMaterial.EXPLOSIVE)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_satchel, 15),3500,
						CBCItems.materials.newStack(3, EnumMaterial.LIGHT),
						CBCItems.materials.newStack(1, EnumMaterial.TECH),
						CBCItems.materials.newStack(6, EnumMaterial.EXPLOSIVE)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_gauss), 4800,
						CBCItems.materials.newStack(8, EnumMaterial.LIGHT),
						CBCItems.materials.newStack(3, EnumMaterial.TECH),
						new ItemStack(Block.glass, 5)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_egon), 5600,
						CBCItems.materials.newStack(5, EnumMaterial.HEAVY),
						CBCItems.materials
								.newStack(3, EnumMaterial.ACCESSORIES),
						CBCItems.materials.newStack(4, EnumMaterial.TECH)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_crossbow), 3000,
						CBCItems.materials.newStack(6, EnumMaterial.LIGHT),
						CBCItems.materials
								.newStack(3, EnumMaterial.ACCESSORIES),
						new ItemStack(CBCItems.ironBar, 2)),
				new RecipeCrafter(new ItemStack(DMItems.weapon_hornet), 3000,
						CBCItems.materials.newStack(4, EnumMaterial.BIO),
						CBCItems.materials
								.newStack(3, EnumMaterial.ACCESSORIES)),
				new RecipeCrafter(new ItemStack(CBCMobItems.weapon_snark, 5),0, CBCItems.materials.newStack(1, EnumMaterial.BIO)) },
		// 高级武器合成机 弹药合成表
		advAmmos[] = {
				new RecipeCrafter(new ItemStack(CBCItems.bullet_steelbow, 10),650, new ItemStack(CBCItems.ironBar, 10),
						CBCItems.materials.newStack(1, EnumMaterial.EXPLOSIVE)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_bow, 1), 950,CBCItems.materials.newStack(3, EnumMaterial.AMMUNITION)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_argrenade, 5),
						600,CBCItems.materials.newStack(1, EnumMaterial.LIGHT),CBCItems.materials.newStack(2, EnumMaterial.EXPLOSIVE)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_rpg, 6), 1500,CBCItems.materials.newStack(1, EnumMaterial.HEAVY),CBCItems.materials.newStack(3, EnumMaterial.EXPLOSIVE)),
				new RecipeCrafter(new ItemStack(CBCItems.ammo_uranium, 1),
						1500, CBCItems.materials.newStack(1, EnumMaterial.BOX),new ItemStack(CBCItems.ingotUranium, 3)),
				new RecipeRepair(CBCItems.ammo_bow, CBCItems.bullet_steelbow),
				new RecipeRepair(CBCItems.ammo_uranium, CBCItems.ingotUranium,33) },
		// 电力合成机独有 装备合成表
		armors[] = {
				new RecipeCrafter(new ItemStack(DMItems.armorHEVBoot), 6000,
						CBCItems.materials.newStack(3, EnumMaterial.TECH),
						CBCItems.materials.newStack(6, EnumMaterial.LIGHT),
						CBCItems.materials.newStack(3, EnumMaterial.ARMOR)),
				new RecipeCrafter(new ItemStack(DMItems.armorHEVLeggings),6000,
						CBCItems.materials.newStack(3, EnumMaterial.TECH),
						CBCItems.materials.newStack(6, EnumMaterial.LIGHT),
						CBCItems.materials.newStack(3, EnumMaterial.ARMOR)),
				new RecipeCrafter(new ItemStack(DMItems.armorHEVChestplate),7000,
						CBCItems.materials.newStack(4, EnumMaterial.TECH),
						CBCItems.materials.newStack(7, EnumMaterial.LIGHT),
						CBCItems.materials.newStack(4, EnumMaterial.ARMOR)),
				new RecipeCrafter(new ItemStack(DMItems.armorHEVHelmet), 6000,
						CBCItems.materials.newStack(2, EnumMaterial.TECH),
						CBCItems.materials.newStack(4, EnumMaterial.LIGHT),
						CBCItems.materials.newStack(2, EnumMaterial.ARMOR)),
				new RecipeCrafter(new ItemStack(DMItems.longjump), 3000,
						CBCItems.materials.newStack(2, EnumMaterial.TECH),
						CBCItems.materials.newStack(2, EnumMaterial.ARMOR)),
				new RecipeCrafter(new ItemStack(DMItems.medkit, 3), 2000,
						CBCItems.materials
								.newStack(2, EnumMaterial.ACCESSORIES),
						CBCItems.materials.newStack(2, EnumMaterial.LIGHT),
						new ItemStack(CBCItems.lambdaChip, 1)), };

		RecipeWeapons.addNormalRecipe(0, wpnRecipes);
		RecipeWeapons.addNormalRecipe(1, ammoRecipes);
		RecipeWeapons.addAdvancedRecipe(0, advWeapons);
		RecipeWeapons.addAdvancedRecipe(1, advAmmos);
		RecipeWeapons.addECSpecificRecipe(2, armors);

		RecipeWeapons.close();
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
	}

	public static final String SND_ENTITIES[] = { "sprayer" };

}
