package cbproject.core.gui;

/**
 * LambdaCraft GUI Button.
 * @author WeAthFolD
 *
 */
public class CBCGuiButton {
	
	public enum ButtonState{
		INVAILD, IDLE, DOWN;
	}
	
	public int posX, posY;
	public int width, height;
	public int idleTexU, idleTexV, downTexU, downTexV, invaildTexU, invaildTexV;
	public String buttonName;
	public ButtonState buttonState;
	
	public CBCGuiButton(String name, int x, int y, int w, int h) {
		buttonName = name;
		posX = x;
		posY = y;
		width = w;
		height = h;
		buttonState = ButtonState.IDLE;
	}
	
	public CBCGuiButton setidleCoords(int u, int v){
		idleTexU = u;
		idleTexV = v;
		return this;
	}
	
	public CBCGuiButton setDownCoords(int u, int v){
		downTexU = u;
		downTexV = v;
		return this;
	}
	
	public CBCGuiButton setInvaildCoords(int u, int v){
		invaildTexU = u;
		invaildTexV = v;
		return this;
	}
	
	public void setButtonState(ButtonState a){
		if(this.buttonState != ButtonState.INVAILD)
			this.buttonState = a;
	}
	
	public void setButtonStateForce(ButtonState a){
			this.buttonState = a;
	}

}
