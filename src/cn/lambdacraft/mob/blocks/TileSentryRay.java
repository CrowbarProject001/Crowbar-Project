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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.utils.BlockPos;
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import cn.lambdacraft.mob.ModuleMob;
import cn.lambdacraft.mob.entities.EntitySentry;
import cn.lambdacraft.mob.network.NetSentrySync;

/**
 * @author WeAthFolD
 */
public class TileSentryRay extends TileEntity {

	public EntitySentry linkedSentry;
	public TileSentryRay linkedBlock;
	public boolean isLoaded;
	
	private int tickSinceLastUpdate = 0;
	
	public boolean isActivated;
	
	private int linkedX = 0, linkedY = 0, linkedZ = 0, sentryId = 0;
	
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
		if(linkedSentry != null && linkedSentry.isDead) {
			isActivated = false;
			linkedSentry = null;
		}
		if(linkedBlock != null && linkedBlock.isInvalid()) {
			linkedBlock = null;
		}
		if(worldObj.isRemote)
			return;
		if(linkedX != 0 || linkedY != 0 || linkedZ != 0) {
	        TileEntity te = worldObj.getBlockTileEntity(linkedX, linkedY, linkedZ);
	        if(te != null && te instanceof TileSentryRay) {
	        	linkedBlock = (TileSentryRay) te;
	        	NetSentrySync.sendSyncPacket(this);
	        }
	        linkedX = linkedY = linkedZ = 0;
		}
		if(sentryId != 0) {
			Entity e = worldObj.getEntityByID(sentryId);
	        if(e != null && EntitySentry.class.isInstance(e)) {
	        	linkedSentry = (EntitySentry) e;
	        	sentryId = 0;
	        }
	        System.out.println("Looping search...");
		}
		//TODO:Check if touched, and notice the entity to activate
		if(!isLoaded) {
			EntityPlayer player = ModuleMob.playerMap.get(new BlockPos(this));
			if(player != null) {
				TileSentryRay another = ModuleMob.tileMap.get(player);
				if(another == null || another.isInvalid())
					ModuleMob.tileMap.put(player, this);
				else {
					linkedBlock = another;
					linkedSentry = ModuleMob.syncMap.get(player);
					ModuleMob.syncMap.remove(player);
					ModuleMob.tileMap.remove(player);
					sendChatToPlayer(player, linkedSentry);
					NetSentrySync.sendSyncPacket(this);
					isActivated = true;
				}
			} else isActivated = false;
			isLoaded = true;
		}
		if(linkedBlock != null) {
			Vec3 offset = getRayOffset();
			Vec3 vec0 = worldObj.getWorldVec3Pool().getVecFromPool(xCoord, yCoord, zCoord).addVector(offset.xCoord, offset.yCoord, offset.zCoord);
			offset = linkedBlock.getRayOffset();
			Vec3 vec1 = worldObj.getWorldVec3Pool().getVecFromPool(linkedBlock.xCoord, linkedBlock.yCoord, linkedBlock.zCoord).addVector(offset.xCoord, offset.yCoord, offset.zCoord);
			MovingObjectPosition result = BulletManager.rayTraceEntities(GenericUtils.selectorLiving, worldObj, vec0, vec1);
			if(result != null) {
				if(linkedSentry != null)
					this.linkedSentry.activate();
				else CBCMod.log.severe("Why didn't I find my target?");
			}
		}
		if(++tickSinceLastUpdate > 20) {
			tickSinceLastUpdate = 0;
			NetSentrySync.sendSyncPacket(this);
		}
	}
	
	public Vec3 getRayOffset() {
		double x = 0.0, y = 0.0, z = 0.0;
		float var6 = BlockSentryRay.HEIGHT;
		float var7 = BlockSentryRay.WIDTH;
		switch(this.blockMetadata) {
		case 5 :
			y += 0.5;
			z += 0.5;
			x += 2 * var6;
			break;
		case 4 :
			y += 0.5;
			z += 0.5;
			x += 1 - 2 * var6;
			break;
		case 3 :
			y += 0.5;
			x += 0.5;
			z += 2 * var6;
			break;
		case 2 :
			y += 0.5;
			x += 0.5;
			z += 1 - 2 * var6;
			break;
		case 1 :
			x += 0.5;
			z += 0.5;
			y += 2 * var6;
			break;
		case 0 :
			x += 0.5;
			z += 0.5;
			y += 1- 2 * var6;
			break;
		default:
			break;
		}
		return Vec3.createVectorHelper(x, y, z);
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
        
        sentryId = nbt.getInteger("sentry");
        linkedX = nbt.getInteger("linkX");
        linkedY = nbt.getInteger("linkY");
        linkedZ = nbt.getInteger("linkZ");
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
