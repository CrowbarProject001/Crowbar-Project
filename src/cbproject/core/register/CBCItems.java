package cbproject.core.register;

import cbproject.core.misc.Config;
import cbproject.crafting.items.*;
import cbproject.deathmatch.items.ammos.*;
import net.minecraft.block.Block;
import net.minecraft.command.WrongUsageException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * CBC Mod Item Register class.
 * @author WeAthFolD, mkpoli
 *
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
	
			mat_accessories = new Material_accessories(conf.GetItemID("mat_a", 8050));
			mat_ammunition = new Material_ammunition(conf.GetItemID("mat_b", 8051));
			mat_bio = new Material_bio(conf.GetItemID("mat_c", 8052));
			mat_heavy = new Material_heavy(conf.GetItemID("mat_d", 8053));
			mat_light = new Material_light(conf.GetItemID("mat_e", 8054));
			mat_pistol = new Material_pistol(conf.GetItemID("mat_f", 8055));
			mat_tech = new Material_tech(conf.GetItemID("mat_g", 8056));
			mat_explosive = new Material_explosive(conf.GetItemID("mat_h", 8057));
			mat_box = new Material_box(conf.GetItemID("mat_0", 8058));
			
			ironBar = new SteelBar(conf.GetItemID("ironBar", 8059));
			lambdaChip = new LambdaChipset(conf.GetItemID("lambdachip", 8060));
			ingotUranium = new IngotUranium(conf.GetItemID("ingotUranium", 8061));
			ingotSteel = new IngotSteel(conf.GetItemID("itemRefinedIronIngot",7100));
			
		} catch(Exception e){
			System.err.println("Error when loading itemIDs from config . " + e );
		}
		
		addItemRecipes();
	}
	
	public static void addItemRecipes(){
        //Recipes

        ItemStack output[] = {
        		new ItemStack(mat_pistol, 1),
        		new ItemStack(mat_accessories, 1),
        		new ItemStack(mat_light, 1),
        		new ItemStack(mat_heavy, 1),
        		new ItemStack(mat_explosive, 1),
        		new ItemStack(mat_bio, 1),
        		new ItemStack(mat_ammunition, 1),
        		new ItemStack(mat_tech, 1),
        		new ItemStack(mat_box, 5),
        		new ItemStack(CBCBlocks.blockRefined, 1),
        		new ItemStack(ironBar, 5),
        		new ItemStack(lambdaChip),
        		new ItemStack(CBCBlocks.blockWeaponCrafter)
        };
        
        ItemStack sredstone = new ItemStack(Item.redstone),
        		swood = new ItemStack(Block.wood),
        		sglow = new ItemStack(Item.lightStoneDust),
        		sstick = new ItemStack(Item.stick),
        		srefined = new ItemStack(ingotSteel),
        		sglass = new ItemStack(Block.glass),
        		scoal = new ItemStack(Item.coal),
        		sgold = new ItemStack(Item.ingotGold),
        		siron = new ItemStack(Item.ingotIron),
        		sblazep = new ItemStack(Item.blazePowder),
        		sdiamond = new ItemStack(Item.diamond),
        		sbrefined = new ItemStack(CBCBlocks.blockRefined),
        		sbredstone = new ItemStack(Block.blockRedstone),
        		sblap = new ItemStack(Block.blockLapis),
        		sgunpowder = new ItemStack(Item.gunpowder),
        		sflint = new ItemStack(Item.flint),
        		sfspieye = new ItemStack(Item.fermentedSpiderEye),
        		srotten = new ItemStack(Item.rottenFlesh),
        		smagma = new ItemStack(Item.magmaCream),
        		sbox = new ItemStack(CBCItems.mat_box),
        		stnt = new ItemStack(Block.tnt),
        		sendereye = new ItemStack(Item.eyeOfEnder),
        		slambdachip = new ItemStack(lambdaChip),
        		suranium = new ItemStack(ingotUranium),
        		semerald = new ItemStack(Item.emerald),
        		schest = new ItemStack(Block.chest);
 
        Object input[][] = new Object[][]{
        		{"ADA", "CCC", "EBE", 'A', sstick,'B', sbox, 'C', srefined, 'D', sredstone, 'E', sglass},
        		{"ADA", "CCC", "EBE", 'A', sstick, 'B', sbox, 'C', scoal, 'D', sglow, 'E', sglass},
        		{"ADA", "CCC", "EBE", 'A', sgold, 'B', sbox, 'C', srefined, 'D', sglow, 'E', sglass},
        		{"ADA", "CCC", "EBE", 'A', sblazep, 'B', sbox, 'C', srefined, 'D', sblap, 'E', sglass},
        		{"ACA", "DCD", "EBE", 'A', srefined, 'B', sbox, 'C', stnt, 'D', sgunpowder, 'E', sglass},
        		{"ACA", "DFD", "EBE", 'A', srotten, 'B', sbox, 'C', smagma, 'D', sfspieye, 'E', sglass, 'F', sendereye},
        		{"AAA", "ACA", "EBE", 'A', sgunpowder, 'B', sbox, 'C', sredstone, 'E', sglass},
        		{"ACA", "DFD", "EBE", 'A', sglow, 'B', sbox, 'C',sbredstone, 'D', sdiamond, 'E', sglass, 'F', slambdachip},
        		{"A A", "AAA", 'A', srefined},
        		{"AAA", "AAA", "AAA", 'A', srefined},
        		{"A  ", "A  ", 'A', srefined},
        		{"ABA","CDC","ABA", 'A', sglass, 'B', semerald, 'C', sredstone, 'D', sdiamond},
        		{"AAA","BCB","EDE", 'A', sglass, 'B', sbredstone, 'C', slambdachip, 'D', schest, 'E', sbrefined}
        };
        
        addRecipes(output, input);
        GameRegistry.addShapelessRecipe(new ItemStack(ingotSteel, 9), sbrefined);
        //Smeltings
        ModLoader.addSmelting(Item.ingotIron.itemID,new ItemStack(ingotSteel.itemID,1,0) );
        ModLoader.addSmelting(CBCBlocks.blockUraniumOre.blockID, suranium);
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
