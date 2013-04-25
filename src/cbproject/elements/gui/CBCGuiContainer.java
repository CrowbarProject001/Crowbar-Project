package cbproject.elements.gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class CBCGuiContainer extends GuiContainer {
	
	private HashSet<CBCGuiButton> buttons;
	
	public CBCGuiContainer(Container par1Container) {
		super(par1Container);
		buttons = new HashSet<CBCGuiButton>();
	}
	
	public void addButton(CBCGuiButton button){
		buttons.add(button);
	}
	
	@Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
		super.mouseClicked(par1, par2, par3);
		for(CBCGuiButton b : buttons){
			if(isPointWithin(b, par1, par2)){
				b.isButtonDown = true;
				onButtonClicked(b);
			}
		}
    }
	
	@Override
	protected void mouseMovedOrUp(int par1, int par2, int par3)
    {
		super.mouseMovedOrUp(par1, par2, par3);
		if(par3 == 0 || par3 == 1){
			for(CBCGuiButton b : buttons){
				if(isPointWithin(b, par1, par2))
					b.isButtonDown = false;
			}
		}
    }
	
	public void drawButtons(){
		for(CBCGuiButton b : buttons){
			int x = (width - xSize) / 2;
	        int y = (height - ySize) / 2;
	        int texU = b.isButtonDown? b.downTexU : b.idleTexU;
	        int texV = b.isButtonDown? b.downTexV : b.idleTexV;
	        drawTexturedModalRect(x + b.posX, y + b.posY, texU, texV, b.width, b.height);
		}
	}
	
	protected boolean isPointWithin(CBCGuiButton button, int x, int y){
		return this.isPointInRegion(button.posX, button.posY, button.width, button.height, x, y);
	}
	
	public abstract void onButtonClicked(CBCGuiButton button);

}
