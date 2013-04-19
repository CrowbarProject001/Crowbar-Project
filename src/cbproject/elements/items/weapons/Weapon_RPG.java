package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityRocket;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_RPG extends WeaponGeneralBullet {

	public Weapon_RPG(int par1) {
		super(par1,CBCMod.cbcItems.itemAmmo_rpg.itemID, 1);
		
		setItemName("weapon_rpg");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(7,2);
		setCreativeTab(CBCMod.cct);
		setMaxDamage(2);
		
		setJamTime(20);
		setLiftProps(20, 2);
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
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){
		information.setLastTick();
		par2World.playSoundAtEntity(par3Entity, getSoundShoot(information.mode), 0.5F, 1.0F);
		par2World.spawnEntityInWorld(new EntityRocket(par2World, par3Entity));
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 400; //20s
    }

	@Override
	public String getSoundShoot(int mode) {
		return "cbc.weapons.rocketfire";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "";
	}

	@Override
	public int getShootTime(int mode) {
		return 40;
	}

	@Override
	public double getPushForce(int mode) {
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		return 0;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return "RPG Normal mode";
	}
		 

}
