package cbproject.deathmatch.entities;

import cbproject.api.tile.IUseable;
import cbproject.core.utils.BlockPos;
import cbproject.deathmatch.items.ArmorHEV;
import cbproject.deathmatch.items.ArmorLongjump;
import cbproject.deathmatch.items.ArmorHEV.EnumAttachment;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CBCPlayer extends PlayerBase {

	public static final float LJ_VEL_RADIUS = 1.5F;

	public CBCPlayer(PlayerAPI var1) {
		super(var1);
	}

	@Override
	public void jump() {
		player.localJump();
		ItemStack slotChestplate = player.inventory.armorInventory[2];
		if (slotChestplate != null && player.isSneaking()) {
			Boolean b = false;
			Item item = slotChestplate.getItem();
			if(item instanceof ArmorLongjump)
				b = true;
			else if(item instanceof ArmorHEV){
				ArmorHEV hev = (ArmorHEV) item;
				if(hev.getAttachment(slotChestplate) == EnumAttachment.LONGJUMP)
					b = true;
			}
			if (b) {
				double motionX = -MathHelper.sin(player.rotationYaw / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(player.rotationPitch / 180.0F
								* (float) Math.PI) * LJ_VEL_RADIUS;
				double motionZ = MathHelper.cos(player.rotationYaw / 180.0F
						* (float) Math.PI)
						* MathHelper.cos(player.rotationPitch / 180.0F
								* (float) Math.PI) * LJ_VEL_RADIUS;
				player.addVelocity(motionX, 0.1F, motionZ);
			}
		}
	}
	

}
