package cbproject.deathmatch.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.api.item.ICustomEnItem;
import cbproject.core.CBCMod;
import cbproject.core.props.ClientProps;
import cbproject.deathmatch.register.DMItems;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;

public class ArmorHEV extends ItemArmor implements ICustomEnItem {

	public static int reductionAmount[] = {8, 15, 10, 5};
	public static EnumArmorMaterial material = EnumHelper.addArmorMaterial("armorHEV", 1000000, reductionAmount, 0);
	
	public ArmorHEV(int par1, int armorType) {
		super(par1, material, 2, armorType);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("hev" + this.armorType);
		System.out.println("Registered type :" + armorType + Item.itemsList[par1]);
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

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public int getChargedItemId(ItemStack itemStack) {
		return itemStack.getItem().itemID;
	}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {
		return itemStack.getItem().itemID;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return this.getMaxDamage();
	}

	@Override
	public int getTier(ItemStack itemStack) {
		return 2;
	}

	@Override
	public int getTransferLimit(ItemStack itemStack) {
		return 128;
	}

	@Override
	public int charge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		if(itemStack.getItemDamage() == 0)
			return 0;
		int en = itemStack.getMaxDamage() - itemStack.getItemDamage();
		if(!ignoreTransferLimit)
			amount = this.getTransferLimit(itemStack);
		if(en > amount){
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() - amount);
			return amount;
		} else {
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() - en);
			return en;
		}
	}

	@Override
	public int discharge(ItemStack itemStack, int amount, int tier,
			boolean ignoreTransferLimit, boolean simulate) {
		int en = itemStack.getMaxDamage() - itemStack.getItemDamage();
		if(en == 0)
			return 0;
		if(!ignoreTransferLimit)
			amount = this.getTransferLimit(itemStack);
		if(en > amount){
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() + amount);
			return amount;
		} else {
			if(!simulate)
				itemStack.setItemDamage(itemStack.getItemDamage() + en);
			return en;
		}
	}

	@Override
	public boolean canUse(ItemStack itemStack, int amount) {
		int en = itemStack.getMaxDamage() - itemStack.getItemDamage();
		return en > 1000;
	}

	@Override
	public boolean canShowChargeToolTip(ItemStack itemStack) {
		return true;
	}

}
