/**
 * 
 */
package cbproject.misc;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import cbproject.configure.Config;
import cbproject.utils.weapons.InformationWeapon;

/**
 * @author WeAthFolD
 *
 */
public class CBCKeyProcess {
	
	public int Key_ModeChange;
	public CBCKeyProcess(Config conf){
		try{
			Key_ModeChange = conf.GetKeyCode("ModeChange", Keyboard.KEY_LCONTROL);
		} catch(Exception e){
			System.err.println("Something went wrong during loading key informations." + e);
		}
	}
	
	public  void onModeChange(InformationWeapon inf, EntityPlayer player, int maxModes){
			player.sendChatToPlayer("Attempting to change mode...");
			inf.mode = (maxModes -1 == inf.mode) ? 0 : inf.mode +1;
	}
	
}
