package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.AmmoManager;
import cbproject.utils.weapons.InformationEnergy;

public class Weapon_egon extends WeaponGeneralEnergy {

	public Weapon_egon(int par1) {
		
		super(par1, CBCMod.cbcItems.itemAmmo_uranium.itemID, 1);
		setCreativeTab(CBCMod.cct);
		setItemName("weapon_egon");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(1,3);

		setPathShoot("");
		setPathSpecial("cbc.weapon.egon_windup", "cbc.weapons.egon_run", "cbc.weapons.egon_off");
		setPathJam("cbc.weapons.gunjam_a");
		setShootTime(4);
		setDamage(4);
		setOffset(2);
		setPushForce(0.5);
		setJamTime(20);
		
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		InformationEnergy inf = loadInformation(par1ItemStack, par3EntityPlayer).getProperEnergy(par2World);
		processRightClick( inf, par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
		
    }

}
