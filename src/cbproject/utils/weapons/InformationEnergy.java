package cbproject.utils.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InformationEnergy extends InformationWeapon {
	
	public int charge;
	public Boolean isShooting;
	
	public InformationEnergy(ItemStack par1ItemStack, EntityPlayer par2Player) {
		
		charge = 0;
		isShooting = false;
		ammoManager = new AmmoManager(par2Player, par1ItemStack);
		
	}

	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}
	
}
