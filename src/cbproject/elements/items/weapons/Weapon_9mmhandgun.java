package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityAutomateWeapon;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.AmmoManager;


public class Weapon_9mmhandgun extends WeaponGeneral {
	/*9mmHandgun:两种模式
	 * 模式I(mode = 0 ):低速射击模式，准确度较高
	 * 模式II (mode = 1):高速射击模式，准确度较低
	 * 都是自动模式
	 */
	public static final int shootTime[]={ 30 , 15 };
	public static final int reloadTime = 60; //3s
	public static final String pathSoundShoot = "cbc.weapons.9mmhandgunshoot" , 
			pathSoundReload = "cbc.weapons.9mmhandgunreload" , 
			pathSoundJam = "cbc.weapons.9mmhandgunjam" ;
			
	/*Local Variables in ItemStack NBT
	 * 
	 * canUse(Boolean) : To flag whether the gun is available for shoot.
	 * isShooting(Boolean) : To flag whether the gun is shooting or not.
	 * isReloading(Boolean) : To flag whether the gun is reloading or not.
	 * isAlive(Boolean) : To flag whether the itemEntity exists or not.
	 * 
	 */
	
	public Weapon_9mmhandgun(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID);
		
		setItemName("weapon_9mmhandgun");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(2,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		this.maxStackSize = 1;
		setMaxDamage(18); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		mode = 0; //低速
		
		// TODO Auto-generated constructor stub
	}


	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		//EVENT post
		//fail:delete entity,setDead
		NBTTagCompound nbt = par1ItemStack.getTagCompound();
		if(nbt == null){
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		}
		nbt = par1ItemStack.getTagCompound();
		Boolean isAlive = nbt.getBoolean("isAlive");
		Boolean canUse = nbt.getBoolean("canUse");
		Boolean isShooting = nbt.getBoolean("isShooting");
		Boolean isReloading = nbt.getBoolean("isReloading");
		
		if(!canUse && !isReloading){
			if(par1ItemStack.getItemDamage() < 17)
				canUse = true;
			else isReloading = true;
		}
		if(canUse){
			isShooting = true;
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack) );
		}
		
		nbt.setBoolean("canUse", canUse);
		nbt.setBoolean("isShooting", isShooting);
		nbt.setBoolean("isReloading", isReloading);
		
		if(!isAlive){
			isAlive = true;
			nbt.setBoolean("isAlive", isAlive);
			par2World.spawnEntityInWorld(new EntityAutomateWeapon( shootTime[mode] , reloadTime , pathSoundShoot , pathSoundReload
					, pathSoundJam , par1ItemStack, par3EntityPlayer));
		}
		
		return par1ItemStack;
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		//if event calcelled setdead
		par1ItemStack.stackTagCompound.setBoolean( "isShooting" , false );
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }
	
	


}
