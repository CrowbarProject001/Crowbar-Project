/**
 * 
 */
package cbproject.misc;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumSet;
import java.util.Random;


import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import cbproject.configure.Config;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.WeaponGeneralBullet;
import cbproject.elements.items.weapons.Weapon_satchel;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/**
 * @author WeAthFolD
 * Generally handle the keyPress event,load key config in .cfg.
 * Not all keys are processed here, some are within item's handle classes.
 */
public class CBCKeyProcess extends KeyHandler{
	
	public static int Key_ModeChange, Key_Reload;
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
			Key_Reload = conf.GetKeyCode("Reload", Keyboard.KEY_R);
		} catch(Exception e){
			e.printStackTrace();
		}
		KeyCodes[0].keyCode = Key_ModeChange;
		
	}
	
	public static void onModeChange(ItemStack itemStack, InformationWeapon inf, EntityPlayer player, int maxModes){
		
			modeChange = false;
			if(!player.worldObj.isRemote)
				return;
			
			InformationWeapon sv = inf;
			sv.mode = (maxModes -1 <= sv.mode) ? 0 : sv.mode +1;
			WeaponGeneral weapon = (WeaponGeneral) itemStack.getItem();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(12);
			DataOutputStream outputStream = new DataOutputStream(bos);
			
			try {
				outputStream.writeDouble(itemStack.getTagCompound().getDouble("uniqueID"));
		        outputStream.writeInt(sv.mode);
			} catch (Exception ex) {
		        ex.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "CBCWeaponMode";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			PacketDispatcher.sendPacketToServer(packet);
			player.sendChatToPlayer("New Mode: " + weapon.getModeDescription(sv.mode));
			
	}

	@Override
	public String  getLabel() {
		return "CrowbarCraft Keys";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		if(tickEnd)
			return;
		if( KeyCodes[0].keyCode == kb.keyCode ){
			modeChange = true;
			return;
		}
		if( KeyCodes[1].keyCode == kb.keyCode){
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
