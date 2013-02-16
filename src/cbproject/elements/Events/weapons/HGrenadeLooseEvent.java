package cbproject.elements.Events.weapons;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class HGrenadeLooseEvent extends PlayerEvent {
	public final ItemStack itemGren;
	public int charge;
	public HGrenadeLooseEvent(EntityPlayer player, ItemStack gren, int chg) {
		super(player);
		itemGren = gren;
		this.charge=chg;
		// TODO Auto-generated constructor stub
	}

}
