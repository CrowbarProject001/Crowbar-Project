/**
 * 
 */
package cn.weaponmod.client;

import java.util.EnumSet;

import cn.weaponmod.WeaponMod;
import cn.weaponmod.api.weapon.IZoomable;
import cn.weaponmod.client.command.Command_SetMode;
import cn.weaponmod.client.keys.Debug_KeyMoving;
import cn.weaponmod.client.keys.Debug_MovingProcessor;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Administrator
 *
 */
@SideOnly(Side.CLIENT)
public class WMClientTickHandler implements ITickHandler {
	
	public final float DEFAULT_UPLIFT_RADIUS = 1.0F, UPLIFT_SPEED = 1.2F, RECOVER_SPEED = .5F;
	
	Minecraft mc = Minecraft.getMinecraft();
	EntityPlayer player;
	private float angleToLift = 0F;
	private float totalUpliftAngle = 0F;
	private boolean lastIsZooming = false, isZooming = false;
	
	public void setUplift() {
		setUplift(DEFAULT_UPLIFT_RADIUS);
	}
	
	public void setUplift(float upliftRad) {
		if(angleToLift < 0)
			angleToLift = 0;
		angleToLift += upliftRad;
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.ITickHandler#tickStart(java.util.EnumSet, java.lang.Object[])
	 */
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		player = mc.thePlayer;
		if(player != null) {
			if(angleToLift > 0) {
				angleToLift -= UPLIFT_SPEED;
				if(totalUpliftAngle < 4.3F * DEFAULT_UPLIFT_RADIUS) {
					player.rotationPitch -= UPLIFT_SPEED;
					totalUpliftAngle += UPLIFT_SPEED;
				}
			} else if(totalUpliftAngle > 0) {
				player.rotationPitch += RECOVER_SPEED;
				totalUpliftAngle -= RECOVER_SPEED;
			} else totalUpliftAngle = 0F;
			
			ItemStack is = player.getCurrentEquippedItem();
			if(is != null) {
				Item item = is.getItem();
				if(item instanceof IZoomable && ((IZoomable) item).isItemZooming(is, player.worldObj, player))
					setZooming(true);
				else setZooming(false);
			} else setZooming(false);
			
			if(isZooming) {
				player.capabilities.setPlayerWalkSpeed(10000F);
			} else if(lastIsZooming) {
				player.capabilities.setPlayerWalkSpeed(.1F);
				lastIsZooming = false;
			}
		}
		
		if(WeaponMod.DEBUG) {
			if(player != null)
				doDebug(player);
		}
	}
	
	private void setZooming(boolean b) {
		lastIsZooming = isZooming;
		isZooming = b;
	}
	
	private void doDebug(EntityPlayer player) {
		ItemStack item = player.getCurrentEquippedItem();
		if(item != null) {
			IItemRenderer render = MinecraftForgeClient.getItemRenderer(item, ItemRenderType.EQUIPPED_FIRST_PERSON);
			if(render != null) {
				Debug_MovingProcessor proc = Debug_KeyMoving.getProcessor(render);
				Command_SetMode.setActiveProcessor(player, proc);
			} else Command_SetMode.setActiveProcessor(player, null);
		} else Command_SetMode.setActiveProcessor(player, null);
	}
	
	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.ITickHandler#tickEnd(java.util.EnumSet, java.lang.Object[])
	 */
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.common.ITickHandler#ticks()
	 */
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
	
	@Override
	public String getLabel() {
		return "MyWeaponary Client Tick Handler";
	}

}
