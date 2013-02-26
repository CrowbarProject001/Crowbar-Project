package cbproject.elements.events.process.weapons;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import cbproject.elements.events.weapons.EventHGrenadePin;

public class HGrenadePinEvent {
	
	public HGrenadePinEvent(){
		
	}
	
	
	@ForgeSubscribe
	public void onHGrenadePin( EventHGrenadePin event ){
		
		if( event.result.isOnItemFrame() ){
			
			event.setCanceled(true);
			
		}
	}

}
