/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.mob.blocks;

import cn.lambdacraft.core.network.TileEntitySyncer;
import cn.lambdacraft.core.register.SyncableField;
import cn.lambdacraft.core.utils.BlockPos;
import cn.lambdacraft.mob.ModuleMob;
import cn.lambdacraft.mob.entities.EntitySentry;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author WeAthFolD
 */
public class TileSentryRay extends TileEntity {

	public EntitySentry linkedSentry;
	public TileSentryRay linkedBlock;
	public boolean isLoaded;
	
	private int tickSinceLastUpdate = 0;
	
	@SyncableField(Boolean.class)
	public boolean isActivated;
	
	@SyncableField(Integer.class)
	public int linkedX, linkedZ;
	
	@SyncableField(Short.class)
	public int linkedY;
	
	/**
	 * 
	 */
	public TileSentryRay() {
	}
	
	public void connectWith(TileSentryRay another) {
		this.linkedBlock = another;
	}
	
	public void noticeSentry() {
		linkedSentry.activate();
	}
	
	@Override
	public void updateEntity() {
		if(worldObj.isRemote)
			return;
		//TODO:Check if touched, and notice the entity to activate
		if(!isLoaded) {
			EntityPlayer player = ModuleMob.playerMap.get(new BlockPos(this));
			if(player != null) {
				TileSentryRay another = ModuleMob.tileMap.get(player);
				if(another == null)
					ModuleMob.tileMap.put(player, this);
				else {
					linkedBlock = another;
					linkedSentry = ModuleMob.syncMap.get(player);
					ModuleMob.syncMap.remove(player);
					ModuleMob.tileMap.remove(player);
					sendChatToPlayer(player, linkedSentry);
					isActivated = true;
				}
			} else isActivated = false;
			isLoaded = true;
		}
		
		if(++tickSinceLastUpdate > 5) {
			tickSinceLastUpdate = 0;
			if(linkedBlock != null) {
				linkedX = linkedBlock.xCoord;
				linkedY = linkedBlock.yCoord;
				linkedZ = linkedBlock.zCoord;
			}
			TileEntitySyncer.updateTileEntity(this);
		}
	}
	
	protected void sendChatToPlayer(EntityPlayer player, EntitySentry sentry) {
		if(worldObj.isRemote)
			return;
		StringBuilder sb = new StringBuilder("---------HECU Special Force---------\n");
		sb.append(EnumChatFormatting.RED).append("Sentry ID : ").append(EnumChatFormatting.RED).append(sentry.entityId).append("\n").
			append(EnumChatFormatting.GREEN).append("Sentry Ray Successfully Deployed.");
		player.sendChatToPlayer(sb.toString());
	}
	
    /**
     * Reads a tile entity from NBT.
     */
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        Entity e = worldObj.getEntityByID(nbt.getInteger("sentry"));
        if(e != null && EntitySentry.class.isInstance(e))
        	linkedSentry = (EntitySentry) e;
        
        int x = nbt.getInteger("linkX"), y = nbt.getInteger("linkY"), z = nbt.getInteger("linkZ");
        TileEntity te = worldObj.getBlockTileEntity(x, y, z);
        if(te != null && te instanceof TileSentryRay)
        	linkedBlock = (TileSentryRay) te;
        
        isLoaded = nbt.getBoolean("isLoaded");
        isActivated = nbt.getBoolean("isActivated");
    }

    /**
     * Writes a tile entity to NBT.
     */
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(linkedSentry != null)
        	nbt.setInteger("sentry", linkedSentry.entityId);
        if(linkedBlock != null) {
        	nbt.setInteger("linkX", linkedBlock.xCoord);
        	nbt.setInteger("linkY", linkedBlock.yCoord);
        	nbt.setInteger("linkZ", linkedBlock.zCoord);
        }
        nbt.setBoolean("isLoaded", isLoaded);
        nbt.setBoolean("isActivated", isActivated);
    }

}
