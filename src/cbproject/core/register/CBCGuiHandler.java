package cbproject.core.register;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * 根据TileEntity的类型自动获取对应GUI的统一处理类。请配合IGuiHandler使用。
 * TODO:加入对按键等其他情况打开Gui的支持。
 * @author WeAThFolD
 */
public class CBCGuiHandler implements IGuiHandler {

	public static HashMap<Class<? extends TileEntity>, IGuiElement> guis = new HashMap();
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		Class<? extends TileEntity> te = world.getBlockTileEntity(x, y, z).getClass();
		if(te == null)
			return null;
		if(guis.containsKey(te)){
			Object gui = guis.get(te).getServerContainer(ID, player, world, x, y, z);
			System.out.println(te + " " + gui);
			return gui;
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
			Object gui = guis.get(te).getClientGui(ID, player, world, x, y, z);
			return gui;
		}
		return null;
	}
	
	public static void addGuiElement(Class<? extends TileEntity> tileEntity, IGuiElement process){
		guis.put(tileEntity, process);
	}

}