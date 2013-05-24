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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.crafting.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cbproject.core.gui.CBCGuiButton;
import cbproject.core.gui.CBCGuiButton.ButtonState;
import cbproject.core.gui.CBCGuiContainer;
import cbproject.core.gui.CBCGuiPart;
import cbproject.core.props.ClientProps;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.network.NetCrafterClient;
import cbproject.crafting.recipes.RecipeWeapons;

/**
 * 武器合成机和高级武器合成机的Gui类。
 * @author WeAthFolD
 */
public class GuiWeaponCrafter extends CBCGuiContainer {

	public TileEntityWeaponCrafter te;
	public InventoryPlayer inv;
	
	public GuiWeaponCrafter(InventoryPlayer inventoryPlayer,
            TileEntityWeaponCrafter tileEntity) {
		super(new ContainerWeaponCrafter(inventoryPlayer, tileEntity));
		te = tileEntity;
		inv = inventoryPlayer;
		this.xSize = 200;
		this.ySize = 250;
	}
	
	@Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
		super.mouseClicked(par1, par2, par3);
    }
    
	@Override
    public void initGui()
    {
        super.initGui();
        CBCGuiPart up = new CBCGuiButton("up", 111, 19, 7, 6).setDownCoords(220, 13).setInvaildCoords(220, 6).setTextureCoords(208, 13),
        		down = new CBCGuiButton("down", 111, 74, 7, 6).setDownCoords(220, 43).setInvaildCoords(208, 6).setTextureCoords(208, 43),
        		left = new CBCGuiButton("left", 5, 2, 5, 6).setDownCoords(220, 53).setInvaildCoords(245, 53).setTextureCoords(210, 53),
        		right = new CBCGuiButton("right", 190, 2, 5, 6).setDownCoords(220, 63).setInvaildCoords(245, 63).setTextureCoords(210, 63);
        this.addButton(up);
        this.addButton(down);
        addButton(left);
        addButton(right);
        this.updateButtonState();
    }
	
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	String storage = StatCollector.translateToLocal("crafter.storage");
    	String currentPage = StatCollector.translateToLocal(!te.isAdvanced ? RecipeWeapons.pageDescriptions[te.page] : RecipeWeapons.advPageDescriptions[te.page]);
        this.fontRenderer.drawString(storage, 8, 88, 4210752);
        fontRenderer.drawString(currentPage, 100 - fontRenderer.getStringWidth(currentPage) / 2, 1, 4210752);
    }
	
	public void updateButtonState(){
		int place = isAtTopOrBottom();
        if(place == 1){
        	this.setButtonState("up", ButtonState.INVAILD);
        } else if(this.getButtonState("up") == ButtonState.INVAILD)
        	this.setButtonState("up", ButtonState.IDLE);
        
        if(place == -1){
        	this.setButtonState("down", ButtonState.INVAILD);
        } else if(this.getButtonState("down") == ButtonState.INVAILD)
        	this.setButtonState("down", ButtonState.IDLE);
        
        if(te.page == 0){
        	this.setButtonState("left", ButtonState.INVAILD);
        } else if(this.getButtonState("left") == ButtonState.INVAILD)
        	this.setButtonState("left", ButtonState.IDLE);
        if(te.page == RecipeWeapons.recipes.length - 1){
        	this.setButtonState("right", ButtonState.INVAILD);
        } else if(this.getButtonState("right") == ButtonState.INVAILD)
        	this.setButtonState("right", ButtonState.IDLE);
        	
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ClientProps.GUI_WEAPONCRAFTER_PATH);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        this.drawElements();
        int dy = 0;
        if(te.iconType == CrafterIconType.NOMATERIAL)
            dy = 60;
        else if(te.iconType == CrafterIconType.CRAFTING)
        	dy = 16;
        else dy = 38;
        drawTexturedModalRect(x + 160, y + 16, 232, dy, 8, 18);
        
        int height = te.heat * 64 / TileEntityWeaponCrafter.MAX_HEAT;
        if(height > 0){
        	drawTexturedModalRect(x + 174, y + 78 - height, 232, 150 - height, 8, height);
        }
        if(te.isCrafting){
        	if(te.currentRecipe != null){
        		height = te.currentRecipe.heatRequired * 64 / TileEntityWeaponCrafter.MAX_HEAT;
        		drawTexturedModalRect(x + 173, y + 77 - height, 201, 1, 6, 3);
        	}
        }
        if(te.maxBurnTime != 0){
        	height = te.burnTimeLeft * 22 / te.maxBurnTime;
        	if(height > 0)
        		drawTexturedModalRect(x + 156, y + 78 - height, 211, 101 - height, 16, height);
        }
	}
	
	//top 1, bottom -1, neither 0
	public int isAtTopOrBottom(){
		if(te.scrollFactor == RecipeWeapons.getRecipeLength(te.page) - 3)
			return -1;
		if(te.scrollFactor == 0)
			return 1;
		return 0;
	}

	@Override
	public void onButtonClicked(CBCGuiButton button) {
		if(button.name == "up" || button.name =="down"){
			boolean isDown = button.name == "down" ? true: false;
			te.addScrollFactor(isDown);
			NetCrafterClient.sendCrafterPacket((short)Minecraft.getMinecraft().theWorld.getWorldInfo().getDimension(), (short) 0, te.xCoord, te.yCoord, te.zCoord, isDown);
			this.updateButtonState();
			return;
		}
		if(button.name == "left" || button.name == "right"){
			boolean isForward = button.name == "right" ? true: false;
			te.addPage(isForward);
			NetCrafterClient.sendCrafterPacket((short)Minecraft.getMinecraft().theWorld.getWorldInfo().getDimension(), (short) 1, te.xCoord, te.yCoord, te.zCoord, isForward);
			this.updateButtonState();
			return;
		}
	}

}
