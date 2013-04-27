package cbproject.deathmatch.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.props.ClientProps;
import cbproject.core.proxy.ClientProxy;
import cbproject.core.register.CBCItems;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.IArmorTextureProvider;

public class ArmorHEV extends ItemArmor implements IArmorTextureProvider  {

	public static int reductionAmount[] = {0, 0, 0, 0};
	public static EnumArmorMaterial material = EnumHelper.addArmorMaterial("armorHEV", 0, reductionAmount, 0);
	
	public ArmorHEV(int par1, int armorType) {
		super(par1, material, 2, armorType);
		int reductionAmount[] = {0, 0, 0, 0};
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("armorHEV" + this.armorType);
	}

	@Override
	public String getArmorTextureFile(ItemStack itemstack) {
		if(itemstack.getItem().itemID == CBCItems.armorHEVLeggings.itemID){
			return ClientProps.HEV_ARMOR_PATH[1];
		} else {
			return ClientProps.HEV_ARMOR_PATH[0];
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("cbproject:armorHEV" + this.armorType);
    }

}
