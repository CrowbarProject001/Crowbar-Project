package cbproject.crafting.register;

import cbproject.core.misc.Config;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.GeneralRegistry;
import cbproject.crafting.items.*;
import cbproject.crafting.items.ItemMaterial.EnumMaterial;
import cbproject.deathmatch.items.ammos.*;
import net.minecraft.block.Block;
import net.minecraft.command.WrongUsageException;
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
	
	public static ItemMaterial materials;
	public static LambdaChipset lambdaChip;
	public static SteelBar ironBar;
	public static IngotUranium ingotUranium;
	public static LCRecord halfLife01, halfLife02, halfLife03;
	public static ItemSpray spray1, spray2;
	
	/**
	 * 实际注册，请在Init中调用。
	 * @param conf
	 */
	public static void init(Config conf){
		
		ammo_uranium = new Ammo_uranium(GeneralRegistry.getItemId("itemAmmo_uranium", 0));
		ammo_9mm = new Ammo_9mm(GeneralRegistry.getItemId("itemAmmo_9mm", 0));
		ammo_9mm2 = new Ammo_9mm2(GeneralRegistry.getItemId("itemAmmo_9mm2", 0));
		ammo_357 = new Ammo_357(GeneralRegistry.getItemId("itemAmmo_357", 0));
		ammo_bow = new Ammo_bow(GeneralRegistry.getItemId("itemAmmo_bow", 0));
		ammo_rpg = new Ammo_rpg(GeneralRegistry.getItemId("itemAmmo_RPG", 0));
		ammo_argrenade = new Ammo_argrenade(GeneralRegistry.getItemId("itemAmmo_ARGrenade", 0));
		
		ammo_shotgun = new Ammo_shotgun(GeneralRegistry.getItemId("itemBullet_Shotgun", 0));
		bullet_9mm = new Bullet_9mm(GeneralRegistry.getItemId("itemBullet_9mm", 0));
		bullet_steelbow = new Bullet_steelbow(GeneralRegistry.getItemId("steelbow", 0));

		materials = new ItemMaterial(GeneralRegistry.getItemId("mat_a", 0));
		
		ironBar = new SteelBar(GeneralRegistry.getItemId("ironBar", 0));
		lambdaChip = new LambdaChipset(GeneralRegistry.getItemId("lambdachip", 0));
		ingotUranium = new IngotUranium(GeneralRegistry.getItemId("ingotUranium", 0));
		ingotSteel = new IngotSteel(GeneralRegistry.getItemId("itemRefinedIronIngot",0));
		
		halfLife01 = new LCRecord(GeneralRegistry.getItemId("halfLife01", GeneralProps.CAT_MISC), "Half-Life01", 0);
		halfLife02 = new LCRecord(GeneralRegistry.getItemId("halfLife02", GeneralProps.CAT_MISC), "Half-Life02", 1);
		halfLife03 = new LCRecord(GeneralRegistry.getItemId("halfLife03", GeneralProps.CAT_MISC), "Half-Life03", 2);
		
		spray1 = new ItemSpray(GeneralRegistry.getItemId("spray1", GeneralProps.CAT_MISC), 0);
		spray2 = new ItemSpray(GeneralRegistry.getItemId("spray2", GeneralProps.CAT_MISC), 1);
		
		LanguageRegistry.addName(halfLife01, "record");
		LanguageRegistry.addName(halfLife01, "record");
		LanguageRegistry.addName(halfLife01, "record");
		
		addItemRecipes();
	}
	
	/**
	 * 添加基础合成表
	 */
	public static void addItemRecipes(){
        //Recipes

        ItemStack output[] = {
        		materials.newStack(1, EnumMaterial.ACCESSORIES),
        		materials.newStack(1, EnumMaterial.BIO),
        		materials.newStack(1, EnumMaterial.AMMUNITION),
        		materials.newStack(1, EnumMaterial.TECH),
        		new ItemStack(lambdaChip)
        };
        
        OreDictionary.registerOre("oreUranium", CBCBlocks.uraniumOre);
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
        		sbox = materials.newStack(1, EnumMaterial.BOX),
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
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CBCBlocks.weaponCrafter), "AAA", "BCB", "EDE", 'A', sglass, 'B', sbredstone, 'C', slambdachip, 'D', schest, 'E', "blockSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ironBar, 3), "A  ", "A  ", 'A', "ingotSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(materials.newStack(5, EnumMaterial.BOX), "A A", "AAA", 'A', "ingotSteel"));
        GameRegistry.addRecipe(new ShapedOreRecipe(materials.newStack(1, EnumMaterial.PISTOL), "ADA", "CCC", "EBE", 'A', sstick,'B', sbox, 'C', "ingotSteel", 'D', sredstone, 'E', sglass));
        GameRegistry.addRecipe(new ShapedOreRecipe(materials.newStack(1, EnumMaterial.LIGHT), "ADA", "CCC", "EBE", 'A', sgold, 'B', sbox, 'C', "ingotSteel", 'D', sglow, 'E', sglass));
        GameRegistry.addRecipe(new ShapedOreRecipe(materials.newStack(1, EnumMaterial.HEAVY), "ADA", "CCC", "EBE", 'A', sblazep, 'B', sbox, 'C', "ingotSteel", 'D', sblap, 'E', sglass));
        GameRegistry.addRecipe(new ShapedOreRecipe(materials.newStack(1, EnumMaterial.EXPLOSIVE), "ACA", "DCD", "EBE", 'A', "ingotSteel", 'B', sbox, 'C', stnt, 'D', sgunpowder, 'E', sglass));
        ModLoader.addSmelting(Item.ingotIron.itemID,new ItemStack(ingotSteel.itemID,1,0) );
        ModLoader.addSmelting(CBCBlocks.uraniumOre.blockID, new ItemStack(ingotUranium), 2);
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
