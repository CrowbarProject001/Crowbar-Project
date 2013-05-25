package cbproject.deathmatch.entities;

import cbproject.deathmatch.items.ArmorLongjump;
import net.minecraft.item.ItemStack;
import net.minecraft.src.PlayerAPI;
import net.minecraft.src.PlayerBase;
import net.minecraft.util.MathHelper;

public class CBCPlayer extends PlayerBase {

	public static final float LJ_VEL_RADIUS = 1.5F;
	
	public CBCPlayer(PlayerAPI var1) {
		super(var1);
	}
	
	@Override
	public void jump(){
		player.localJump();
		ItemStack slotChestplate = player.inventory.armorInventory[2];
		if(slotChestplate != null && slotChestplate.getItem() instanceof ArmorLongjump){
			if(player.isSneaking()){
		        double motionX = -MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * LJ_VEL_RADIUS;
		        double motionZ = MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI) * LJ_VEL_RADIUS;
		        player.addVelocity(motionX, 0.1F, motionZ);
			}
		}
	}
	


}
