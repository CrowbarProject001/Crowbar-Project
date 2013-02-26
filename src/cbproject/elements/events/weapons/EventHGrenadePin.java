package cbproject.elements.events.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class EventHGrenadePin extends PlayerEvent {
	
	public ItemStack result;
	
	public EventHGrenadePin(EntityPlayer player , ItemStack itemStack) {
		
		super(player);
		result = itemStack;
		
		
	}

}
