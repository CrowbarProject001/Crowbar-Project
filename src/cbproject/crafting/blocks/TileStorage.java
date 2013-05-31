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
package cbproject.crafting.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import cbproject.api.LCDirection;
import cbproject.api.energy.events.EnergyTileSourceEvent;
import cbproject.api.energy.tile.IEnergySink;
import cbproject.api.energy.tile.IEnergySource;
import cbproject.core.block.TileElectricStorage;
import cbproject.core.block.TileElectrical;

/**
 * @author WeAthFolD
 *
 */
public abstract class TileStorage extends TileElectricStorage implements IEnergySource {

	public class TileStorageSmall extends TileStorage {

		public TileStorageSmall() {
			super(1, 80000);
		}

		@Override
		public int getMaxEnergyOutput() {
			return 32;
		}

		@Override
		public int getMaxSafeInput() {
			return 32;
		}
		
	}
	
	public class TileStorageLarge extends TileStorage {

		public TileStorageLarge() {
			super(2, 600000);
		}

		@Override
		public int getMaxEnergyOutput() {
			return 128;
		}

		@Override
		public int getMaxSafeInput() {
			return 128;
		}
		
	}
	
	public int currentEnergy = 0;

	public TileStorage(int t, int max) {
		super(t, max);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(currentEnergy > 0){
			int all = currentEnergy > 5 ?  5 : currentEnergy;
			int rev = sendEnergy(all);
			currentEnergy -= (all - rev);
		}
	}
	
	public int sendEnergy(int amm) {
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(worldObj, this, amm);
		MinecraftForge.EVENT_BUS.post(event);
		return event.amount;
	}

	@Override
	public boolean emitEnergyTo(TileEntity emTileEntity, LCDirection emDirection) {
		return true;
	}


	@Override
	public boolean acceptsEnergyFrom(TileEntity paramTileEntity,
			LCDirection paramDirection) {
		return true;
	}
	
}
