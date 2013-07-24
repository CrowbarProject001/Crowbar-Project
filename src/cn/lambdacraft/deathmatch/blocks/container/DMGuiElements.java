package cn.lambdacraft.deathmatch.blocks.container;

import cn.lambdacraft.core.register.IGuiElement;
import cn.lambdacraft.deathmatch.blocks.TileArmorCharger;
import cn.lambdacraft.deathmatch.blocks.TileHealthCharger;
import cn.lambdacraft.deathmatch.blocks.TileMedkitFiller;
import cn.lambdacraft.deathmatch.blocks.container.ContainerArmorCharger;
import cn.lambdacraft.deathmatch.client.gui.GuiArmorCharger;
import cn.lambdacraft.deathmatch.client.gui.GuiHealthCharger;
import cn.lambdacraft.deathmatch.client.gui.GuiMedFiller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class DMGuiElements {

	public static class ElementArmorCharger implements IGuiElement {
		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerArmorCharger(
					(TileArmorCharger) world.getBlockTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiArmorCharger(
					(TileArmorCharger) world.getBlockTileEntity(x, y, z),
					new ContainerArmorCharger((TileArmorCharger) world
							.getBlockTileEntity(x, y, z), player.inventory));
		}
	}

	public static class ElementHealthCharger implements IGuiElement {
		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerHealthCharger(
					(TileHealthCharger) world.getBlockTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiHealthCharger(
					(TileHealthCharger) world.getBlockTileEntity(x, y, z),
					new ContainerHealthCharger(
							(TileHealthCharger) world.getBlockTileEntity(x, y,
									z), player.inventory));
		}
	}

	public static class ElementMedFiller implements IGuiElement {
		@Override
		public Object getServerContainer(EntityPlayer player, World world,
				int x, int y, int z) {
			return new ContainerMedFiller(
					(TileMedkitFiller) world.getBlockTileEntity(x, y, z),
					player.inventory);
		}

		@Override
		public Object getClientGui(EntityPlayer player, World world, int x,
				int y, int z) {
			return new GuiMedFiller(
					(TileMedkitFiller) world.getBlockTileEntity(x, y, z),
					new ContainerMedFiller((TileMedkitFiller) world
							.getBlockTileEntity(x, y, z), player.inventory));
		}
	}

}
