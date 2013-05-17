/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
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
	
	/**
	 * 按钮位置。
	 */
	public int posX, posY;
	
	/**
	 * 按钮的大小。
	 */
	public int width, height;
	
	/*
	 * 三种状态对应贴图的U、V。
	 */
	public int idleTexU, idleTexV, downTexU, downTexV, invaildTexU, invaildTexV;
	public String buttonName;
	/*
	 * 当前的按钮状态。
	 */
	public ButtonState buttonState;
	
	public CBCGuiButton(String name, int x, int y, int w, int h) {
		buttonName = name;
		posX = x;
		posY = y;
		width = w;
		height = h;
		buttonState = ButtonState.IDLE;
	}
	
	/**
	 * 设置按钮空闲时的U、V位置（像素，左上角）
	 * @param u
	 * @param v
	 * @return 当前按钮
	 */
	public CBCGuiButton setidleCoords(int u, int v){
		idleTexU = u;
		idleTexV = v;
		return this;
	}
	
	/**
	 * 设置按钮按下时的U、V位置（像素，左上角）
	 * @param u
	 * @param v
	 * @return 当前按钮
	 */
	public CBCGuiButton setDownCoords(int u, int v){
		downTexU = u;
		downTexV = v;
		return this;
	}
	
	/**
	 * 设置按钮无法使用时的U、V位置（像素，左上角）
	 * @param u
	 * @param v
	 * @return 当前按钮
	 */
	public CBCGuiButton setInvaildCoords(int u, int v){
		invaildTexU = u;
		invaildTexV = v;
		return this;
	}
	
	public void setButtonState(ButtonState a){
		if(this.buttonState != ButtonState.INVAILD)
			this.buttonState = a;
	}
	
	/**
	 * 强制设置按钮状态。
	 * @param a 按钮状态
	 */
	public void setButtonStateForce(ButtonState a){
			this.buttonState = a;
	}

}
