package cbproject.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.proxy.ClientProxy;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationSet;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * .357 Magnum weapon class.
 * @author WeAthFolD
 *
 */
public class Weapon_357 extends WeaponGeneralBullet {

	public Weapon_357(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_357.itemID, 1);

		setUnlocalizedName("weapon_357");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(7);
		setNoRepair();
			
		setReloadTime(70);
		setJamTime(20);
		setLiftProps(25, 3);
		
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:weapon_357");
    }
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		processRightClick(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
    }
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }

	@Override
	public String getSoundShoot(int mode) {
		String[] shoot  = { "cbc.weapons.pyt_shota", "cbc.weapons.pyt_shotb"};
		int index = (int) (Math.random() * 2);
		return shoot[index];
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapon.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "cbc.weapons.pyt_reloada";
	}

	@Override
	public int getShootTime(int mode) {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public double getPushForce(int mode) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getDamage(int mode) {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public int getOffset(int mode) {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public String getModeDescription(int mode) {
		return "mode.357";
	}

}
