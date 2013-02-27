package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.WrongUsageException;
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
			
	//List storing every single ItemStack's information.
	/*Local Variables in ItemStack NBT
	 * 
	 * canUse(Boolean) : To flag whether the gun is available for shoot.
	 * isShooting(Boolean) : To flag whether the gun is shooting or not.
	 * isReloading(Boolean) : To flag whether the gun is reloading or not.
	 * isAlive(Boolean) : To flag whether the itemEntity exists or not.
	 * 
	 */
	
	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		//System.out.println("Entity Updated.");
		//装弹函数 
		
		if(par2World.isRemote)
			return;
		
		if(par1ItemStack.getTagCompound() == null)
			return;
		
		int id = par1ItemStack.getTagCompound().getInteger("weaponID");
		
		if(id == 0 || id > listItemStack.size()){
			System.err.println("no match ID");
			return;
		}
		
		id--;
		
		NBTTagCompound nbt = (NBTTagCompound)listItemStack.get( id );
		
		if( nbt == null){
			System.err.println("null NBT");
		}
		
		
		int ticksExisted = nbt.getInteger("ticksExisted") + 1 ;
		nbt.setInteger("ticksExisted", ticksExisted);
		
		int lastTick = nbt.getInteger("lastTick");
			
		Boolean isShooting = nbt.getBoolean("isShooting");
		Boolean isReloading = nbt.getBoolean("isReloading");
		Boolean canUse = nbt.getBoolean("canUse");
		
		if( isReloading && ticksExisted - lastTick >= reloadTime ){
			//CheckAmmoCapacity
			int dmg = par1ItemStack.getItemDamage();
			if( dmg <= 0){
				isReloading = false;
				canUse = true;
				
				lastTick = ticksExisted;
				nbt.setInteger("lastTick", ticksExisted);
				nbt.setBoolean("isReloading", isReloading);
				nbt.setBoolean("canUse", canUse);
				return;
			}
			
			//int cap = this.ammoManager.ammoCapacity;
			int cap = 33333;
			if( dmg >= cap ){
				//ammoManager.clearAmmo( entityPlayer );
				cap = 0;
				par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - cap);
			} else {
				//ammoManager.consumeAmmo( dmg );
				cap -= dmg;
				par1ItemStack.setItemDamage( 0 );
			}
			
			if( par1ItemStack.getItemDamage() >=17 )
				canUse = false;
			
			isReloading = false;
			
			nbt.setBoolean( "canUse", canUse);
			nbt.setBoolean( "isReloading", isReloading);
			
			lastTick = ticksExisted;
			nbt.setInteger("lastTick", ticksExisted);
			return;
		}
		
		//射击
		if(isShooting && canUse && ticksExisted - lastTick >= shootTime[mode] ){
			if( par1ItemStack.getItemDamage() >= 17 ){
				canUse = false;
				
				lastTick = ticksExisted;
				nbt.setBoolean("canUse", canUse);
				nbt.setInteger("lastTick", lastTick);
				return;
			}
			
			//playSoundAtEntity
			par1ItemStack.damageItem( 1 , null);
			CBCMod.bulletManager.Shoot( (EntityPlayer) par3Entity , par2World);
			System.out.println("Bang!");
			
			lastTick = ticksExisted;
			nbt.setInteger("lastTick", ticksExisted);
			return;
			
		}
        
		//卡住：播放音效
		if( isShooting && !canUse && ticksExisted - lastTick >= shootTime[mode] ){
			if( par1ItemStack.getItemDamage() < 17){
				canUse = true;
				
				nbt.setBoolean("canUse", canUse);
				return;
			}
			System.out.println("Jammed");
			//playSoundAtEntity
			lastTick = ticksExisted;
			nbt.setInteger("lastTick" , lastTick);
		}
		
    }

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
		listItemStack = new ArrayList();
		
		mode = 0; //低速
		
		// TODO Auto-generated constructor stub
	}


	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		//EVENT post
		//fail:delete entity,setDead
		int id;
		if(par1ItemStack.getTagCompound() == null){
			id = 0;
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		} else {
			id = par1ItemStack.getTagCompound().getInteger("weaponID");
		}
		NBTTagCompound nbt;
		if( id == 0){
			nbt = new NBTTagCompound();

			listItemStack.add(nbt);
			par1ItemStack.getTagCompound().setInteger("weaponID", listItemStack.size()); //这里的值是物品索引+1 为了令出初始索引为0帮助判断
			id = listItemStack.size();
			id--;
		} else {
			
			if(id >= listItemStack.size()){
				nbt = new NBTTagCompound();
				par1ItemStack.stackTagCompound = new NBTTagCompound();
				
				listItemStack.add(nbt);
				par1ItemStack.getTagCompound().setInteger("weaponID", listItemStack.size()); //这里的值是物品索引+1 为了令出初始索引为0帮助判断
				id = listItemStack.size();
			}
			id--;
			nbt = (NBTTagCompound)listItemStack.get( id );
		}
		
		System.out.println("To here =w=");
		
		
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
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		//if event calcelled setdead
		int id = par1ItemStack.getTagCompound().getInteger("weaponID");
		if(id == 0 || id > listItemStack.size()){
			throw new WrongUsageException("no match id for the ItemStack" , par1ItemStack );
		}
		id--;
		
		NBTTagCompound nbt = (NBTTagCompound)listItemStack.get( id );
		if( nbt == null){
			throw new NullPointerException();
		}
		
		Boolean isReloading  = nbt.getBoolean("isReloading");
		System.out.println("isReloading : " + isReloading);
		
		nbt.setBoolean( "isShooting" , false );
		System.out.println("Setted dead");

	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }
	
	


}
