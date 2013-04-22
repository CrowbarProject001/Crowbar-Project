package cbproject.elements.items.armor;

import cbproject.CBCMod;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;

public class ArmorHEVBoot extends ItemArmor {

	public ArmorHEVBoot(int par1) {
		super(par1, EnumArmorMaterial.IRON, 2, 3);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("armorHEVBoot");
	}

}
