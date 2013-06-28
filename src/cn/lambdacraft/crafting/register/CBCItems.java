package cn.lambdacraft.crafting.register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import cn.lambdacraft.api.energy.item.ICustomEnItem;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.core.misc.Config;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.register.GeneralRegistry;
import cn.lambdacraft.crafting.items.*;
import cn.lambdacraft.crafting.items.ItemMaterial.EnumMaterial;
import cn.lambdacraft.deathmatch.items.ArmorHEV;
import cn.lambdacraft.deathmatch.items.ArmorHEV.EnumAttachment;
import cn.lambdacraft.deathmatch.items.ItemBattery;
import cn.lambdacraft.deathmatch.items.ammos.*;
import cn.lambdacraft.deathmatch.register.DMBlocks;
import cn.lambdacraft.deathmatch.register.DMItems;

import net.minecraft.block.Block;
import net.minecraft.command.WrongUsageException;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * core和crafting模块中所有的物品注册。
 * 
 * @author WeAthFolD, mkpoli
 */
public class CBCItems {

	public static ItemAmmo ammo_uranium;
	public static Ammo_9mm ammo_9mm;
	public static Ammo_357 ammo_357;
	public static Ammo_9mm2 ammo_9mm2;
	public static Ammo_bow ammo_bow;
	public static Ammo_rpg ammo_rpg;
	public static ItemAmmo ammo_argrenade, ammo_shotgun;

	public static ItemBullet bullet_9mm, bullet_steelbow;

	public static CBCGenericItem ingotSteel;

	public static ItemMaterial materials;
	public static CBCGenericItem lambdaChip;
	public static SteelBar ironBar;
	public static IngotUranium ingotUranium;
	public static LCRecord halfLife01, halfLife02, halfLife03;
	public static ItemSpray spray1, spray2;
	public static Item tin, copper, chip;

	public static ItemBattery battery;

	/**
	 * 实际注册，请在Init中调用。
	 * 
	 * @param conf
	 */
	public static void init(Config conf) {

		ammo_uranium = new Ammo_uranium(GeneralRegistry.getItemId(
				"itemAmmo_uranium", 0));
		ammo_9mm = new Ammo_9mm(GeneralRegistry.getItemId("itemAmmo_9mm", 0));
		ammo_9mm2 = new Ammo_9mm2(GeneralRegistry.getItemId("itemAmmo_9mm2", 0));
		ammo_357 = new Ammo_357(GeneralRegistry.getItemId("itemAmmo_357", 0));
		ammo_bow = new Ammo_bow(GeneralRegistry.getItemId("itemAmmo_bow", 0));
		ammo_rpg = new Ammo_rpg(GeneralRegistry.getItemId("itemAmmo_RPG", 0));
		ammo_argrenade = new Ammo_argrenade(GeneralRegistry.getItemId(
				"itemAmmo_ARGrenade", 0));

		ammo_shotgun = new Ammo_shotgun(GeneralRegistry.getItemId(
				"itemBullet_Shotgun", 0));
		bullet_9mm = new Bullet_9mm(GeneralRegistry.getItemId("itemBullet_9mm",
				0));
		bullet_steelbow = new Bullet_steelbow(GeneralRegistry.getItemId(
				"steelbow", 0));

		materials = new ItemMaterial(GeneralRegistry.getItemId("mat_a", 0));

		ironBar = new SteelBar(GeneralRegistry.getItemId("ironBar", 0));
		lambdaChip = new CBCGenericItem(GeneralRegistry.getItemId("lambdachip",
				0)).setIAndU("lambdachip");
		;
		ingotUranium = new IngotUranium(GeneralRegistry.getItemId(
				"ingotUranium", 0));
		ingotSteel = new CBCGenericItem(GeneralRegistry.getItemId(
				"itemRefinedIronIngot", 0)).setIAndU("steel");

		halfLife01 = new LCRecord(GeneralRegistry.getItemId("halfLife01",
				GeneralProps.CAT_MISC), "hla", 0);
		halfLife02 = new LCRecord(GeneralRegistry.getItemId("halfLife02",
				GeneralProps.CAT_MISC), "hlb", 1);
		halfLife03 = new LCRecord(GeneralRegistry.getItemId("halfLife03",
				GeneralProps.CAT_MISC), "hlc", 2);

		spray1 = new ItemSpray(GeneralRegistry.getItemId("spray1",
				GeneralProps.CAT_MISC), 0);
		spray2 = new ItemSpray(GeneralRegistry.getItemId("spray2",
				GeneralProps.CAT_MISC), 1);

		tin = new CBCGenericItem(GeneralRegistry.getItemId("tin",
				GeneralProps.CAT_MISC)).setIAndU("tin");
		copper = new CBCGenericItem(GeneralRegistry.getItemId("copper",
				GeneralProps.CAT_MISC)).setIAndU("copper");
		chip = new CBCGenericItem(GeneralRegistry.getItemId("chip",
				GeneralProps.CAT_MISC)).setIAndU("chip");
		if(!CBCMod.ic2Installed)
			battery = new ItemBattery(GeneralRegistry.getItemId("battery", 3));
	}

