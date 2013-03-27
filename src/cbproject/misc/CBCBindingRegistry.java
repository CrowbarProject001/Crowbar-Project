package cbproject.misc;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class CBCBindingRegistry extends KeyHandler{

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
		return null;
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
		return null;
	}

}
