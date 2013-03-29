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
		"plgun_c",
		"nmmclipa",
		"explode_a",
		"explode_b",
		"g_bounceb",
		"gunjam_a",
		"hksa",
		"hksb",
		"hksc",
		"nmmarr",
		"pyt_shota",
		"pyt_shotb",
		"pyt_cocka",
		"pyt_reloada",
		"sbarrela",
		"scocka",
		"cbar_hita",
		"cbar_hitb",
		"cbar_hitboda",
		"cbar_hitbodb",
		"cbar_hitbodc",
		"reloada",
		"reloadb",
		"reloadc",
		"gaussb",
		"gauss_chargea",
		"gauss_chargeb"
		
	};
	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event)
	{
		
		System.out.println("Attempting to load CBC sound files...");
		
		try{
			SoundPoolEntry snd;
			for(String path:PathWeapons){
				snd = event.manager.soundPoolSounds.addSound("cbc/weapons/" + path + ".wav", CBCMod.class.getResource("/cbproject/gfx/sounds/weapons/" + path + ".wav"));
				System.out.println("AddSound : " + snd.soundName + " URL: " + snd.soundUrl);
			}

		}catch(Exception e){
			
			System.out.println("Sound registering failed.");
			
		}
	}
	

}
