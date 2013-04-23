package cbproject.elements.inventory.containers;

import org.lwjgl.opengl.GL11;

import cbproject.elements.blocks.BlockWeaponCrafter.TileEntityWeaponCrafter;
import cbproject.proxy.ClientProxy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;

public class GuiWeaponCrafter extends GuiContainer {

	public GuiWeaponCrafter(InventoryPlayer inventoryPlayer,
            TileEntityWeaponCrafter tileEntity) {
		super(new ContainerWeaponCrafter(inventoryPlayer, tileEntity));
		this.ySize = 215;
	}

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(ClientProxy.GUI_WEAPONCRAFTER_PATH);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
