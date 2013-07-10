package cn.lambdacraft.deathmatch.flashlight;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import java.util.Iterator;

import org.bouncycastle.asn1.eac.Flags;

import cn.lambdacraft.deathmatch.register.DMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumSkyBlock;

public class ClientTickHandler implements ITickHandler {
	public static int ticks = 0;

	/* the flag if the flashlight button has been pressed */
	public boolean flag = false;

	public void tickStart(EnumSet var1, Object... var2) {
	}

	public void tickEnd(EnumSet var1, Object... var2) {
		if (var1.equals(EnumSet.of(TickType.RENDER))) {
			this.onRenderTick();
		} else if (var1.equals(EnumSet.of(TickType.CLIENT))) {
			GuiScreen var3 = Minecraft.getMinecraft().currentScreen;

			if (var3 != null) {
				this.onTickInGUI(var3);
			} else {
				this.onTickInGame();
			}
		}
	}

	public EnumSet ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	public String getLabel() {
		return "Flashlight.ClientTickHandler";
	}

	public void onRenderTick() {
	}

	public void onTickInGUI(GuiScreen var1) {
	}

	public void onTickInGame() {
		++ticks;
		boolean var1 = false;
		int var5;

		if (ticks % 10 == 0) {
			LightValue.resetAll2();
			LightValue.emptyData2();
			Iterator var2 = Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().thePlayer.boundingBox.expand(64.0D, 64.0D, 64.0D)).iterator();
			while (var2.hasNext()) {
				Object var3 = var2.next();

				if (var3 instanceof EntityLiving) {
					EntityLiving var4 = (EntityLiving) var3;
					ItemStack armorStack = null;
					try {
						armorStack = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[0];
					} catch (Exception e) {

					}

					if (flag && armorStack.itemID == DMItems.armorHEVHelmet.itemID && var4.rayTrace(200.0D, 1.0F) != null) {
						var5 = var4.rayTrace(200.0D, 1.0F).blockX;
						int var6 = var4.rayTrace(200.0D, 1.0F).blockY;
						int var7 = var4.rayTrace(200.0D, 1.0F).blockZ;
						int var8 = var4.rayTrace(200.0D, 1.0F).sideHit;

						if (var8 == 0) {
							--var6;
						} else if (var8 == 1) {
							++var6;
						} else {
							if (var8 == 2) {
								--var7;
							}

							if (var8 == 3) {
								++var7;
							}

							if (var8 == 4) {
								--var5;
							}

							if (var8 == 5) {
								++var5;
							}
						}

						LightValue.addData2(var5, var6, var7);
						Minecraft.getMinecraft().theWorld.setLightValue(EnumSkyBlock.Block, var5, var6, var7, 15);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6 + 1, var7);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6 - 1, var7);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5 - 1, var6, var7);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5 + 1, var6, var7);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6, var7 - 1);
						Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var5, var6, var7 + 1);
					}
				}
			}
		}

		if (ticks % 2 == 0) {
			LightValue.resetAll();
			LightValue.emptyData();
			ItemStack armorStack = null;
			try {
				if (Minecraft.getMinecraft().thePlayer.inventory.armorInventory[3] == null)
					return;
				armorStack = Minecraft.getMinecraft().thePlayer.inventory.armorInventory[3];
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (flag && armorStack.itemID == DMItems.armorHEVHelmet.itemID && Minecraft.getMinecraft().thePlayer.rayTrace(200.0D, 1.0F) != null)
			{
				int var9 = Minecraft.getMinecraft().thePlayer.rayTrace(200.0D, 1.0F).blockX;
				int var10 = Minecraft.getMinecraft().thePlayer.rayTrace(200.0D, 1.0F).blockY;
				int var11 = Minecraft.getMinecraft().thePlayer.rayTrace(200.0D, 1.0F).blockZ;
				var5 = Minecraft.getMinecraft().thePlayer.rayTrace(200.0D, 1.0F).sideHit;

				if (var5 == 0) {
					--var10;
				} else if (var5 == 1) {
					++var10;
				} else {
					if (var5 == 2) {
						--var11;
					}

					if (var5 == 3) {
						++var11;
					}

					if (var5 == 4) {
						--var9;
					}

					if (var5 == 5) {
						++var9;
					}
				}

				LightValue.addData(var9, var10, var11);
				Minecraft.getMinecraft().theWorld.setLightValue(EnumSkyBlock.Block, var9, var10, var11, 15);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9, var10 + 1, var11);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9, var10 - 1, var11);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9 - 1, var10, var11);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9 + 1, var10, var11);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9, var10, var11 - 1);
				Minecraft.getMinecraft().theWorld.updateLightByType(EnumSkyBlock.Block, var9, var10, var11 + 1);
			}
		}
	}
}
