package cn.lambdacraft.deathmatch.items.ammos;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.deathmatch.client.HEVRenderingUtils;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Ammo_rpg extends ItemAmmo {

	public Ammo_rpg(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setIAndU("ammo_rpg");
		setMaxStackSize(10);
	}
	
}
