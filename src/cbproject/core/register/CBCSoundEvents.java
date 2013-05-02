package cbproject.core.register;

import java.util.HashSet;

import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cbproject.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CBCSoundEvents {

	private static HashSet<String[]> pathSounds = new HashSet();
	
	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void onSound(SoundLoadEvent event)
	{
		System.out.println("Attempting to load CBC sound files...");
		try{
			SoundPoolEntry snd;
			for(String[] path:pathSounds){
				snd = event.manager.soundPoolSounds.addSound(path[0], CBCMod.class.getResource(path[1]));
				System.out.println("AddSound : " + snd.soundName + " URL: " + snd.soundUrl);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param name : name of the sound. i.e. "cbc/weapons/rocket"
	 * @param sndname : sound name . i.e."/cbproject/gfx/weapons/glauncherb"
	 * Both params are auto prefixed by .wav.
	 */
	public static void addSoundPath(String name, String absPath){
		String[] s = {name + ".wav", absPath + ".wav"};
		pathSounds.add(s);
	}

}
