package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_crossbow extends WeaponGeneralBullet {

	public Weapon_crossbow(int par1) {
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID, 2);
		
		setItemName("weapon_crossbow");
		
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(6,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(9); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		String[] shoot  = { "xbow_fire"};
		String[] reload = { "xbow_reload" };
		String[] jam = { ""};
		int shootTime[] = {20, 20}, dmg[] = { 7, 7}, off[] = { 2, 2};
		double push[] = {2, 2};
		
		setPathShoot(shoot);
		setPathJam(jam);
		setPathReload(reload);
		
		setShootTime(shootTime);
		setReloadTime(7);
		setJamTime(0);
		
		setPushForce(push);
		setDamage(dmg);
		setOffset(off);
		
		this.setLiftProps(30, 5);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub
	}

}
