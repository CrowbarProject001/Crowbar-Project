package cbproject.elements.events.weapons;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class HGrenadePinEvent extends PlayerEvent {
	public final ItemStack itemStack;
	public int duration;
	
	public HGrenadePinEvent(EntityPlayer player, ItemStack gren, int chg) {
		super(player);
		itemStack = gren;
		this.duration=chg;
        System.out.println("Event updated.");
        System.out.println("charge: " + duration);
		// TODO Auto-generated constructor stub
	}

}
