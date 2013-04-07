/**
 * 
 */
package cbproject.misc;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

import cbproject.configure.Config;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

/**
 * @author WeAthFolD
 *
 */
public class CBCKeyProcess extends KeyHandler{
	
	public int Key_ModeChange;
	public static Boolean modeChange = false;
	
	public static KeyBinding KeyCodes[] = {
			new KeyBinding("Mode Change Key", Keyboard.KEY_LCONTROL)
	};
	public static boolean isRepeating[] = {
			false
	};
	
	public CBCKeyProcess(Config conf){
		super(KeyCodes, isRepeating);
		try{
			Key_ModeChange = conf.GetKeyCode("ModeChange", Keyboard.KEY_LCONTROL);
		} catch(Exception e){
			System.err.println("Something went wrong during loading key informations." + e);
		}
		KeyCodes[0].keyCode = Key_ModeChange;
	}
	
	public static void onModeChange(InformationSet inf, EntityPlayer player, int maxModes){
		
			modeChange = false;
			InformationWeapon cl = inf.clientReference, sv = inf.serverReference;
			if(player.worldObj.isRemote){
				cl.mode = (maxModes -1 == cl.mode) ? 0 : cl.mode +1;
				sv.mode = cl.mode;
			}
			else sv.mode = cl.mode;
			sv.modeChange = cl.modeChange = true;
			player.sendChatToPlayer("New Mode: " + inf.getProperInf(player.worldObj).mode);
			
	}

	@Override
	public String  getLabel() {
		return "CrowbarCraft Keys";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		System.out.println("Key Down!> <");
		if(KeyCodes[0].keyCode == kb.keyCode){
			System.out.println("Setted");
			modeChange = true;
			return;
		}
		
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
	
}
