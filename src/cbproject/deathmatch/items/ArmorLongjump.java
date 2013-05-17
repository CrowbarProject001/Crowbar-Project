package cbproject.deathmatch.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumHelper;

public class ArmorLongjump extends ItemArmor {
	
	public static int reductionAmount[] = {0, 0, 0, 0};
	public static EnumArmorMaterial material = EnumHelper.addArmorMaterial("armorLJ", 0, reductionAmount, 0);
	
	public ArmorLongjump(int par1, int armorType) {
		super(par1, material, 2, armorType);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("longjump");
	}
	
	
    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param world
     * @param player
     * @param itemStack
     */
	@Override
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {

    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:longjump");
    }

}
