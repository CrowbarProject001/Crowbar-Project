package cbproject.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cbproject.elements.blocks.tileentities.TileEntityWeaponCrafter;
import cbproject.elements.gui.ContainerWeaponCrafter;
import cbproject.elements.gui.GuiWeaponCrafter;
import cpw.mods.fml.common.network.IGuiHandler;

public class CBCGuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntityWeaponCrafter te = (TileEntityWeaponCrafter) world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityWeaponCrafter)
			return new ContainerWeaponCrafter(player.inventory, te);
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntityWeaponCrafter te = (TileEntityWeaponCrafter) world.getBlockTileEntity(x, y, z);
		if(te instanceof TileEntityWeaponCrafter)
			return new GuiWeaponCrafter(player.inventory, te);
		return null;
	}

}