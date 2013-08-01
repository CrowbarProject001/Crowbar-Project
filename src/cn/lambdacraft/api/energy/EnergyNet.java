package cn.lambdacraft.api.energy;

import cn.lambdacraft.api.energy.tile.IEnergySource;

import java.lang.reflect.Method;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public final class EnergyNet {

	Object energyNetInstance;
	private static Method EnergyNet_getForWorld;
	private static Method EnergyNet_addTileEntity;
	private static Method EnergyNet_removeTileEntity;
	private static Method EnergyNet_emitEnergyFrom;
	private static Method EnergyNet_getTotalEnergyConducted;
	private static Method EnergyNet_getTotalEnergyEmitted;
	private static Method EnergyNet_getTotalEnergySunken;

	public static EnergyNet getForWorld(World world) {
		try {
			if (EnergyNet_getForWorld == null)
				EnergyNet_getForWorld = Class.forName(
						getPackage() + ".core.energy.EnergyNet").getMethod(
						"getForWorld", new Class[] { World.class });

			return new EnergyNet(EnergyNet_getForWorld.invoke(null,
					new Object[] { world }));
		} catch (Exception e) {
		throw new RuntimeException(e);
		}
	}

	private EnergyNet(Object energyNetInstance) {
		this.energyNetInstance = energyNetInstance;
	}

	@Deprecated
	public void addTileEntity(TileEntity addedTileEntity) {
		try {
			if (EnergyNet_addTileEntity == null)
				EnergyNet_addTileEntity = Class.forName(
						getPackage() + ".core.energy.EnergyNet").getMethod(
						"addTileEntity", new Class[] { TileEntity.class });

			EnergyNet_addTileEntity.invoke(this.energyNetInstance,
					new Object[] { addedTileEntity });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	public void removeTileEntity(TileEntity removedTileEntity) {
		try {
			if (EnergyNet_removeTileEntity == null)
				EnergyNet_removeTileEntity = Class.forName(
						getPackage() + ".core.energy.EnergyNet").getMethod(
						"removeTileEntity", new Class[] { TileEntity.class });

			EnergyNet_removeTileEntity.invoke(this.energyNetInstance,
					new Object[] { removedTileEntity });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	public int emitEnergyFrom(IEnergySource energySource, int amount) {
		try {
			if (EnergyNet_emitEnergyFrom == null)
				EnergyNet_emitEnergyFrom = Class.forName(
						getPackage() + ".core.energy.EnergyNet").getMethod(
						"emitEnergyFrom",
						new Class[] { IEnergySource.class, Integer.TYPE });

			return ((Integer) EnergyNet_emitEnergyFrom.invoke(
					this.energyNetInstance, new Object[] { energySource,
							Integer.valueOf(amount) })).intValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Deprecated
	public long getTotalEnergyConducted(TileEntity tileEntity) {
		try {
			if (EnergyNet_getTotalEnergyConducted == null)
				EnergyNet_getTotalEnergyConducted = Class.forName(
						getPackage() + ".core.energy.EnergyNet").getMethod(
						"getTotalEnergyConducted",
						new Class[] { TileEntity.class });

			return ((Long) EnergyNet_getTotalEnergyConducted.invoke(
					this.energyNetInstance, new Object[] { tileEntity }))
					.longValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public long getTotalEnergyEmitted(TileEntity tileEntity) {
		try {
			if (EnergyNet_getTotalEnergyEmitted == null)
				EnergyNet_getTotalEnergyEmitted = Class.forName(
						getPackage() + ".core.energy.EnergyNet").getMethod(
						"getTotalEnergyEmitted",
						new Class[] { TileEntity.class });

			return ((Long) EnergyNet_getTotalEnergyEmitted.invoke(
					this.energyNetInstance, new Object[] { tileEntity }))
					.longValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public long getTotalEnergySunken(TileEntity tileEntity) {
		try {
			if (EnergyNet_getTotalEnergySunken == null)
				EnergyNet_getTotalEnergySunken = Class.forName(
						getPackage() + ".core.energy.EnergyNet").getMethod(
						"getTotalEnergySunken",
						new Class[] { TileEntity.class });

			return ((Long) EnergyNet_getTotalEnergySunken.invoke(
					this.energyNetInstance, new Object[] { tileEntity }))
					.longValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	private static String getPackage() {
		Package pkg = EnergyNet.class.getPackage();

		if (pkg != null) {
			String packageName = pkg.getName();

			return packageName.substring(0, packageName.length()
					- ".api.energy".length());
		}

		return "cn.lambdacraft";
	}
}