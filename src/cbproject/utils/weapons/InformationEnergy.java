package cbproject.utils.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationEnergy extends InformationWeapon {
	
	public int charge,chargeTime;
	public Boolean isShooting;
	
	public InformationEnergy(ItemStack par1ItemStack, EntityPlayer par2Player) {
		super(par2Player, par1ItemStack);
		charge = 0;
		isShooting = false;
	}

	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}
	
	@Override
	public void resetState(){
		super.resetState();
		charge = chargeTime = 0;
		isShooting = false;
	}
	
}
