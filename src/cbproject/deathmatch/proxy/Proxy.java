package cbproject.deathmatch.proxy;

import cbproject.deathmatch.events.CBCLivingAttackEvent;
import net.minecraftforge.common.MinecraftForge;

public class Proxy {
	public void init(){
		System.out.println("DM init method called");
		MinecraftForge.EVENT_BUS.register(new CBCLivingAttackEvent());
	}
}
