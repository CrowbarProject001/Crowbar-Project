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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
	 * GUI元素列表。
	 */
	private HashSet<CBCGuiPart> elements;
	
	public CBCGuiContainer(Container par1Container) {
		super(par1Container);
		elements = new HashSet<CBCGuiPart>();
	}
	
	/**
	 * 添加一个按钮。
	 * @param part
	 */
	public void addElement(CBCGuiPart part){
		elements.add(part);
	}
	
	public void addElements(CBCGuiPart... parts){
		for(CBCGuiPart p : parts)
			addElement(p);
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
	public boolean setElementTip(String buttonName, IGuiTip tip){
		getElement(buttonName).tip = tip;
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
		for(CBCGuiPart e : elements){
			if(!e.doesDraw)
				continue;
			if(e instanceof CBCGuiButton){
				CBCGuiButton b = (CBCGuiButton) e;
				int x = (width - xSize) / 2;
		        int y = (height - ySize) / 2;
		        int texU = 0, texV = 0;
		        
		        if(b.buttonState == ButtonState.IDLE){
		        	texU = b.texU;
		        	texV = b.texV;
		        } else if (b.buttonState == ButtonState.DOWN){
		        	texU = b.downTexU;
		        	texV = b.downTexV;
		        } else if (b.buttonState == ButtonState.INVAILD){
		        	texU = b.invaildTexU;
		        	texV = b.invaildTexV;
		        }
		        
		        drawTexturedModalRect(x + b.posX, y + b.posY, texU, texV, b.width, b.height);
			} else {
				int x = (width - xSize) / 2;
		        int y = (height - ySize) / 2;
		        int texU = e.texU, texV = e.texV;
				drawTexturedModalRect(x + e.posX, y + e.posY, texU, texV, e.width, e.height);
			}
		}
	}
	
	@Override
	/*
	 * 绘制GUI上层图像。请务必在子类中调用它以绘制Tip。
	 */
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		super.drawGuiContainerForegroundLayer(par1, par2);
		IGuiTip currentTip = null;
		for(CBCGuiPart b : elements){
			if(isPointWithin(b, par1, par2) && b.hasToolTip()){
				currentTip = b.tip;
			}
		}
		if(currentTip != null){
			boolean drawHead = currentTip.getHeadText() != "";
			List<String> list = new ArrayList();
			if(drawHead){
				list.add(currentTip.getHeadText());
			}
			int x = (width - xSize)/2, y = (height - ySize)/2;
			list.add(currentTip.getTip());
			super.func_102021_a(list, par1 - x, par2 - y);
		}
	}
	
	@Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
		super.mouseClicked(par1, par2, par3);
		for(CBCGuiPart e : elements){
			if(!(e instanceof CBCGuiButton))
				continue;
			CBCGuiButton b = (CBCGuiButton) e;
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
			for(CBCGuiPart b : elements){
				if(!(b instanceof CBCGuiButton))
					continue;
				if(isPointWithin(b, par1, par2))
					((CBCGuiButton)b).setButtonState(ButtonState.IDLE);
			}
		}
		
    }
	

	
	protected boolean isPointWithin(CBCGuiPart element, int x, int y){
		return this.isPointInRegion(element.posX, element.posY, element.width, element.height, x, y);
	}
	
	protected CBCGuiButton getButton(String name){
		CBCGuiPart elem = getElement(name);
		if(elem != null && elem instanceof CBCGuiButton)
			return (CBCGuiButton) elem;
		return null;
	}
	
	protected CBCGuiPart getElement(String name){
		for(CBCGuiPart b : elements){
			if(b.name == name)
				return b;
		}
		return null;
	}

}
