package cbproject.crafting.items;

import cbproject.core.CBCMod;
import cbproject.core.item.CBCGenericItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMaterial extends CBCGenericItem {

	private EnumMaterial material;
	
	public enum EnumMaterial {
		BOX(0), AMMUNITION(1), ARMOR(2), ACCESSORIES(3), BIO(4), EXPLOSIVE(5), HEAVY(6), LIGHT(7), PISTOL(8), TECH(9);
		private int id;
		private EnumMaterial(int i){
			this.id = i;
		}
		@Override
		public String toString() {
			return this.name().toLowerCase();
		}
	}
	
	public ItemMaterial(int par1, EnumMaterial mat) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		this.setUnlocalizedName("mat_" + mat.toString());
		this.setIconName("mat_" + mat.toString());
		this.material = mat;
	}
	
	public ItemStack newStack(int stackSize) {
		return new ItemStack(this.itemID, stackSize, material.id);
	}

}
