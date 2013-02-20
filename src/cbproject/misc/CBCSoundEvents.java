package cbproject.misc;

import java.io.File;
import java.net.URL;

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
		
		
			
		//File file=new File("/cbproject/textures/blocks.png");
		//if(file==null || !file.canRead()){
		//		System.err.println("Fail to load the damn file.");
		//		return;
		//} 
		//System.out.println(file.getPath());
		try{
			URL url=CBCMod.class.getResource("/cbproject/sounds/hgrenadepin.wav");
			SoundPoolEntry snd = event.manager.soundPoolSounds.addSound("cbproject/sounds/hgrenadepin.wav", url); //indicates "cbc.hgrenadepinpull"
			System.out.println(snd.soundName);
			System.out.println(url.getPath());
		}catch(Exception e){
			
			System.out.println("Sound registering failed.");
			
		}
	}
	

}
