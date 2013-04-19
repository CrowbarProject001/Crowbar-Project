package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityARGrenade;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;

/**
 * 9mm Assault Rifle class.
 * Mode I : Bullet, II : AR Grenade.
 * @author WeAthFolD
 *
 */
public class Weapon_9mmAR extends WeaponGeneralBullet {
	
	public Weapon_9mmAR(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm2.itemID, 2);
		setIconCoord(3,2);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setCreativeTab(CBCMod.cct);
		setItemName("weapon_9mmar");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(3,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(51); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		setReloadTime(60);
		setJamTime(10);
		setLiftProps(8 , 2);

	}

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
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
    	
    	if(information.mode == 0){
    		
    		par2World.playSoundAtEntity(par3Entity, getSoundShoot(information.mode), 0.5F, 1.0F);	
    		int mode = information.mode;
    		BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");
    		if(par3Entity instanceof EntityPlayer){
    			doUplift(information, par3Entity);
    			if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode ){
    				par1ItemStack.damageItem( 1 , par3Entity);
    			}
    		}
    		
    	} else {
    		
    		if(par3Entity.capabilities.isCreativeMode  || information.ammoManager.tryConsume(par3Entity, CBCMod.cbcItems.itemAmmo_ARGrenade.itemID));
    			par2World.spawnEntityInWorld(new EntityARGrenade(par2World, par3Entity));
    		
    	}
    	
    	information.setLastTick();
		return;
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
		return "cbc.weapons.hksa";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "cbc.weapons.nmmarr";
	}

	@Override
	public int getShootTime(int mode) {
		return mode == 0 ? 4 : 20;
	}

	@Override
	public double getPushForce(int mode) {
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		return 4;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ? "9mm Bullet mode " : "AR Grenade mode";
	}

}
