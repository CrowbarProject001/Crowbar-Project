package cbproject.crafting.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cbproject.core.gui.CBCGuiButton;
import cbproject.core.gui.CBCGuiButton.ButtonState;
import cbproject.core.gui.CBCGuiContainer;
import cbproject.core.props.ClientProps;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.network.NetWeaponCrafter;
import cbproject.crafting.recipes.RecipeWeapons;

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
	
    /**
     * Called when the mouse is clicked.
     */
	@Override
    protected void mouseClicked(int par1, int par2, int par3)
    {
		super.mouseClicked(par1, par2, par3);
    }
    
	@Override
    public void initGui()
    {
        super.initGui();
        CBCGuiButton up = new CBCGuiButton("up", 111, 19, 7, 6).setidleCoords(208, 13).setDownCoords(220, 13).setInvaildCoords(220, 6),
        		down = new CBCGuiButton("down", 111, 74, 7, 6).setidleCoords(208, 43).setDownCoords(220, 43).setInvaildCoords(208, 6),
        		left = new CBCGuiButton("left", 5, 2, 5, 6).setidleCoords(210, 53).setDownCoords(220, 53).setInvaildCoords(245, 53),
        		right = new CBCGuiButton("right", 190, 2, 5, 6).setidleCoords(210, 63).setDownCoords(220, 63).setInvaildCoords(245, 63);
        this.addButton(up);
        this.addButton(down);
        addButton(left);
        addButton(right);
        this.updateButtonState();
    }
	
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	String storage = StatCollector.translateToLocal("crafter.storage");
    	String currentPage = StatCollector.translateToLocal(RecipeWeapons.pageDescriptions[te.page]);
        this.fontRenderer.drawString(storage, 8, 86, 4210752);
        fontRenderer.drawString(currentPage, 100 - fontRenderer.getStringWidth("Weapon Forger") / 2, 1, 4210752);
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
        if(te.page == RecipeWeapons.recipes.length){
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
        this.drawButtons();
        int dy = 0;
        if(te.iconType == CrafterIconType.NOMATERIAL)
            dy = 60;
        else if(te.iconType == CrafterIconType.CRAFTING)
        	dy = 16;
        else dy = 38;
        drawTexturedModalRect(x + 160, y + 16, 232, dy, 8, 18);
        
        int height = te.heat * 64 / TileEntityWeaponCrafter.MAX_HEAT;
        if(height > 0)
        	drawTexturedModalRect(x + 174, y + 78 - height, 232, 150 - height, 8, height);
        if(te.maxBurnTime != 0){
        	height = te.burnTimeLeft * 24 / te.maxBurnTime;
        	if(height > 0)
        		drawTexturedModalRect(x + 156, y + 78 - height, 211, 103 - height, 17, height);
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
		if(button.buttonName == "up" || button.buttonName =="down"){
			boolean isDown = button.buttonName == "down" ? true: false;
			te.addScrollFactor(isDown);
			NetWeaponCrafter.sendCrafterPacket((short)te.worldObj.getWorldInfo().getDimension(), (short) 0, te.xCoord, te.yCoord, te.zCoord, isDown);
			this.updateButtonState();
			return;
		}
		if(button.buttonName == "left" || button.buttonName == "right"){
			boolean isForward = button.buttonName == "right" ? true: false;
			te.addPage(isForward);
			NetWeaponCrafter.sendCrafterPacket((short)te.worldObj.getWorldInfo().getDimension(), (short) 1, te.xCoord, te.yCoord, te.zCoord, isForward);
			this.updateButtonState();
			return;
		}
	}

}
