package cbproject.deathmatch.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.api.item.ICustomEnItem;
import cbproject.core.CBCMod;
import cbproject.core.props.ClientProps;
import cbproject.deathmatch.register.DMItems;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.ISpecialArmor;

public class ArmorHEV extends ItemArmor implements ICustomEnItem, ISpecialArmor {

	private static final int ENERGY_PER_DAMAGE = 1000;
	public static int reductionAmount[] = {0, 0, 0, 0};
	public static EnumArmorMaterial material = EnumHelper.addArmorMaterial("armorHEV", 1000000, reductionAmount, 0);
	
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
		
		int en = itemStack.getMaxDamage() - itemStack.getItemDamage() - 1;
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
		int en = itemStack.getMaxDamage() - itemStack.getItemDamage() - 1;
		return en > 1000;
	}

	@Override
	public boolean canShowChargeToolTip(ItemStack itemStack) {
		return true;
	}

	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor,
			DamageSource source, double damage, int slot) {
		ArmorProperties ap = new ArmorProperties(slot == 2 ? 3:2, 12.0, 250);
		return ap;
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (slot == 2) ? 8 : 4;
	}

	@Override
	public void damageArmor(EntityLiving entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		this.discharge(stack, damage * ENERGY_PER_DAMAGE, 2, true, false);
	}

}
