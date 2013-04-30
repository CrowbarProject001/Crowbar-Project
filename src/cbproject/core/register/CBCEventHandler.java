package cbproject.core.register;

import java.util.HashSet;

import cbproject.deathmatch.events.CBCLivingAttackEvent;

import net.minecraftforge.common.MinecraftForge;

public class CBCEventHandler {

	private static HashSet events = new HashSet();
	
	private static void addEvents(){
		events.add(new CBCSoundEvents());
		events.add(new CBCLivingAttackEvent());
	}
	
	public static void registerAll(){
		addEvents();
		for(Object c : events){
			MinecraftForge.EVENT_BUS.register(c);
		}
	}

}
