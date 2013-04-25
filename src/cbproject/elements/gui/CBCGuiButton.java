package cbproject.elements.gui;

public class CBCGuiButton {
	
	public int posX, posY;
	public int width, height;
	public int idleTexU, idleTexV, downTexU, downTexV;
	public String buttonName;
	public boolean isButtonDown;
	
	public CBCGuiButton(String name, int x, int y, int w, int h) {
		buttonName = name;
		posX = x;
		posY = y;
		width = w;
		height = h;
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

}
