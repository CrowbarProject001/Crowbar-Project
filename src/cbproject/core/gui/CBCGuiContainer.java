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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.lwjgl.opengl.GL11;

import cbproject.core.gui.CBCGuiButton.ButtonState;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * @author WeAthFolD
 * LambdaCraft的GUI Container，目前具有：
 * 按钮功能
 * 区域Tip功能
 */
public abstract class CBCGuiContainer extends GuiContainer {
	
	/**
	 * GUI按钮列表。
	 */
	private HashSet<CBCGuiButton> buttons;
	
	public CBCGuiContainer(Container par1Container) {
		super(par1Container);
		buttons = new HashSet<CBCGuiButton>();
	}
	
	/**
	 * 添加一个按钮。
	 * @param button
	 */
	public void addButton(CBCGuiButton button){
		buttons.add(button);
	}

	/**
	 * 处理每个按钮按下时行为的函数，在子类实现它来做些什么。
	 * @param button 被按下的按钮
	 */
	public abstract void onButtonClicked(CBCGuiButton button);
	
	/**
	 * 设置某一个按钮的状态。
	 * @param buttonName 按钮名称
	 * @param state 状态
	 */
	public void setButtonState(String buttonName, ButtonState state){
		getButton(buttonName).buttonState = state;
	}
	
	/**
	 * @param buttonName
	 * @param tip
	 * @return
	 */
	public boolean setButtonTip(String buttonName, IGuiTip tip){
		getButton(buttonName).tooltip = tip;
		return true;
	}
	
	/**
	 * 获取某一按钮的状态
	 * @param name 按钮名称
	 * @return 对应按钮状态
	 */
	public ButtonState getButtonState(String name){
		return getButton(name).buttonState;
	}
	
	/**
	 * 绘制按钮，请务必在drawGuiBackgroundLayer()中调用。
	 */
	public void drawElements(){
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
	
	@Override
	/**
	 * TODO:未完成
	 * 绘制GUI上层图像。请务必在子类中调用它以绘制Tip。
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		IGuiTip currentTip = null;
		for(CBCGuiButton b : buttons){
			if(isPointWithin(b, par1, par2) && b.hasToolTip()){
				currentTip = b.tooltip;
			}
		}
		if(currentTip != null){
			int x = xSize / 2, y = ySize / 2;
			GL11.glColor3f(0.0F,0.0F,0.0F);
			boolean drawHead = currentTip.getHeadText() != "";
			if(drawHead){
				fontRenderer.drawString(currentTip.getTip(), par1 - x, par2 - y, 0x000000);
			} else {
				fontRenderer.drawString(currentTip.getTip(), par1 - x, par2 - y, 0x000000);
			}
		}
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
	

	
	protected boolean isPointWithin(CBCGuiButton button, int x, int y){
		return this.isPointInRegion(button.posX, button.posY, button.width, button.height, x, y);
	}
	
	protected CBCGuiButton getButton(String name){
		for(CBCGuiButton b : buttons){
			if(b.buttonName == name)
				return b;
		}
		return null;
	}

}
