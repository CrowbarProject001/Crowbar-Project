package cbproject.elements.items.weapons;

import java.util.ArrayList;

import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBulletWeapon;


public class Weapon_9mmhandgun extends WeaponGeneralBullet {
	/*9mmHandgun:两种模式
	 * 模式I(mode = 0 ):低速射击模式，准确度较高
	 * 模式II (mode = 1):高速射击模式，准确度较低
	 * 都是自动模式
	 */

	public Weapon_9mmhandgun(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID);
		
		setItemName("weapon_9mmhandgun");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(2,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(18); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		shootTime = new int [2];
		shootTime[0] = 10;
		shootTime [1]=5;
		jamTime = 10;
		reloadTime = 60;
		this.damage = 3;
		this.offset = 5;
		pathSoundShoot = new String[1];
		pathSoundJam = new String[1];
		pathSoundReload = new String[1];
		
		pathSoundShoot[0] = "cbc.weapons.plgun_c";
		pathSoundJam[0] = "cbc.weapons.gunjam_a";
		pathSoundReload[0] =  "cbc.weapons.nmmclipa";
		
		
		mode = 0; //低速
		
		// TODO Auto-generated constructor stub
	}

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    }
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {

		InformationBulletWeapon information = loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(information, par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		//if event calcelled setdead
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);

	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }

}
