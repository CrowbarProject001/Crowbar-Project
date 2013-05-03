package cbproject.core.register;

import java.util.HashMap;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.gui.ContainerWeaponCrafter;
import cbproject.crafting.gui.GuiWeaponCrafter;
import cpw.mods.fml.common.network.IGuiHandler;

public class CBCGuiHandler implements IGuiHandler {

	public static HashMap<Class<? extends TileEntity>, CBCGuiElement> guis = new HashMap();
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		Class<? extends TileEntity> te = world.getBlockTileEntity(x, y, z).getClass();
		if(te == null)
			return null;
		if(guis.containsKey(te)){
			return guis.get(te).getServerContainer(ID, player, world, x, y, z);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		Class<? extends TileEntity> te = world.getBlockTileEntity(x, y, z).getClass();
		if(te == null)
			return null;
		if(guis.containsKey(te)){
			return guis.get(te).getClientGui(ID, player, world, x, y, z);
		}
		return null;
	}
	
	public static void addGuiElement(Class<? extends TileEntity> tileEntity, CBCGuiElement process){
		guis.put(tileEntity, process);
	}

}
