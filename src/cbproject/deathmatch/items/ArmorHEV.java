package cbproject.deathmatch.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.props.ClientProps;
import cbproject.deathmatch.register.DMItems;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.IArmorTextureProvider;

public class ArmorHEV extends ItemArmor{

	public static int reductionAmount[] = {0, 0, 0, 0};
	public static EnumArmorMaterial material = EnumHelper.addArmorMaterial("armorHEV", 0, reductionAmount, 0);
	
	public ArmorHEV(int par1, int armorType) {
		super(par1, material, 2, armorType);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("hev" + this.armorType);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		if(stack.getItem().itemID == DMItems.armorHEVLeggings.itemID){
			return ClientProps.HEV_ARMOR_PATH[1];
		} else {
			return ClientProps.HEV_ARMOR_PATH[0];
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:hev" + this.armorType);
    }

}
