package cbproject.core.register;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface CBCGuiElement {
	public Object getServerContainer(int ID, EntityPlayer player, World world,
			int x, int y, int z);
	public Object getClientGui(int ID, EntityPlayer player, World world,
			int x, int y, int z);
}
