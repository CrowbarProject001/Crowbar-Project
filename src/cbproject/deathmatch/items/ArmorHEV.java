package cbproject.deathmatch.items;

import cbproject.core.item.ElectricArmor;
import cbproject.core.props.ClientProps;
import cbproject.deathmatch.register.DMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

public class ArmorHEV extends ElectricArmor {

	public enum EnumAttachment {
		LONGJUMP(2, "lj");
		private int slot;
		private String internalName;
		private EnumAttachment(int x, String name) {
			this.slot = x;
			this.internalName = name;
		}
		public int getSlot(){
			return slot;
		}
		public String getName(){
			return internalName;
		}
	}
	
	public static int reductionAmount[] = {0, 0, 0, 0};
	protected static EnumArmorMaterial material = EnumHelper.addArmorMaterial("armorHEV", 100000, reductionAmount, 0);
	private static ArmorProperties propChest = new ArmorProperties(3, 100.0, 325),
			propDefault = new ArmorProperties(2, 100.0, 250), 
			propNone = new ArmorProperties(2, 0.0, 0),
			propShoe = new ArmorProperties(2, 100.0, 125),
			propShoeFalling = new ArmorProperties(3, 100.0, 2500);
	
	public ArmorHEV(int par1, int armorType) {
		super(par1, material, 2, armorType);
		setUnlocalizedName("hev" + this.armorType);
		this.setIconName("hev" + armorType);
		this.setMaxCharge(200000);
		this.setTier(2);
		this.setTransferLimit(128);
		this.setEnergyPerDamage(1000);
	}
	
	public ArmorHEV(int par1, EnumAttachment attach) {
		super(par1, material, 2, attach.getSlot());
		setUnlocalizedName("hev_" + attach.getName());
		this.setIconName("hev_" + attach.getName());
		this.setMaxCharge(200000);
		this.setTier(2);
		this.setTransferLimit(128);
		this.setEnergyPerDamage(1000);
	}
	
	public static EnumAttachment getAttachment() {
		return null;
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
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor,
			DamageSource source, double damage, int slot) {
		if(getItemCharge(armor) <= this.energyPerDamage)
			return propNone;
		if(source == DamageSource.fall){
			if(slot == 0)
				return propShoeFalling;
			else return propNone;
		}
		return slot == 2? propChest : (slot == 0 ? propShoe : propDefault);
	}
	
	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (slot == 2) ? 8 : 4;
	}

}