	/**
	 * TODO:请务必进行一次完成的合成检查，恰当调整平衡性 添加基础合成表
	 */
	public static void addItemRecipes() {
		// Recipes

		OreDictionary.registerOre("oreUranium", CBCBlocks.uraniumOre);
		OreDictionary.registerOre("ingotUranium", CBCItems.ingotUranium);
		OreDictionary.registerOre("ingotRefinedIron", CBCItems.ingotSteel);
		OreDictionary.registerOre("blockRefinedIron", CBCBlocks.blockRefined);
		OreDictionary.registerOre("ingotTin", tin);
		OreDictionary.registerOre("ingotcopper", copper);

		ItemStack iSmaterials_1_0 = new ItemStack(CBCItems.materials, 1, 0), iSmaterials_1_1 = new ItemStack(
				CBCItems.materials, 1, 1), iSmaterials_1_2 = new ItemStack(
				CBCItems.materials, 1, 2), iSmaterials_1_3 = new ItemStack(
				CBCItems.materials, 1, 3), iSmaterials_1_4 = new ItemStack(
				CBCItems.materials, 1, 4), iSmaterials_1_5 = new ItemStack(
				CBCItems.materials, 1, 5), iSmaterials_1_6 = new ItemStack(
				CBCItems.materials, 1, 6), iSmaterials_1_7 = new ItemStack(
				CBCItems.materials, 1, 7), iSmaterials_1_8 = new ItemStack(
				CBCItems.materials, 1, 8), iSmaterials_1_9 = new ItemStack(
				CBCItems.materials, 1, 9), iSredstone = new ItemStack(
				Item.redstone), iSlightStoneDust = new ItemStack(
				Item.lightStoneDust), iStnt = new ItemStack(Block.tnt), iSblockLapis = new ItemStack(
				Block.blockLapis), iSdiamond = new ItemStack(Item.diamond), iSblazePowder = new ItemStack(
				Item.blazePowder), iSglass = new ItemStack(Block.glass), iSlambdaChip = new ItemStack(
				CBCItems.lambdaChip), iScoal = new ItemStack(Item.coal), iSgunpowder = new ItemStack(
				Item.gunpowder), iSingotGold = new ItemStack(Item.ingotGold), iSblockRedstone = new ItemStack(
				Block.blockRedstone), iSfurnace = new ItemStack(
				Block.furnaceIdle), iSchest = new ItemStack(Block.chest), iSweaponCrafter = new ItemStack(
				CBCBlocks.weaponCrafter), iSadvCrafter = new ItemStack(
				CBCBlocks.advCrafter), iSwire = new ItemStack(CBCBlocks.wire), iSemerald = new ItemStack(
				Item.emerald), iSchip = new ItemStack(chip), iSelectricCrafter = new ItemStack(
				CBCBlocks.elCrafter), iSbattery = new ItemStack(battery), iSgenFire = new ItemStack(
				CBCBlocks.genFire), iSstorageS = new ItemStack(
				CBCBlocks.storageS), iSstorageL = new ItemStack(
				CBCBlocks.storageL), iSgenLava = new ItemStack(
				CBCBlocks.genLava), iSbucketEmpty = new ItemStack(
				Item.bucketEmpty), iSgenSolar = new ItemStack(
				CBCBlocks.genSolar), iSnetherQuartz = new ItemStack(
				Item.netherQuartz), iSspray = new ItemStack(CBCItems.spray1), iSspray2 = new ItemStack(
				CBCItems.spray2), sfspieye = new ItemStack(
				Item.fermentedSpiderEye), srotten = new ItemStack(
				Item.rottenFlesh), smagma = new ItemStack(Item.magmaCream), sendereye = new ItemStack(
				Item.eyeOfEnder), smedkit = new ItemStack(DMItems.medkit);

		Object input[][] = {
				{ "ABA", "AAA", 'A', "ingotTin", 'B', iSglass },
				{ "ABA", "CCC", "DED", 'A', "ingotcopper", 'B', iSlambdaChip,
						'C', "ingotRefinedIron", 'D', iSglass, 'E',
						iSmaterials_1_0 },
				{ "ABA", "CCC", "DED", 'A', iScoal, 'B', iSlightStoneDust, 'C',
						"ingotcopper", 'D', iSglass, 'E', iSmaterials_1_0 },
				{ "ABA", "CBC", "DED", 'A', "ingotRefinedIron", 'B', iStnt,
						'C', iSgunpowder, 'D', iSglass, 'E', iSmaterials_1_0 },
				{ "ABA", "CCC", "DED", 'A', iSblazePowder, 'B', iSblockLapis,
						'C', "ingotRefinedIron", 'D', iSglass, 'E',
						iSmaterials_1_0 },
				{ "ABA", "CCC", "DED", 'A', iSingotGold, 'B', iSlightStoneDust,
						'C', "ingotRefinedIron", 'D', iSglass, 'E',
						iSmaterials_1_0 },
				{ "ABA", "CCC", "DED", 'A', "ingotcopper", 'B', iSredstone,
						'C', "ingotRefinedIron", 'D', iSglass, 'E',
						iSmaterials_1_0 },
				{ "ACA", "DFD", "EBE", 'A', srotten, 'B', iSmaterials_1_0, 'C',
						smagma, 'D', sfspieye, 'E', iSglass, 'F', sendereye },
				{ "ACA", "DFD", "EBE", 'A', iSlightStoneDust, 'B',
						iSmaterials_1_0, 'C', iSblockRedstone, 'D', iSdiamond,
						'E', iSglass, 'F', iSlambdaChip },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSblockRedstone, 'C',
						"blockRefinedIron", 'D', iSfurnace, 'E', iSchest },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSdiamond, 'C',
						iSweaponCrafter, 'D', "ingotcopper", 'E',
						"blockRefinedIron" },
				{ "ABA", "ABA", "ABA", 'A', Block.cloth, 'B', "ingotcopper" },
				{ "ABA", "CDC", "AEA", 'A', iSlightStoneDust, 'B', iSemerald,
						'C', iSdiamond, 'D', iSchip, 'E', iSglass },
				{ "ABA", "CDC", "EAE", 'A', iSglass, 'B', iSwire, 'C',
						iSlambdaChip, 'D', iSadvCrafter, 'E', iSlightStoneDust },
				{ "ABA", "ACA", "ACA", 'A', "ingotTin", 'B', iSwire, 'C',
						iSlightStoneDust },
				{ " A ", "BCB", "DED", 'A', iSstorageS, 'B', iSglass, 'C',
						"blockRefinedIron", 'D', iSblockLapis, 'E', iSfurnace },
				{ " A ", "BCB", "DED", 'A', iSglass, 'B', iSblazePowder, 'C',
						iSbucketEmpty, 'D', "ingotRefinedIron", 'E', iSgenFire },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', iSnetherQuartz, 'C',
						iSchip, 'D', "ingotRefinedIron", 'E', iSgenFire },
				{ "AAA", "BCB", "DED", 'A', iSglass, 'B', battery, 'C',
						iSredstone, 'D', iSchip, 'E', "blockRefinedIron" },
				{ " AB", "B B", "B B", 'A', iSredstone, 'B', "ingotTin" },
				{ " AB", "B B", "B B", 'A', iSredstone, 'B', "ingotcopper" },
				{ "AAA", "ABA", "CDC", 'A', iSgunpowder, 'B', iSredstone, 'C',
						iSglass, 'D', iSmaterials_1_0 },
				{ "ABA", "CDC", "ABA", 'A', iSwire, 'B', iSnetherQuartz, 'C',
						iSredstone, 'D', "ingotRefinedIron" },
				{ "ABA", "CDC", "AEA", 'A', iSglass, 'B', smedkit, 'C',
						iSlambdaChip, 'D', iSstorageS, 'E',
						"blockRefinedIron" },
				{ "AAA", "CDC", "BEB", 'A', iSglass, 'B', smedkit, 'C',
						iSlambdaChip, 'D', iSstorageS, 'E',
						"blockRefinedIron" },
				{ "AAA", "CDC", "BEB", 'A', iSglass, 'B', iSlambdaChip, 'C',
						battery, 'D', iSstorageL, 'E', "blockRefinedIron" },
				{ "AAA", "AAA", "AAA", 'A', "ingotRefinedIron" } ,
				{"A  ", "A  ", 'A', "ingotRefinedIron"} };

		ItemStack output[] = { materials.newStack(5, EnumMaterial.BOX),
				materials.newStack(2, EnumMaterial.ARMOR),
				materials.newStack(2, EnumMaterial.ACCESSORIES),
				materials.newStack(2, EnumMaterial.EXPLOSIVE),
				materials.newStack(1, EnumMaterial.HEAVY),
				materials.newStack(1, EnumMaterial.LIGHT),
				materials.newStack(1, EnumMaterial.PISTOL),
				materials.newStack(1, EnumMaterial.BIO),
				materials.newStack(1, EnumMaterial.TECH), iSweaponCrafter,
				iSadvCrafter, new ItemStack(CBCBlocks.wire, 6),
				new ItemStack(lambdaChip, 2), iSelectricCrafter, iSbattery,
				iSgenFire, iSgenLava, iSgenSolar, iSstorageS, iSspray,
				iSspray2, iSmaterials_1_1, iSchip,
				new ItemStack(DMBlocks.medkitFiller),
				new ItemStack(DMBlocks.healthCharger),
				new ItemStack(DMBlocks.armorCharger),
				new ItemStack(CBCBlocks.blockRefined),
				new ItemStack(CBCItems.ironBar, 5)};

		addOreRecipes(output, input);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife01), lambdaChip,
				Item.diamond);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife02), lambdaChip,
				Item.emerald);
		GameRegistry.addShapelessRecipe(new ItemStack(halfLife03), lambdaChip,
				Item.eyeOfEnder);
		GameRegistry.addShapelessRecipe(iSstorageL, CBCBlocks.storageS,
				lambdaChip);
		GameRegistry.addRecipe(new RecipeHEVAttachedShapeless(EnumAttachment.LONGJUMP, new ItemStack(DMItems.armorHEVChestplate),new ItemStack(DMItems.armorHEVChestplate), new ItemStack(DMItems.longjump)));
		GameRegistry.addRecipe(new RecipeRepair(spray1, new ItemStack(
				Item.dyePowder)));
		GameRegistry.addRecipe(new RecipeRepair(spray2, new ItemStack(
				Item.dyePowder)));

		ModLoader.addSmelting(Item.ingotIron.itemID, new ItemStack(
				ingotSteel.itemID, 1, 0));
		ModLoader.addSmelting(CBCBlocks.uraniumOre.blockID, new ItemStack(
				ingotUranium), 5);
		ModLoader.addSmelting(CBCBlocks.oreCopper.blockID,
				new ItemStack(copper), 2);
		ModLoader.addSmelting(CBCBlocks.oreTin.blockID, new ItemStack(tin), 2);

	}

	private static class RecipeElectricShapeless implements IRecipe {

		protected ArrayList<ItemStack> recipeItems = new ArrayList();
		protected ItemStack result, source;

		public RecipeElectricShapeless(ItemStack output, ItemStack original,
				ItemStack... add) {
			this.result = output;
			this.source = original;
			Collections.addAll(recipeItems, add);
		}

		/**
		 * Used to check if a recipe matches current crafting inventory
		 */
		@Override
		public boolean matches(InventoryCrafting par1InventoryCrafting,
				World par2World) {
			ArrayList arraylist = new ArrayList(this.recipeItems);
			arraylist.add(source);

			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					ItemStack itemstack = par1InventoryCrafting
							.getStackInRowAndColumn(j, i);

					if (itemstack != null) {
						boolean flag = false;
						Iterator iterator = arraylist.iterator();

						while (iterator.hasNext()) {
							ItemStack itemstack1 = (ItemStack) iterator.next();

							if (itemstack.itemID == itemstack1.itemID) {
								flag = true;
								arraylist.remove(itemstack1);
								break;
							}
						}

						if (!flag) {
							return false;
						}
					}
				}
			}

			return arraylist.isEmpty();
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			ItemStack is = result.copy();
			ItemStack in = findIdentifyStack(inv);
			if (in == null)
				return null;
			ICustomEnItem item = (ICustomEnItem) in.getItem();
			ICustomEnItem item2 = (ICustomEnItem) is.getItem();
			int energy = item.discharge(in, Integer.MAX_VALUE, 5, true, true);
			item2.charge(is, energy, 5, true, false);
			return is;
		}

		private ItemStack findIdentifyStack(InventoryCrafting inv) {
			ItemStack item = null;
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					ItemStack s = inv.getStackInRowAndColumn(i, j);
					if (s != null && s.itemID == source.itemID) {
						if (item != null)
							return null;
						item = s;
					}
				}
			return item;
		}

		@Override
		public int getRecipeSize() {
			return this.recipeItems.size() + 1;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return result;
		}

	}
	
	private static class RecipeHEVAttachedShapeless extends RecipeElectricShapeless {

		protected EnumAttachment theAttach;
		
		public RecipeHEVAttachedShapeless(EnumAttachment attach, ItemStack output, ItemStack original,
				ItemStack... add) {
			super(output, original, add);
			theAttach = attach;
		}
		
		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			ItemStack is = super.getCraftingResult(inv);
			if(is != null && is.getItem() instanceof ArmorHEV){
				ArmorHEV.addAttachTo(is, theAttach);
				return is;
			}
			return null;
		}
	}

	/**
	 * 使用一个物品来修复另一个物品，1stack数对应物品的1damage。
	 * 
	 * @author WeAthFolD
	 * 
	 */
	private static class RecipeRepair implements IRecipe {

		private Item itemToRepair;
		private ItemStack repairMat;

		public RecipeRepair(Item item, ItemStack mat) {
			itemToRepair = item;
			repairMat = mat;
		}

		@Override
		public boolean matches(InventoryCrafting inv, World world) {
			boolean b1 = false, b2 = false;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				if (s != null) {
					if (s.itemID == itemToRepair.itemID) {
						b1 = true;
					} else if (s.itemID == repairMat.itemID) {
						b2 = true;
					}
				}
			}
			return b1 && b2;
		}

		@Override
		public ItemStack getCraftingResult(InventoryCrafting inv) {
			int cntRep = 0;
			ItemStack theItem = null;
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack s = inv.getStackInSlot(i);
				if (s != null) {
					if (s.itemID == itemToRepair.itemID) {
						if (theItem != null) {
							System.out.println("more than 1 cntItem ");
							return null;
						}
						theItem = s;
					} else if (s.itemID == repairMat.itemID)
						++cntRep;
				}
			}
			if (theItem == null) {
				System.out.println("No");
				return null;
			}
			int damage = theItem.getItemDamage();
			damage -= cntRep;
			if (damage < 0)
				damage = 0;
			return new ItemStack(itemToRepair, 1, damage);
		}

		@Override
		public int getRecipeSize() {
			return 9;
		}

		@Override
		public ItemStack getRecipeOutput() {
			return null;
		}

	}

	private static void addOreRecipes(ItemStack[] output, Object[][] input) {
		if (output.length != input.length) {
			throw new WrongUsageException("Two par's size should be the same",
					input[0]);
		}
		for (int i = 0; i < output.length; i++) {
			ItemStack out = output[i];
			Object[] in = input[i];
			boolean flag = true;
			if (flag)
				GameRegistry.addRecipe(new ShapedOreRecipe(out, in));
		}
	}

	private static void addRecipes(ItemStack[] output, Object[][] input) {
		if (output.length != input.length) {
			throw new WrongUsageException("Two par's size should be the same",
					input[0]);
		}
		for (int i = 0; i < output.length; i++) {
			ItemStack out = output[i];
			Object[] in = input[i];
			boolean flag = true;
			if (flag)
				GameRegistry.addRecipe(out, in);
		}
	}

}
