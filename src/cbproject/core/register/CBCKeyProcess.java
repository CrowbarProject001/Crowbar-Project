/**
 * 
 */
package cbproject.core.register;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import cbproject.core.misc.CBCKey;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationWeapon;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;


/**
 * @author WeAthFolD
 * Generally handle the keyPress event.
 */
public class CBCKeyProcess extends KeyHandler{
	
	private static HashMap<Integer, CBCKey>keyProcesses = new HashMap();
	
	private static List<KeyBinding>keyCodes = new ArrayList();
	private static List<Boolean>isRepeating = new ArrayList();
	public static CBCKeyProcess instance;
	
	public CBCKeyProcess(){
		super(toKeyBindingArray(), toBooleanArray());
		if(instance == null)
			instance = this;
	}
	
	private static boolean[] toBooleanArray(){
		boolean[] b = new boolean[isRepeating.size()];
		for(int i = 0; i < b.length; i++){
			b[i] = isRepeating.get(i);
		}
		return b;
	}
	
	private static KeyBinding[] toKeyBindingArray(){
		KeyBinding kb[] = new KeyBinding[keyCodes.size()];
		for(int i = 0; i < kb.length; i++){
			kb[i] = keyCodes.get(i);
		}
		return kb;
	}
	
	public static void addKey(KeyBinding key, boolean isRep, CBCKey process){
		if(instance != null)
			throw new WrongUsageException("Trying to add a key after the process is instanted.");
		
		keyCodes.add(key);
		isRepeating.add(isRep);
		keyProcesses.put(key.keyCode, process);
		System.out.println(keyCodes.size() == isRepeating.size());
	}
	
	private static void onModeChange(ItemStack itemStack, InformationWeapon inf, EntityPlayer player, int maxModes){
			if(!player.worldObj.isRemote)
				return;

			WeaponGeneral wpn = (WeaponGeneral) itemStack.getItem();
			int stackInSlot = -1;
			for(int i = 0; i < player.inventory.mainInventory.length; i++){
				if(player.inventory.mainInventory[i] == itemStack){
					stackInSlot = i;
					break;
				}
			}
			if(stackInSlot == -1)
				return;
			
			if(inf == null)
				return;
			inf.mode = (maxModes -1 <= inf.mode) ? 0 : inf.mode +1;
			player.sendChatToPlayer(StatCollector.translateToLocal("mode.new") + ": \u00a74" + StatCollector.translateToLocal(wpn.getModeDescription(inf.mode)));
			NetDeathmatch.sendModePacket(stackInSlot, (short) 0, inf.mode);
			
	}

	public static void onReload(ItemStack is, InformationBullet inf, EntityPlayer player){
		if(!player.worldObj.isRemote)
			return;

		WeaponGeneralBullet wpn = (WeaponGeneralBullet) is.getItem();
		int stackInSlot = -1;
		for(int i = 0; i < player.inventory.mainInventory.length; i++){
			if(player.inventory.mainInventory[i] == is){
				stackInSlot = i;
				break;
			}
		}
		if(stackInSlot == -1)
			return;
		
		if(wpn.onSetReload(is, player))
			NetDeathmatch.sendModePacket(stackInSlot, (short) 1, 0);
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
		if(keyProcesses.containsKey(kb.keyCode))
			keyProcesses.get(kb.keyCode).onKeyDown();
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if(tickEnd)
			return;
		if(keyProcesses.containsKey(kb.keyCode))
			keyProcesses.get(kb.keyCode).onKeyUp();
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
}
