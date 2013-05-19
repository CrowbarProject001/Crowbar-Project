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
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;


/**
 * @author WeAthFolD
 * Generally handle the keyPress event.
 */
public class CBCKeyProcess extends KeyHandler{
	
	private static HashMap<Integer, IKeyProcess>keyProcesses = new HashMap();
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
	
	public static void addKey(KeyBinding key, boolean isRep, IKeyProcess process){
		if(instance != null)
			throw new WrongUsageException("Trying to add a key after the process is instanted.");
		
		keyCodes.add(key);
		isRepeating.add(isRep);
		keyProcesses.put(key.keyCode, process);
	}
	
	@Override
	public String  getLabel() {
		return "LambdaCraft Keys";
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
