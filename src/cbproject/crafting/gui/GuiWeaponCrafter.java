package cbproject.crafting.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cbproject.core.gui.CBCGuiButton;
import cbproject.core.gui.CBCGuiButton.ButtonState;
import cbproject.core.gui.CBCGuiContainer;
import cbproject.core.props.ClientProps;
import cbproject.core.proxy.ClientProxy;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.blocks.BlockWeaponCrafter.CrafterIconType;
import cbproject.crafting.recipes.RecipeWeapons;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

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
    
    public void initGui()
    {
        super.initGui();
        CBCGuiButton up = new CBCGuiButton("up", 111, 19, 7, 6).setidleCoords(208, 13).setDownCoords(220, 13).setInvaildCoords(220, 6),
        		down = new CBCGuiButton("down", 111, 74, 7, 6).setidleCoords(208, 43).setDownCoords(220, 43).setInvaildCoords(208, 6);
        this.addButton(up);
        this.addButton(down);
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
    	String currentPage = StatCollector.translateToLocal("crafter.weapons");
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
        System.out.println(te.iconType);
        drawTexturedModalRect(x + 160, y + 16, 232, dy, 8, 18);
        
        int height = te.heat * 64 / te.MAX_HEAT;
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
		switch(button.buttonName){
		case "up":
		case "down":
			boolean isDown = button.buttonName == "down" ? true: false;
			te.addScrollFactor(isDown);
			this.updateButtonState();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(15);
			DataOutputStream outputStream = new DataOutputStream(bos);
			
			try {
				outputStream.writeShort(te.worldObj.getWorldInfo().getDimension());
				outputStream.writeInt(te.xCoord);
				outputStream.writeInt(te.yCoord);
				outputStream.writeInt(te.zCoord);
		        outputStream.writeBoolean(isDown);
			} catch (Exception ex) {
		        ex.printStackTrace();
			}
			
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = "CBCCrafterScroll";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			PacketDispatcher.sendPacketToServer(packet);
			
		}
	}

}
