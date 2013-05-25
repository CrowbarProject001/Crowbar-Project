package cbproject.deathmatch.items;

import cbproject.core.item.CBCGenericArmor;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraftforge.common.EnumHelper;

public class ArmorLongjump extends CBCGenericArmor {
	
	public static int reductionAmount[] = {0, 0, 0, 0};
	public static EnumArmorMaterial material = EnumHelper.addArmorMaterial("armorLJ", 0, reductionAmount, 0);
	
	public ArmorLongjump(int par1, int armorType) {
		super(par1, material, 2, armorType);
		setUnlocalizedName("longjump");
		this.setIconName("longjump");
	}

}
