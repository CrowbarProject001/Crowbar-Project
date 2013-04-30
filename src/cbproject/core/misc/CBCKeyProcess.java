/**
 * 
 */
package cbproject.core.misc;

import java.util.EnumSet;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
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
 * Generally handle the keyPress event,load key config in .cfg.
 * Not all keys are processed here, some are within item's handle classes.
 */
public class CBCKeyProcess extends KeyHandler{
	
	public static int Key_ModeChange, Key_Reload;
	
	public static KeyBinding KeyCodes[] = {
			new KeyBinding("Mode Change Key", Keyboard.KEY_LCONTROL),
			new KeyBinding("Reload Key", Keyboard.KEY_R)
	};
	
	public static boolean isRepeating[] = {
			false, false
	};
	
	public CBCKeyProcess(){
		super(KeyCodes, isRepeating);
	}
	
	public static void onModeChange(ItemStack itemStack, InformationWeapon inf, EntityPlayer player, int maxModes){
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
		if( KeyCodes[0].keyCode == kb.keyCode ){
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			ItemStack currentItem = player.inventory.getCurrentItem();
			if(currentItem!= null && currentItem.getItem() instanceof WeaponGeneral){
				WeaponGeneral wpn = (WeaponGeneral) currentItem.getItem();
				InformationWeapon inf = wpn.loadInformation(currentItem, player);
				onModeChange(currentItem, inf, player, wpn.maxModes);
			}
			return;
		}
		if( KeyCodes[1].keyCode == kb.keyCode){
			EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
			ItemStack currentItem = player.inventory.getCurrentItem();
			if(currentItem!= null && currentItem.getItem() instanceof WeaponGeneralBullet){
				WeaponGeneralBullet wpn = (WeaponGeneralBullet) currentItem.getItem();
				InformationBullet inf = wpn.loadInformation(currentItem, player);
				onReload(currentItem, inf, player);
			}
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
