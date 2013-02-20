package cbproject.misc;

import java.io.File;

import cbproject.CBCMod;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class CBCSoundEvents {
	
	@SuppressWarnings("unused")
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event)
	{
		
		System.out.println("but a simple try");
		
		
			
		File file=new File("/cbproject/sounds/hgrenadepin.wav");
		if(file==null){
				System.err.println("Fail to load the damn file.");
				return;
		} 
		System.out.println(file.getPath());
		try{
			SoundPoolEntry snd = event.manager.soundPoolSounds.addSound("cbc/hgrenadepinpull.wav", file); //indicates "cbc.hgrenadepinpull"
			System.out.println(snd.soundName);
		}catch(Exception e){
			
			System.out.println("Sound registering failed.");
			
		}
	}
	

}
