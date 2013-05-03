package cbproject.crafting.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cbproject.core.register.IGuiElement;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;

public class ElementCrafter implements IGuiElement {

	@Override
	public Object getServerContainer(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return new ContainerWeaponCrafter(player.inventory, (TileEntityWeaponCrafter) world.getBlockTileEntity(x, y, z));
	}

	@Override
	public Object getClientGui(int ID, EntityPlayer player, World world, int x,
			int y, int z) {
		return new GuiWeaponCrafter(player.inventory, (TileEntityWeaponCrafter) world.getBlockTileEntity(x, y, z));
	}

}
