package cn.lambdacraft.api.weapon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationEnergy extends InformationWeapon {

	public int charge, chargeTime;
	public float rotationVelocity;
	public float rotationAngle;
	
	public InformationEnergy(ItemStack par1ItemStack, EntityPlayer par2Player) {
		super(par1ItemStack);
		charge = 0;
	}

	@Override
	public void updateTick() {
		ticksExisted++;
	}

	@Override
	public void setLastTick() {
		lastTick = ticksExisted;
	}

	@Override
	public void resetState() {
		super.resetState();
		charge = chargeTime = 0;
	}

}
