package cbproject.elements.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import cbproject.elements.blocks.BlockWeaponCrafter.TileEntityWeaponCrafter;
import cbproject.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;

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
		System.out.println(par1 + " " + par2 + " " + par3);
    }
    
    public void initGui()
    {
        super.initGui();
        CBCGuiButton up = new CBCGuiButton("up", 111, 19, 7, 6).setidleCoords(208, 13).setDownCoords(220, 13),
        		down = new CBCGuiButton("down", 111, 74, 7, 6).setidleCoords(208, 43).setDownCoords(220, 43);
        this.addButton(up);
        this.addButton(down);
    }
	
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
	@Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.fontRenderer.drawString("Weapon Storage", 8, 86, 4210752);
        fontRenderer.drawString("Weapon Forger", 100 - fontRenderer.getStringWidth("Weapon Forger") / 2, 1, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ClientProxy.GUI_WEAPONCRAFTER_PATH);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        this.drawButtons();
	}

	@Override
	public void onButtonClicked(CBCGuiButton button) {
		switch(button.buttonName){
		case "up":
		case "down":
			boolean isDown = button.buttonName == "down" ? true: false;
			
			te.addScrollFactor(isDown);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(13);
			DataOutputStream outputStream = new DataOutputStream(bos);
			
			try {
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
			System.out.println(FMLCommonHandler.instance().getSide());
			
		}
	}

}
