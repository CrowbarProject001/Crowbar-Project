package cbproject.deathmatch.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import cbproject.deathmatch.items.ArmorHEV;

public class OnLivingAttack {
	
	@ForgeSubscribe
	public void onLivingAttack(LivingAttackEvent event){
		if(event.source == DamageSource.fall){
			if(event.ammount <= 2)
				return;
			if(event.entity instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer) event.entity;				
				if(player.inventory.armorInventory[0] == null)
					return;
				for(ItemStack i : player.inventory.armorInventory){
					if(i == null)
						System.out.println(i + ": " + null);
					else System.out.println(i + ": " + i.getItemName());
				}
					
				if(player.inventory.armorInventory[0].getItem() instanceof ArmorHEV){
					player.attackEntityFrom(DamageSource.fall, 2);
					event.setCanceled(true);
				}
			}
		}
	}
	
}