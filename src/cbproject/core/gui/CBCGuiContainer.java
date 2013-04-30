package cbproject.core.gui;

import java.util.HashSet;
import cbproject.core.gui.CBCGuiButton.ButtonState;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * LambdaCraft Container GUI,currently have:
 * button functions
 * @author WeAthFolD
 *
 */
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
				if(b.buttonState != ButtonState.INVAILD){
					b.setButtonState(ButtonState.DOWN);
					onButtonClicked(b);
				}
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
					b.setButtonState(ButtonState.IDLE);
			}
		}
    }
	
	public void setButtonState(String buttonName, ButtonState state){
		for(CBCGuiButton b : buttons){
			if(b.buttonName == buttonName){
				b.buttonState = state;
				return;
			}
		}
	}
	
	public ButtonState getButtonState(String name){
		for(CBCGuiButton b : buttons){
			if(b.buttonName == name)
				return b.buttonState;
		}
		return null;
	}
	
	
	public void drawButtons(){
		for(CBCGuiButton b : buttons){
			int x = (width - xSize) / 2;
	        int y = (height - ySize) / 2;
	        int texU = 0, texV = 0;
	        
	        if(b.buttonState == ButtonState.IDLE){
	        	texU = b.idleTexU;
	        	texV = b.idleTexV;
	        } else if (b.buttonState == ButtonState.DOWN){
	        	texU = b.downTexU;
	        	texV = b.downTexV;
	        } else if (b.buttonState == ButtonState.INVAILD){
	        	texU = b.invaildTexU;
	        	texV = b.invaildTexV;
	        }
	        
	        drawTexturedModalRect(x + b.posX, y + b.posY, texU, texV, b.width, b.height);
		}
	}
	
	protected boolean isPointWithin(CBCGuiButton button, int x, int y){
		return this.isPointInRegion(button.posX, button.posY, button.width, button.height, x, y);
	}
	
	public abstract void onButtonClicked(CBCGuiButton button);

}
