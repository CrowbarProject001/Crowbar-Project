package cbproject.crafting.items;

import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.item.CBCGenericItem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public class ItemMaterial extends CBCGenericItem {
	
	private Icon icons[] = new Icon[10];
	
	EnumMaterial mat = EnumMaterial.BOX;
	
	public enum EnumMaterial {
		BOX(0), AMMUNITION(1), ARMOR(2), ACCESSORIES(3), BIO(4), EXPLOSIVE(5), HEAVY(6), LIGHT(7), PISTOL(8), TECH(9);
		private int id;

		private static HashMap<Integer, EnumMaterial> map = new HashMap();
		static{
			for(EnumMaterial a : EnumMaterial.values()) {
				map.put(a.ordinal(), a);
			}
		}
		
		private EnumMaterial(int i){
			this.id = i;
		}
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}
	
	public ItemMaterial(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		this.setUnlocalizedName("material");
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        for(EnumMaterial i : EnumMaterial.values()) {
        	icons[i.id] = ir.registerIcon("lambdacraft:mat_" + i.name().toLowerCase());
        }
    }
	
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return "item.mat_" + mat.map.get(par1ItemStack.getItemDamage());
    }
	
	
	public ItemStack newStack(int stackSize, EnumMaterial mat) {
		return new ItemStack(this.itemID, stackSize, mat.id);
	}
	
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
        return this.icons[par1];
    }
	
	@Override
    @SideOnly(Side.CLIENT)

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 10; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }

}
