package cbproject.deathmatch.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cbproject.core.register.IGuiElement;
import cbproject.deathmatch.blocks.tileentities.TileEntityArmorCharger;
import cbproject.deathmatch.gui.ContainerArmorCharger;
import cbproject.deathmatch.gui.GuiArmorCharger;
import cbproject.deathmatch.blocks.tileentities.TileEntityHealthCharger;

public class DMGuiElements {

	public static class ElementArmorCharger implements IGuiElement {
		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerArmorCharger((TileEntityArmorCharger) world.getBlockTileEntity(x, y, z), player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiArmorCharger((TileEntityArmorCharger) world.getBlockTileEntity(x, y, z), new ContainerArmorCharger((TileEntityArmorCharger) world.getBlockTileEntity(x, y, z), player.inventory));
		}
	}
	
	public static class ElementHealthCharger implements IGuiElement {
		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerHealthCharger((TileEntityHealthCharger) world.getBlockTileEntity(x, y, z), player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiHealthCharger((TileEntityHealthCharger) world.getBlockTileEntity(x, y, z), new ContainerHealthCharger((TileEntityHealthCharger) world.getBlockTileEntity(x, y, z), player.inventory));
		}
	}

}
