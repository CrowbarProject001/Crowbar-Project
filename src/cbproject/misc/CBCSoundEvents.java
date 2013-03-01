package cbproject.misc;

import java.io.File;
import java.net.URL;

import cbproject.CBCMod;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class CBCSoundEvents {
	
	public final static String PathWeapons[]={
		
		"hgrenadepin",
		"hgrenadebounce",
		"pl_gun3",
		"9mmclip2",
		"explode_a",
		"explode5",
		"g_bounce2"
		
	};
	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event)
	{
		
		System.out.println("Attempting to load CBC sound files...");
		
		try{
			
			for(String path:PathWeapons){
				event.manager.soundPoolSounds.addSound("cbc/weapons/" + path + ".wav", CBCMod.class.getResource("/cbproject/gfx/sounds/weapons/" + path + ".wav"));
			}

		}catch(Exception e){
			
			System.out.println("Sound registering failed.");
			
		}
	}
	

}
