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
import cbproject.utils.weapons.InformationBulletWeapon;


public class Weapon_9mmhandgun extends WeaponGeneral {
	/*9mmHandgun:两种模式
	 * 模式I(mode = 0 ):低速射击模式，准确度较高
	 * 模式II (mode = 1):高速射击模式，准确度较低
	 * 都是自动模式
	 */
	public static final int shootTime[]={ 10 , 5 };
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
		InformationBulletWeapon information = (InformationBulletWeapon)listItemStack.get(id);
		if( information.signID != par1ItemStack.getTagCompound().getDouble("uniqueID")){
			System.err.println("Unique sign not match,quitting...");
		}
		if( information == null){
			System.err.println("null information");
		}
		
		information.updateTick();
		int ticksExisted = information.ticksExisted;
		
		int lastTick = information.lastTick;
			
		Boolean isShooting = information.isShooting;
		Boolean isReloading = information.isReloading;
		Boolean canUse = information.canUse;
		
		if( isReloading && ticksExisted - lastTick >= reloadTime ){
			//CheckAmmoCapacity
			int dmg = par1ItemStack.getItemDamage();
			if( dmg <= 0){
				isReloading = false;
				canUse = true;
				
				lastTick = ticksExisted;
				information.setLastTick();
				information.isReloading = isReloading;
				information.canUse = canUse;
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
			
			information.canUse = canUse;
			information.isReloading = isReloading;
			information.setLastTick();
			return;
		}
		
		//射击
		if(isShooting && canUse && ticksExisted - lastTick >= shootTime[mode] ){
			if( par1ItemStack.getItemDamage() >= 17 ){
				canUse = false;
				
				lastTick = ticksExisted;
				information.canUse = canUse;
				information.setLastTick();
				return;
			}
			
			//playSoundAtEntity
			par1ItemStack.damageItem( 1 , null);
			CBCMod.bulletManager.Shoot( (EntityPlayer) par3Entity , par2World);
			System.out.println("Bang!");
			
			lastTick = ticksExisted;
			information.setLastTick();
			return;
			
		}
        
		//卡住：播放音效
		if( isShooting && !canUse && ticksExisted - lastTick >= shootTime[mode] ){
			if( par1ItemStack.getItemDamage() < 17){
				canUse = true;
				
				information.canUse = canUse;
				return;
			}
			System.out.println("Jammed");
			System.out.println("Item Damage: " + par1ItemStack.getItemDamageForDisplay());
			//playSoundAtEntity
			lastTick = ticksExisted;
			information.setLastTick();
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
		
		InformationBulletWeapon information;
		double uniqueID = Math.random();
		
		if( id == 0){
			information = new InformationBulletWeapon( uniqueID , false , false , false , 0 );
			listItemStack.add(information);
			par1ItemStack.getTagCompound().setInteger("weaponID", listItemStack.size()); //这里的值是物品索引+1 为了令出初始索引为0帮助判断
			par1ItemStack.getTagCompound().setDouble( "uniqueID", uniqueID );
			id = listItemStack.size() - 1;
			
		} else {
			if( id >= listItemStack.size() ){
				information = new InformationBulletWeapon( uniqueID, false, false, false, 0);
				
				par1ItemStack.stackTagCompound = new NBTTagCompound();
				
				listItemStack.add(information);
				par1ItemStack.getTagCompound().setInteger("weaponID", listItemStack.size()); //这里的值是物品索引+1 为了令出初始索引为0帮助判断
				par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
				id = listItemStack.size()-1;
			}
			else{ 
				uniqueID = par1ItemStack.getTagCompound().getDouble("uniqueID");
				information = (InformationBulletWeapon)listItemStack.get( --id );
				if( uniqueID != information.signID ){ //发生了重启 列表不匹配
					uniqueID = Math.random();
					information = new InformationBulletWeapon( uniqueID, false, false, false, 0);
					listItemStack.add( information );
					par1ItemStack.getTagCompound().setInteger("weaponID", listItemStack.size()); 
					par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
				}
				
			}
			
		}
		
		System.out.println("To here =w=");
		
		
		Boolean canUse = information.canUse;
		Boolean isShooting = information.isShooting;
		Boolean isReloading = information.isReloading;
		
		if(!canUse && !isReloading){
			if(par1ItemStack.getItemDamage() < 17)
				canUse = true;
			else isReloading = true;
		}
		
		if(canUse){
			isShooting = true;
			
		}
		
		information.canUse = canUse;
		information.isShooting = isShooting;
		information.isReloading = isReloading;
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack) );
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
		
		InformationBulletWeapon information = (InformationBulletWeapon)listItemStack.get(id);
		if( information == null){
			throw new NullPointerException();
		}
		
		Boolean isReloading  = information.isReloading;
		System.out.println("isReloading : " + isReloading);
		information.isShooting = false;
		System.out.println("Setted dead");

	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }
	
	


}
