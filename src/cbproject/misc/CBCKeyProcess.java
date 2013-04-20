/**
 * 
 */
package cbproject.misc;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.EnumSet;
import java.util.Random;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.configure.Config;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.WeaponGeneralBullet;
import cbproject.elements.items.weapons.Weapon_satchel;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

/**
 * @author WeAthFolD
 *
 */

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
			e.printStackTrace();
		}
		KeyCodes[0].keyCode = Key_ModeChange;
		KeyCodes[0].keyCode = Key_Reload;
		
	}
	
	public static void onModeChange(ItemStack itemStack, InformationSet inf, EntityPlayer player, int maxModes){
		
			modeChange = false;
			if(!player.worldObj.isRemote)
				return;
			
			InformationWeapon sv = inf.clientReference;
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
	
	public static void onReload(ItemStack itemStack, InformationBullet inf, EntityPlayer player){

		if(inf.isReloading)
			reload = false;
		WeaponGeneralBullet item = (WeaponGeneralBullet) inf.itemStack.getItem();
		if(!inf.isReloading){
			System.out.println(inf.player.worldObj.isRemote);
			inf.player.worldObj.playSoundAtEntity(inf.player, item.getSoundReload(inf.mode), 0.5F, 1.0F);
		}
		inf.isReloading = true;
		
		
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
		System.out.println(types);
		if( KeyCodes[0].keyCode == kb.keyCode ){
			modeChange = true;
			return;
		}
		if( KeyCodes[1].keyCode == kb.keyCode){
			reload = true;
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
