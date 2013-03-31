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
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;


public class Weapon_9mmhandgun extends WeaponGeneralBullet {
	/*9mmHandgun:两种模式
	 * 模式I(mode = 0 ):低速射击模式，准确度较高
	 * 模式II (mode = 1):高速射击模式，准确度较低
	 * 都是自动模式
	 */

	public Weapon_9mmhandgun(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID, 2);
		
		setItemName("weapon_9mmhandgun");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(2,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(18); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		String[] shoot  = { "cbc.weapons.plgun_c", "cbc.weapons.plgun_c"};
		String[] reload = { "cbc.weapons.nmmclipa", "cbc.weapons.nmmclipa" };
		String[] jam = { "cbc.weapons.gunjam_a" , "cbc.weapons.gunjam_a"};
		int shootTime[] = {10, 5}, dmg[] = { 3, 3}, off[] = { 2, 8};
		double push[] = {0.5, 0.5};
		
		setPathShoot(shoot);
		setPathJam(jam);
		setPathReload(reload);
		
		setShootTime(shootTime);
		setReloadTime(60);
		setJamTime(10);
		
		setPushForce(push);
		setDamage(dmg);
		setOffset(off);
		
		maxModes = 2; //低速
	}

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    }
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {

		InformationSet inf = super.loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(( par2World.isRemote? inf.getClientAsBullet() : inf.getServerAsBullet() ), 
				par1ItemStack, par2World, par3EntityPlayer);

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
