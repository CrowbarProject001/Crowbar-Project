package cbproject.core.register;

import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cbproject.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		"sbarrela_a",
		"sbarrelb",
		"sbarrelb_a",
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
		"gauss_charge",
		"gauss_windupa",
		"gauss_windupb",
		"gauss_windupc",
		"gauss_windupd",
		"rocketfirea",
		"xbow_fire",
		"xbow_reload",
		"egon_run",
		"egon_windup",
		"egon_off",
		"rocket",
		"rocketfire",
		"glauncher",
		"glauncherb"
		
	};
	
	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void onSound(SoundLoadEvent event)
	{
		
		System.out.println("Attempting to load CBC sound files...");
		try{
			SoundPoolEntry snd;
			for(String path:PathWeapons){
				snd = event.manager.soundPoolSounds.addSound("cbc/weapons/" + path + ".wav", CBCMod.class.getResource("/cbproject/sounds/weapons/" + path + ".wav"));
				System.out.println("AddSound : " + snd.soundName + " URL: " + snd.soundUrl);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
