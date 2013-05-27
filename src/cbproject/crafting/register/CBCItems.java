package cbproject.crafting.register;

import java.io.File;

import cbproject.core.misc.Config;
import cbproject.crafting.items.*;
import cbproject.crafting.items.ItemMaterial.EnumMaterial;
import cbproject.deathmatch.items.ammos.*;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.WrongUsageException;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * core和crafting模块中所有的物品注册。
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
	
	public static ItemBullet bullet_9mm,bullet_steelbow;

	public static IngotSteel ingotSteel;
	
	public static ItemMaterial mat_accessories, mat_ammunition, mat_bio,
		mat_heavy, mat_light, mat_pistol, mat_tech, mat_explosive, mat_box;
	public static LambdaChipset lambdaChip;
	public static SteelBar ironBar;
	public static IngotUranium ingotUranium;
	public static LCRecord halfLife01, halfLife02, halfLife03;
	
	/**
	 * 实际注册，请在Init中调用。
	 * @param conf
	 */
	public static void init(Config conf){
		
		try{

			ammo_uranium = new Ammo_uranium(conf.GetItemID("itemAmmo_uranium", 7300));
			ammo_9mm = new Ammo_9mm(conf.GetItemID("itemAmmo_9mm", 7301));
			ammo_9mm2 = new Ammo_9mm2(conf.GetItemID("itemAmmo_9mm2", 7302));
			ammo_357 = new Ammo_357(conf.GetItemID("itemAmmo_357", 7303));
			ammo_bow = new Ammo_bow(conf.GetItemID("itemAmmo_bow", 7304));
			ammo_rpg = new Ammo_rpg(conf.GetItemID("itemAmmo_RPG", 7305));
			ammo_argrenade = new Ammo_argrenade(conf.GetItemID("itemAmmo_ARGrenade", 7306));
			
			ammo_shotgun = new Ammo_shotgun(conf.GetItemID("itemBullet_Shotgun", 7350));
			bullet_9mm = new Bullet_9mm(conf.GetItemID("itemBullet_9mm", 7351));
			bullet_steelbow = new Bullet_steelbow(conf.GetItemID("steelbow", 8062));
	
			mat_accessories = new ItemMaterial(conf.GetItemID("mat_a", 8050), EnumMaterial.ACCESSORIES);
			mat_ammunition = new ItemMaterial(conf.GetItemID("mat_b", 8051), EnumMaterial.AMMUNITION);
			mat_bio = new ItemMaterial(conf.GetItemID("mat_c", 8052), EnumMaterial.BIO);
			mat_heavy = new ItemMaterial(conf.GetItemID("mat_d", 8053), EnumMaterial.HEAVY);
			mat_light = new ItemMaterial(conf.GetItemID("mat_e", 8054), EnumMaterial.LIGHT);
			mat_pistol = new ItemMaterial(conf.GetItemID("mat_f", 8055), EnumMaterial.PISTOL);
			mat_tech = new ItemMaterial(conf.GetItemID("mat_g", 8056), EnumMaterial.TECH);
			mat_explosive = new ItemMaterial(conf.GetItemID("mat_h", 8057), EnumMaterial.EXPLOSIVE);
			mat_box = new ItemMaterial(conf.GetItemID("mat_0", 8058), EnumMaterial.BOX);
			
			ironBar = new SteelBar(conf.GetItemID("ironBar", 8059));
			lambdaChip = new LambdaChipset(conf.GetItemID("lambdachip", 8060));
			ingotUranium = new IngotUranium(conf.GetItemID("ingotUranium", 8061));
			ingotSteel = new IngotSteel(conf.GetItemID("itemRefinedIronIngot",7100));
			
			halfLife01 = new LCRecord(conf.GetItemID("halfLife01", 8997), "Half-Life01", 0);
			halfLife02 = new LCRecord(conf.GetItemID("halfLife02", 8998), "Half-Life02", 1);
			halfLife03 = new LCRecord(conf.GetItemID("halfLife03", 8999), "Half-Life03", 2);

			LanguageRegistry.addName("Half-Life01", "record");
			LanguageRegistry.addName("Half-Life02", "record");
			LanguageRegistry.addName("Half-Life03", "record");
			
			
		} catch(Exception e){
			System.err.println("Error when loading itemIDs from config . " + e );
		}
		
		addItemRecipes();
	}
	
	/**
	 * 添加基础合成表
	 */
	public static void addItemRecipes(){
        //Recipes

        ItemStack output[] = {
        		mat_accessories.newStack(1),
        		mat_bio.newStack(1),
        		mat_ammunition.newStack(1),
        		mat_tech.newStack(1),
        		new ItemStack(lambdaChip)
        };
        
        OreDictionary.registerOre("oreUranium", CBCBlocks.blockUraniumOre);
        OreDictionary.registerOre("ingotUranium", CBCItems.ingotUranium);
        OreDictionary.registerOre("ingotSteel", CBCItems.ingotSteel);
        OreDictionary.registerOre("blockSteel", CBCBlocks.blockRefined);
        
        ItemStack sredstone = new ItemStack(Item.redstone),
        		swood = new ItemStack(Block.wood),
        		sglow = new ItemStack(Item.lightStoneDust),
        		sstick = new ItemStack(Item.stick),
        		sglass = new ItemStack(Block.glass),
        		scoal = new ItemStack(Item.coal),
        		sgold = new ItemStack(Item.ingotGold),
        		siron = new ItemStack(Item.ingotIron),
        		sblazep = new ItemStack(Item.blazePowder),
        		sdiamond = new ItemStack(Item.diamond),
        		sbredstone = new ItemStack(Block.blockRedstone),
        		sblap = new ItemStack(Block.blockLapis),
        		sgunpowder = new ItemStack(Item.gunpowder),
        		sflint = new ItemStack(Item.flint),
        		sfspieye = new ItemStack(Item.fermentedSpiderEye),
        		srotten = new ItemStack(Item.rottenFlesh),
        		smagma = new ItemStack(Item.magmaCream),
        		sbox = mat_box.newStack(1),
        		stnt = new ItemStack(Block.tnt),
        		sendereye = new ItemStack(Item.eyeOfEnder),
        		slambdachip = new ItemStack(lambdaChip),
        		semerald = new ItemStack(Item.emerald),
        		schest = new ItemStack(Block.chest);
 
        Object input[][] = new Object[][]{
        		{"ADA", "CCC", "EBE", 'A', sstick, 'B', sbox, 'C', scoal, 'D', sglow, 'E', sglass},
        		{"ACA", "DFD", "EBE", 'A', srotten, 'B', sbox, 'C', smagma, 'D', sfspieye, 'E', sglass, 'F', sendereye},
        		{"AAA", "ACA", "EBE", 'A', sgunpowder, 'B', sbox, 'C', sredstone, 'E', sglass},
        		{"ACA", "DFD", "EBE", 'A', sglow, 'B', sbox, 'C',sbredstone, 'D', sdiamond, 'E', sglass, 'F', slambdachip},
        		{"ABA","CDC","ABA", 'A', sglass, 'B', semerald, 'C', sredstone, 'D', sdiamond}
        };
        
        addRecipes(output, input);
       // GameRegistry.addShapelessRecipe(new ItemStack(ingotSteel, 9), sbrefined);
        //Smeltings
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ingotSteel, 9), "blockSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CBCBlocks.blockRefined), "AAA", "AAA", "AAA", 'A', "ingotSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CBCBlocks.blockWeaponCrafter), "AAA", "BCB", "EDE", 'A', sglass, 'B', sbredstone, 'C', slambdachip, 'D', schest, 'E', "blockSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ironBar, 3), "A  ", "A  ", 'A', "ingotSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(mat_box.newStack(5), "A A", "AAA", 'A', "ingotSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(mat_pistol.newStack(1), "ADA", "CCC", "EBE", 'A', sstick,'B', sbox, 'C', "ingotSteel", 'D', sredstone, 'E', sglass));
        GameRegistry.addRecipe(new ShapedOreRecipe(mat_light.newStack(1), "ADA", "CCC", "EBE", 'A', sgold, 'B', sbox, 'C', "ingotSteel", 'D', sglow, 'E', sglass));
        GameRegistry.addRecipe(new ShapedOreRecipe(mat_heavy.newStack(1), "ADA", "CCC", "EBE", 'A', sblazep, 'B', sbox, 'C', "ingotSteel", 'D', sblap, 'E', sglass));
        GameRegistry.addRecipe(new ShapedOreRecipe(mat_explosive.newStack(1), "ACA", "DCD", "EBE", 'A', "ingotSteel", 'B', sbox, 'C', stnt, 'D', sgunpowder, 'E', sglass));
        ModLoader.addSmelting(Item.ingotIron.itemID,new ItemStack(ingotSteel.itemID,1,0) );
        ModLoader.addSmelting(CBCBlocks.blockUraniumOre.blockID, new ItemStack(ingotUranium), 2);
	}
	
	private static void addRecipes(ItemStack[] output, Object[][] input){
		if(output.length != input.length){
			throw new WrongUsageException("Two par's size should be the same", input[0]);
		}
		for(int i = 0; i < output.length; i++){
			ItemStack out = output[i];
			Object[] in = input[i];
			boolean flag = true;
			if(flag)
				GameRegistry.addRecipe(out, in);
		}
	}
	
}
