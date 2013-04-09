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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.configure.Config;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

/**
 * @author WeAthFolD
 *
 */

@SideOnly(Side.CLIENT)
public class CBCKeyProcess extends KeyHandler{
	
	public int Key_ModeChange, Key_Reload;
	public static Boolean modeChange = false;
	public static Boolean reload = false;
	
	public static KeyBinding KeyCodes[] = {
			new KeyBinding("Mode Change Key", Keyboard.KEY_LCONTROL),
			new KeyBinding("Reload Key", Keyboard.KEY_R)
	};
	
	public static boolean isRepeating[] = {
			false, false
	};
	
	public CBCKeyProcess(Config conf){
		
		super(KeyCodes, isRepeating);
		try{
			Key_ModeChange = conf.GetKeyCode("ModeChange", Keyboard.KEY_LCONTROL);
			Key_Reload = conf.GetKeyCode("Reload", Keyboard.KEY_R);
		} catch(Exception e){
			System.err.println("Something went wrong during loading key informations." + e);
		}
		KeyCodes[0].keyCode = Key_ModeChange;
		KeyCodes[0].keyCode = Key_Reload;
		
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
