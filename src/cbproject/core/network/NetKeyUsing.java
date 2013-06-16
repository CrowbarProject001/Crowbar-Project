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
package cbproject.core.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cbproject.core.keys.UsingUtils;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.IChannelProcess;
import cbproject.core.utils.BlockPos;
import cbproject.core.utils.MotionXYZ;

/**
 * 用来处理使用键网络包收发的类。
 * 
 * @author WeAthFolD
 * 
 */
public class NetKeyUsing implements IChannelProcess {

	public static void sendUsingPacket(boolean isUsing) {
		ByteArrayOutputStream bos = CBCNetHandler.getStream(
				GeneralProps.NET_ID_USE, 1);
		DataOutputStream outputStream = new DataOutputStream(bos);
		Packet250CustomPayload packet = new Packet250CustomPayload();
		try {
			outputStream.writeBoolean(isUsing);
		} catch (Exception e) {
			e.printStackTrace();
		}
		packet.channel = GeneralProps.NET_CHANNEL_SERVER;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	@Override
	public void onPacketData(DataInputStream inputStream, Player player) {
		boolean isUsing = false;
		EntityPlayer thePlayer = (EntityPlayer) player;
		World world = thePlayer.worldObj;
		try {
			isUsing = inputStream.readBoolean();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (isUsing) {
			MotionXYZ begin = new MotionXYZ(thePlayer), end = new MotionXYZ(
					thePlayer).updateMotion(8.0);
			MovingObjectPosition mop = world.rayTraceBlocks(
					begin.asVec3(world), end.asVec3(world));
			if (mop == null || mop.sideHit == -1)
				return;
			int id = world.getBlockId(mop.blockX, mop.blockY, mop.blockZ);
			UsingUtils.useBlock(new BlockPos(mop.blockX, mop.blockY,
					mop.blockZ, id), world, thePlayer);

		} else {
			UsingUtils.stopUsingBlock(world, thePlayer);
		}

	}

}
