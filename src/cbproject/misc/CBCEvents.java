package cbproject.misc;

import java.io.File;
import java.net.URL;

import cbproject.CBCMod;
import cbproject.elements.items.armor.ArmorHEVBoot;
import net.minecraft.client.audio.SoundPool;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class CBCEvents {
	
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
		"gauss_chargea",
		"gauss_chargeb",
		"rocketfirea",
		"xbow_fire",
		"xbow_reload",
		"egon_run",
		"egon_windup",
		"egon_off",
		"rocket",
		"rocketfire"
		
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
			e.printStackTrace();
		}
	}
	
	@ForgeSubscribe
	public void onLivingAttack(LivingAttackEvent event){
		
		if(event.source == DamageSource.fall){
			if(event.entity instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer) event.entity;				
				if(player.inventory.armorInventory[0] == null)
					return;
				if(player.inventory.armorInventory[0].getItemName() == CBCMod.cbcItems.armorHEVBoot.getItemName()){
					event.setCanceled(true);
				}
			}
		}
		
	}
	
}
