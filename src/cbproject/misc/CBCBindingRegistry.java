package cbproject.misc;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class CBCBindingRegistry extends KeyHandler{
	public static final KeyBinding KEY_BINDS[] ={ new KeyBinding("Test",Keyboard.KEY_B)
		};
	public static final boolean IS_REPEATS[] = { false 
		};
	
	public CBCBindingRegistry() {
		super(KEY_BINDS,IS_REPEATS);
		// TODO Auto-generated constructor stub
	}
	
	public CBCBindingRegistry(KeyBinding[] keyBindings) {
		super(keyBindings);
		// TODO Auto-generated constructor stub
	}
	
    public CBCBindingRegistry(KeyBinding[] keyBindings, boolean[] repeatings)
    {
        super(keyBindings);
    }


	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "CrowbarCraft Key Binding";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		// TODO Auto-generated method stub
		System.out.println("Key Down.");
		
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.CLIENT);
	}

}
