package cn.weaponmod.client.keys;

import net.minecraftforge.client.IItemRenderer;
import cn.weaponmod.client.keys.Debug_KeyMoving.EnumKey;

public interface Debug_MovingProcessor <T extends IItemRenderer>{

	boolean isProcessAvailable(IItemRenderer render, int mode);
	String doProcess(T render, EnumKey key, int mode, int tickTime);
	String getDescription(int mode);
	
}
