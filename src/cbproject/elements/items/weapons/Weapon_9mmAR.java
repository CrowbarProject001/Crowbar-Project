package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBulletWeapon;

public class Weapon_9mmAR extends WeaponGeneralBullet {
	
	public Weapon_9mmAR(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm2.itemID);
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
		
		shootTime = new int [1];
		shootTime[0] = 3;
		reloadTime = 60;
		jamTime = 10;
		
		this.damage = 3;
		this.offset = 8;
		
		pathSoundShoot = new String[1];
		pathSoundJam = new String[1];
		pathSoundReload = new String[1];
		
		pathSoundShoot[0] = "cbc.weapons.hksa";
		pathSoundJam[0] = "cbc.weapons.gunjam_a";
		pathSoundReload[0] = "cbc.weapons.nmmarr";
		
		mode = 0; //子弹+自动
	}

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    }

	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		InformationBulletWeapon information = super.loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(information, par1ItemStack, par2World, par3EntityPlayer);

		return par1ItemStack;
		
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

}
