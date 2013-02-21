package cbproject.misc;

import java.io.File;
import java.net.URL;

import cbproject.CBCMod;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class CBCSoundEvents {
	
	public final static String Path[]={
		
		"/cbproject/gfx/sounds/weapons/hgrenadepin.wav",
		"/cbproject/gfx/sounds/weapons/hgrenadebounce.wav",
		"/cbproject/gfx/sounds/weapons/explode_a.wav",
		"/cbproject/gfx/sounds/weapon/explode5.wav",
		"/cbproject/gfx/sounds/weapons/g_bounce2.wav"
		
	};
	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event)
	{
		
		System.out.println("Attempting to load CBC sound files...");
		
		try{
			
			event.manager.soundPoolSounds.addSound("cbc/weapons/hgrenadepin.wav", CBCMod.class.getResource (Path[0]) ); 
			event.manager.soundPoolSounds.addSound("cbc/weapons/hgrenadebounce.wav", CBCMod.class.getResource (Path[1]) );
			SoundPoolEntry snd = event.manager.soundPoolSounds.addSound("cbc/weapons/explode_a.wav", CBCMod.class.getResource (Path[2]) );
			event.manager.soundPoolSounds.addSound("cbc/weapons/explode5.wav", CBCMod.class.getResource (Path[3]) );
			event.manager.soundPoolSounds.addSound("cbc/weapons/g_bounce2.wav", CBCMod.class.getResource (Path[4]) );
			System.out.println(snd.soundName);
			System.out.println(snd.soundUrl);
		}catch(Exception e){
			
			System.out.println("Sound registering failed.");
			
		}
	}
	

}
