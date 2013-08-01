package cn.lambdacraft.core.energy;

import cn.lambdacraft.api.LCDirection;
import cn.lambdacraft.api.energy.EnergyTileLoadEvent;
import cn.lambdacraft.api.energy.EnergyTileSourceEvent;
import cn.lambdacraft.api.energy.EnergyTileUnLoadEvent;
import cn.lambdacraft.api.energy.IEnergyAcceptor;
import cn.lambdacraft.api.energy.IEnergyConductor;
import cn.lambdacraft.api.energy.IEnergyEmitter;
import cn.lambdacraft.api.energy.IEnergySink;
import cn.lambdacraft.api.energy.IEnergySource;
import cn.lambdacraft.api.energy.IEnergyTile;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.energy.EnergyNet.EnergyPath;
import cn.lambdacraft.core.world.WorldData;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

public final class EnergyNet {
	public static final double minConductionLoss = 0.0001D;
	private static final LCDirection[] directions;
	private final Map<IEnergySource, List<EnergyPath>> energySourceToEnergyPathMap = new HashMap<IEnergySource, List<EnergyPath>>();
	private final Map<EntityLiving, Integer> entityLivingToShockEnergyMap = new HashMap<EntityLiving, Integer>();
	private final Map<ChunkCoordinates, TileEntity> registeredTileEntities = new HashMap<ChunkCoordinates, TileEntity>();
	private static int apiDemandsErrorCooldown;
	private static int apiEmitErrorCooldown;
	
	public static final int lv = 32;
	  public static final int mv = 128;
	  public static final int hv = 512;
	  public static final int ev = 2048;
	  public static final int redstone = 800;
	  public static final int suBattery = 1200;
	  public static final int ejectUpgradeOperationCost = 20;
	  public static final int pumpOperationCost = 200;
	  public static final int minerWithdrawOperationCost = 3;
	  public static final int minerWithdrawOperationDuration = 20;
	  public static final int minerMineOperationCostAir = 3;
	  public static final int minerMineOperationDurationAir = 20;
	  public static final int minerMineOperationCostDrill = 6;
	  public static final int minerMineOperationDurationDrill = 20;
	  public static final int minerMineOperationCostDDrill = 200;
	  public static final int minerMineOperationDurationDDrill = 50;
	  public static final int minerTransportCost = 2;
	  public static final int miningDrillCost = 50;
	  public static final int diamondDrillCost = 80;

	public static void initialize() {
		new EventHandler();
	}

	public static EnergyNet getForWorld(World world) {
		WorldData worldData = WorldData.get(world);

		return worldData.energyNet;
	}

	public static void onTick(World world) {
		if (world.provider.dimensionId == 0) {
			if (apiDemandsErrorCooldown > 0)
				apiDemandsErrorCooldown -= 1;
			if (apiEmitErrorCooldown > 0)
				apiEmitErrorCooldown -= 1;
		}

		CBCMod.proxy.profilerEndSection();
	}

	public void addTileEntity(TileEntity te) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.addTileEntity: called for ").append(te)
					.append(" client-side, aborting").toString());
			return;
		}

		if (!(te instanceof IEnergyTile)) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.addTileEntity: ").append(te)
					.append(" doesn't implement IEnergyTile, aborting")
					.toString());
			return;
		}

		if (te.isInvalid()) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.addTileEntity: ").append(te)
					.append(" is invalid (TileEntity.isInvalid()), aborting")
					.toString());
			return;
		}

		ChunkCoordinates coords = new ChunkCoordinates(te.xCoord, te.yCoord,
				te.zCoord);

		if (this.registeredTileEntities.containsKey(coords)) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.addTileEntity: ").append(te)
					.append(" is already added, aborting").toString());
			return;
		}

		if (!te.worldObj.blockExists(te.xCoord, te.yCoord, te.zCoord)) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.addTileEntity: ").append(te)
					.append(" was added too early, postponing").toString());

			EnergyNet.addSingleTickCallback(te.worldObj,
					new PostPonedAddCallback(te));

			return;
		}

		this.registeredTileEntities.put(coords, te);

		if ((te instanceof IEnergyAcceptor)) {
			List<EnergyPath> reverseEnergyPaths = discover(te,
					true, 2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getMaxEnergyOutput() <= reverseEnergyPath.loss)) {
					continue;
				}
				this.energySourceToEnergyPathMap.remove(energySource);
			}
		}

		if ((te instanceof IEnergySource))
			;
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int x = te.xCoord + dir.offsetX;
			int y = te.yCoord + dir.offsetY;
			int z = te.zCoord + dir.offsetZ;

			if (!te.worldObj.blockExists(x, y, z))
				continue;
			te.worldObj.notifyBlockOfNeighborChange(x, y, z,
					te.getBlockType().blockID);
		}
	}

	public void removeTileEntity(TileEntity te) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.removeTileEntity: called for ")
					.append(te).append(" client-side, aborting").toString());
			return;
		}

		if (!(te instanceof IEnergyTile)) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.removeTileEntity: ").append(te)
					.append(" doesn't implement IEnergyTile, aborting")
					.toString());
			return;
		}

		ChunkCoordinates coords = new ChunkCoordinates(te.xCoord, te.yCoord,
				te.zCoord);

		if (!this.registeredTileEntities.containsKey(coords)) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.removeTileEntity: ").append(te)
					.append(" is already removed, aborting").toString());
			return;
		}
		Iterator it;
		if ((te instanceof IEnergyAcceptor)) {
			List<EnergyPath> reverseEnergyPaths = discover(te, true,
					2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getMaxEnergyOutput() <= reverseEnergyPath.loss)) {
					continue;
				}
				if ((te instanceof IEnergyConductor))
					this.energySourceToEnergyPathMap.remove(energySource);
				else
					for (it = this.energySourceToEnergyPathMap
							.get(energySource).iterator(); it.hasNext();)
						if (((EnergyPath) it.next()).target == te)
							it.remove();
			}
		}

		if ((te instanceof IEnergySource)) {
			this.energySourceToEnergyPathMap.remove(te);
		}

		this.registeredTileEntities.remove(coords);

		if (te.worldObj.blockExists(te.xCoord, te.yCoord, te.zCoord)) {
			Block block = te.getBlockType();

			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				int x = te.xCoord + dir.offsetX;
				int y = te.yCoord + dir.offsetY;
				int z = te.zCoord + dir.offsetZ;

				if (!te.worldObj.blockExists(x, y, z))
					continue;
				te.worldObj.notifyBlockOfNeighborChange(x, y, z,
						block != null ? block.blockID : 0);
			}
		}
	}

	public int emitEnergyFrom(IEnergySource energySource, int amount) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			if (apiEmitErrorCooldown == 0) {
				apiEmitErrorCooldown = 600;
				CBCMod.log.warning(new StringBuilder()
						.append("EnergyNet.emitEnergyFrom: called for ")
						.append(energySource).append(" client-side, aborting")
						.toString());
			}

			return amount;
		}

		if (!(energySource instanceof TileEntity)) {
			if (apiEmitErrorCooldown == 0) {
				apiEmitErrorCooldown = 600;
				CBCMod.log.warning(new StringBuilder()
						.append("EnergyNet.emitEnergyFrom: ")
						.append(energySource)
						.append(" is no tile entity, aborting").toString());
			}

			return amount;
		}

		TileEntity srcTe = (TileEntity) energySource;

		if (srcTe.isInvalid()) {
			CBCMod.log.warning(new StringBuilder()
					.append("EnergyNet.emitEnergyFrom: ").append(srcTe)
					.append(" is invalid (TileEntity.isInvalid()), aborting")
					.toString());
			return amount;
		}

		if (!this.registeredTileEntities.containsKey(new ChunkCoordinates(
				srcTe.xCoord, srcTe.yCoord, srcTe.zCoord))) {
			if (apiEmitErrorCooldown == 0) {
				apiEmitErrorCooldown = 600;
				CBCMod.log.warning(new StringBuilder()
						.append("EnergyNet.emitEnergyFrom: ")
						.append(energySource)
						.append(" is not added to the enet, aborting")
						.toString());
			}

			return amount;
		}

		if (!this.energySourceToEnergyPathMap.containsKey(energySource)) {
			this.energySourceToEnergyPathMap.put(
					energySource,
					discover((TileEntity) energySource, false,
							energySource.getMaxEnergyOutput()));
		}

		List<EnergyPath> activeEnergyPaths = new Vector<EnergyPath>();
		double totalInvLoss = 0.0D;

		for (EnergyPath energyPath : this.energySourceToEnergyPathMap
				.get(energySource)) {
			assert ((energyPath.target instanceof IEnergySink));

			IEnergySink energySink = (IEnergySink) energyPath.target;

			if ((energySink.demandsEnergy() > 0) && (energyPath.loss < amount)) {
				totalInvLoss += 1.0D / energyPath.loss;
				activeEnergyPaths.add(energyPath);
			}
		}

		Collections.shuffle(activeEnergyPaths);

		for (int i = activeEnergyPaths.size() - amount; i > 0; i--) {
			EnergyPath removedEnergyPath = activeEnergyPaths
					.remove(activeEnergyPaths.size() - 1);
			totalInvLoss -= 1.0D / removedEnergyPath.loss;
		}

		Map<EnergyPath, Integer> suppliedEnergyPaths = new HashMap<EnergyPath, Integer>();
		new Vector();

		while ((!activeEnergyPaths.isEmpty()) && (amount > 0)) {
			int energyConsumed = 0;
			double newTotalInvLoss = 0.0D;

			List<EnergyPath> currentActiveEnergyPaths = activeEnergyPaths;
			activeEnergyPaths = new Vector<EnergyPath>();

			activeEnergyPaths.iterator();

			for (EnergyPath energyPath : currentActiveEnergyPaths) {
				IEnergySink energySink = (IEnergySink) energyPath.target;

				int energyProvided = (int) Math
						.floor(Math.round(amount / totalInvLoss
								/ energyPath.loss * 100000.0D) / 100000.0D);
				int energyLoss = (int) Math.floor(energyPath.loss);

				if (energyProvided > energyLoss) {
					int energyReturned = energySink.injectEnergy(
							energyPath.targetDirection, energyProvided
									- energyLoss);

					if ((energyReturned == 0)
							&& (energySink.demandsEnergy() > 0)) {
						activeEnergyPaths.add(energyPath);
						newTotalInvLoss += 1.0D / energyPath.loss;
					} else if (energyReturned >= energyProvided - energyLoss) {
						energyReturned = energyProvided - energyLoss;

						if (apiDemandsErrorCooldown == 0) {
							apiDemandsErrorCooldown = 600;

							String c = "not a tile entity";
							if ((energySink instanceof TileEntity)) {
								TileEntity te = (TileEntity) energySink;
								c = new StringBuilder()
										.append(te.worldObj == null ? "unknown"
												: Integer
														.valueOf(te.worldObj.provider.dimensionId))
										.append(":").append(te.xCoord)
										.append(",").append(te.yCoord)
										.append(",").append(te.zCoord)
										.toString();
							}
							CBCMod.log
									.warning(new StringBuilder()
											.append("API ERROR: ")
											.append(energySink)
											.append(" (")
											.append(c)
											.append(") didn't implement demandsEnergy() properly, no energy from injectEnergy accepted (")
											.append(energyReturned)
											.append(") although demandsEnergy() requested ")
											.append(energyProvided - energyLoss)
											.append(".").toString());
						}
					}

					energyConsumed += energyProvided - energyReturned;

					int energyInjected = energyProvided - energyLoss
							- energyReturned;

					if (!suppliedEnergyPaths.containsKey(energyPath))
						suppliedEnergyPaths.put(energyPath,
								Integer.valueOf(energyInjected));
					else
						suppliedEnergyPaths.put(
								energyPath,
								Integer.valueOf(energyInjected
										+ suppliedEnergyPaths
												.get(energyPath).intValue()));
				} else {
					activeEnergyPaths.add(energyPath);
					newTotalInvLoss += 1.0D / energyPath.loss;
				}
			}

			if ((energyConsumed == 0) && (!activeEnergyPaths.isEmpty())) {
				EnergyPath removedEnergyPath = activeEnergyPaths
						.remove(activeEnergyPaths.size() - 1);
				newTotalInvLoss -= 1.0D / removedEnergyPath.loss;
			}

			totalInvLoss = newTotalInvLoss;
			amount -= energyConsumed;
		}
		int energyInjected;
		for (Map.Entry entry : suppliedEnergyPaths.entrySet()) {
			EnergyPath energyPath = (EnergyPath) entry.getKey();
			energyInjected = ((Integer) entry.getValue()).intValue();

			energyPath.totalEnergyConducted += energyInjected;

			if (energyInjected > energyPath.minInsulationEnergyAbsorption) {
				List<EntityLiving> entitiesNearEnergyPath = ((TileEntity) energySource).worldObj
						.getEntitiesWithinAABB(EntityLiving.class,
								AxisAlignedBB.getBoundingBox(
										energyPath.minX - 1,
										energyPath.minY - 1,
										energyPath.minZ - 1,
										energyPath.maxX + 2,
										energyPath.maxY + 2,
										energyPath.maxZ + 2));

				for (EntityLiving entityLiving : entitiesNearEnergyPath) {
					int maxShockEnergy = 0;

					for (IEnergyConductor energyConductor : energyPath.conductors) {
						TileEntity te = (TileEntity) energyConductor;

						if (entityLiving.boundingBox
								.intersectsWith(AxisAlignedBB.getBoundingBox(
										te.xCoord - 1, te.yCoord - 1,
										te.zCoord - 1, te.xCoord + 2,
										te.yCoord + 2, te.zCoord + 2))) {
							int shockEnergy = energyInjected
									- energyConductor
											.getInsulationEnergyAbsorption();

							if (shockEnergy > maxShockEnergy)
								maxShockEnergy = shockEnergy;
							if (energyConductor.getInsulationEnergyAbsorption() == energyPath.minInsulationEnergyAbsorption)
								break;
						}
					}
					if (this.entityLivingToShockEnergyMap
							.containsKey(entityLiving))
						this.entityLivingToShockEnergyMap
								.put(entityLiving,
										Integer.valueOf(this.entityLivingToShockEnergyMap
												.get(entityLiving).intValue()
												+ maxShockEnergy));
					else {
						this.entityLivingToShockEnergyMap.put(entityLiving,
								Integer.valueOf(maxShockEnergy));
					}
				}

				if (energyInjected >= energyPath.minInsulationBreakdownEnergy) {
					for (IEnergyConductor energyConductor : energyPath.conductors) {
						if (energyInjected >= energyConductor
								.getInsulationBreakdownEnergy()) {
							energyConductor.removeInsulation();

							if (energyConductor.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption)
								energyPath.minInsulationEnergyAbsorption = energyConductor
										.getInsulationEnergyAbsorption();
						}
					}
				}
			}

			if (energyInjected >= energyPath.minConductorBreakdownEnergy)
				for (IEnergyConductor energyConductor : energyPath.conductors)
					if (energyInjected >= energyConductor
							.getConductorBreakdownEnergy())
						energyConductor.removeConductor();
		}

		return amount;
	}

	@Deprecated
	public long getTotalEnergyConducted(TileEntity tileEntity) {
		long ret = 0L;

		if (((tileEntity instanceof IEnergyConductor))
				|| ((tileEntity instanceof IEnergySink))) {
			List<EnergyPath> reverseEnergyPaths = discover(tileEntity, true, 2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getMaxEnergyOutput() <= reverseEnergyPath.loss)) {
					continue;
				}
				for (EnergyPath energyPath :  this.energySourceToEnergyPathMap
						.get(energySource)) {
					if ((((tileEntity instanceof IEnergySink)) && (energyPath.target == tileEntity))
							|| (((tileEntity instanceof IEnergyConductor)) && (energyPath.conductors
									.contains(tileEntity)))) {
						ret += energyPath.totalEnergyConducted;
					}
				}
			}
		}

		if (((tileEntity instanceof IEnergySource))
				&& (this.energySourceToEnergyPathMap.containsKey(tileEntity))) {
			for (EnergyPath energyPath :  this.energySourceToEnergyPathMap
					.get(tileEntity)) {
				ret += energyPath.totalEnergyConducted;
			}

		}

		return ret;
	}

	public long getTotalEnergyEmitted(TileEntity tileEntity) {
		long ret = 0L;

		if ((tileEntity instanceof IEnergyConductor)) {
			List<EnergyPath> reverseEnergyPaths = discover(tileEntity, true, 2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getMaxEnergyOutput() <= reverseEnergyPath.loss)) {
					continue;
				}
				for (EnergyPath energyPath :  this.energySourceToEnergyPathMap
						.get(energySource)) {
					if (((tileEntity instanceof IEnergyConductor))
							&& (energyPath.conductors.contains(tileEntity))) {
						ret += energyPath.totalEnergyConducted;
					}
				}
			}
		}

		if (((tileEntity instanceof IEnergySource))
				&& (this.energySourceToEnergyPathMap.containsKey(tileEntity))) {
			for (EnergyPath energyPath :  this.energySourceToEnergyPathMap
					.get(tileEntity)) {
				ret += energyPath.totalEnergyConducted;
			}

		}

		return ret;
	}

	public long getTotalEnergySunken(TileEntity tileEntity) {
		long ret = 0L;

		if (((tileEntity instanceof IEnergyConductor))
				|| ((tileEntity instanceof IEnergySink))) {
			List<EnergyPath> reverseEnergyPaths = discover(tileEntity, true, 2147483647);

			for (EnergyPath reverseEnergyPath : reverseEnergyPaths) {
				IEnergySource energySource = (IEnergySource) reverseEnergyPath.target;

				if ((!this.energySourceToEnergyPathMap
						.containsKey(energySource))
						|| (energySource.getMaxEnergyOutput() <= reverseEnergyPath.loss)) {
					continue;
				}
				for (EnergyPath energyPath :  this.energySourceToEnergyPathMap
						.get(energySource)) {
					if ((((tileEntity instanceof IEnergySink)) && (energyPath.target == tileEntity))
							|| (((tileEntity instanceof IEnergyConductor)) && (energyPath.conductors
									.contains(tileEntity)))) {
						ret += energyPath.totalEnergyConducted;
					}
				}
			}
		}

		return ret;
	}

	public TileEntity getTileEntity(int x, int y, int z) {
		return this.registeredTileEntities
				.get(new ChunkCoordinates(x, y, z));
	}

	public TileEntity getNeighbor(TileEntity te, LCDirection dir) {
		ChunkCoordinates coords;
		switch (dir.ordinal()) {
		case 1:
			coords = new ChunkCoordinates(te.xCoord - 1, te.yCoord, te.zCoord);
			break;
		case 2:
			coords = new ChunkCoordinates(te.xCoord + 1, te.yCoord, te.zCoord);
			break;
		case 3:
			coords = new ChunkCoordinates(te.xCoord, te.yCoord - 1, te.zCoord);
			break;
		case 4:
			coords = new ChunkCoordinates(te.xCoord, te.yCoord + 1, te.zCoord);
			break;
		case 5:
			coords = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord - 1);
			break;
		case 6:
			coords = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord + 1);
			break;
		default:
			return null;
		}

		return this.registeredTileEntities.get(coords);
	}

	private List<EnergyPath> discover(TileEntity emitter, boolean reverse,
			int lossLimit) {
		Map<TileEntity, EnergyBlockLink> reachedTileEntities = new HashMap<TileEntity, EnergyBlockLink>();
		LinkedList<TileEntity> tileEntitiesToCheck = new LinkedList<TileEntity>();

		tileEntitiesToCheck.add(emitter);
		double currentLoss;
		while (!tileEntitiesToCheck.isEmpty()) {
			TileEntity currentTileEntity = tileEntitiesToCheck
					.remove();
			if (currentTileEntity.isInvalid()) {
				continue;
			}
			currentLoss = 0.0D;

			if (currentTileEntity != emitter)
				currentLoss = reachedTileEntities
						.get(currentTileEntity).loss;

			List<EnergyTarget> validReceivers = getValidReceivers(currentTileEntity, reverse);

			for (EnergyTarget validReceiver : validReceivers) {
				if (validReceiver.tileEntity == emitter)
					continue;
				double additionalLoss = 0.0D;

				if ((validReceiver.tileEntity instanceof IEnergyConductor)) {
					additionalLoss = ((IEnergyConductor) validReceiver.tileEntity)
							.getConductionLoss();

					if (additionalLoss < 0.0001D)
						additionalLoss = 0.0001D;

					if (currentLoss + additionalLoss >= lossLimit) {
						continue;
					}
				}
				if ((!reachedTileEntities.containsKey(validReceiver.tileEntity))
						|| (reachedTileEntities
								.get(validReceiver.tileEntity).loss > currentLoss
								+ additionalLoss)) {
					reachedTileEntities.put(validReceiver.tileEntity,
							new EnergyBlockLink(validReceiver.direction,
									currentLoss + additionalLoss));

					if ((validReceiver.tileEntity instanceof IEnergyConductor)) {
						tileEntitiesToCheck.remove(validReceiver.tileEntity);
						tileEntitiesToCheck.add(validReceiver.tileEntity);
					}
				}
			}
		}

		List<EnergyPath> energyPaths = new LinkedList<EnergyPath>();

		for (Map.Entry entry : reachedTileEntities.entrySet()) {
			TileEntity tileEntity = (TileEntity) entry.getKey();

			if (((!reverse) && ((tileEntity instanceof IEnergySink)))
					|| ((reverse) && ((tileEntity instanceof IEnergySource)))) {
				EnergyBlockLink energyBlockLink = (EnergyBlockLink) entry
						.getValue();

				EnergyPath energyPath = new EnergyPath();

				if (energyBlockLink.loss > 0.1D)
					energyPath.loss = energyBlockLink.loss;
				else {
					energyPath.loss = 0.1D;
				}

				energyPath.target = tileEntity;
				energyPath.targetDirection = energyBlockLink.direction;

				if ((!reverse) && ((emitter instanceof IEnergySource))) {
					while (true) {
						tileEntity = getNeighbor(tileEntity,
								energyBlockLink.direction);

						if (tileEntity != emitter) {
							if ((tileEntity instanceof IEnergyConductor)) {
								IEnergyConductor energyConductor = (IEnergyConductor) tileEntity;

								if (tileEntity.xCoord < energyPath.minX)
									energyPath.minX = tileEntity.xCoord;
								if (tileEntity.yCoord < energyPath.minY)
									energyPath.minY = tileEntity.yCoord;
								if (tileEntity.zCoord < energyPath.minZ)
									energyPath.minZ = tileEntity.zCoord;
								if (tileEntity.xCoord > energyPath.maxX)
									energyPath.maxX = tileEntity.xCoord;
								if (tileEntity.yCoord > energyPath.maxY)
									energyPath.maxY = tileEntity.yCoord;
								if (tileEntity.zCoord > energyPath.maxZ)
									energyPath.maxZ = tileEntity.zCoord;

								energyPath.conductors.add(energyConductor);

								if (energyConductor
										.getInsulationEnergyAbsorption() < energyPath.minInsulationEnergyAbsorption)
									energyPath.minInsulationEnergyAbsorption = energyConductor
											.getInsulationEnergyAbsorption();
								if (energyConductor
										.getInsulationBreakdownEnergy() < energyPath.minInsulationBreakdownEnergy)
									energyPath.minInsulationBreakdownEnergy = energyConductor
											.getInsulationBreakdownEnergy();
								if (energyConductor
										.getConductorBreakdownEnergy() < energyPath.minConductorBreakdownEnergy)
									energyPath.minConductorBreakdownEnergy = energyConductor
											.getConductorBreakdownEnergy();

								energyBlockLink = reachedTileEntities
										.get(tileEntity);
								if (energyBlockLink == null) {
									CBCMod.proxy
											.displayError(new StringBuilder()
													.append("An energy network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile entities below)\nE: ")
													.append(emitter)
													.append(" (")
													.append(emitter.xCoord)
													.append(",")
													.append(emitter.yCoord)
													.append(",")
													.append(emitter.zCoord)
													.append(")\n")
													.append("C: ")
													.append(tileEntity)
													.append(" (")
													.append(tileEntity.xCoord)
													.append(",")
													.append(tileEntity.yCoord)
													.append(",")
													.append(tileEntity.zCoord)
													.append(")\n")
													.append("R: ")
													.append(energyPath.target)
													.append(" (")
													.append(energyPath.target.xCoord)
													.append(",")
													.append(energyPath.target.yCoord)
													.append(",")
													.append(energyPath.target.zCoord)
													.append(")").toString());
								}

								continue;
							}
							if (tileEntity == null) {
								break;
							}
							CBCMod.log
									.warning(new StringBuilder()
											.append("EnergyNet: EnergyBlockLink corrupted (")
											.append(energyPath.target)
											.append(" [")
											.append(energyPath.target.xCoord)
											.append(" ")
											.append(energyPath.target.yCoord)
											.append(" ")
											.append(energyPath.target.zCoord)
											.append("] -> ").append(tileEntity)
											.append(" [")
											.append(tileEntity.xCoord)
											.append(" ")
											.append(tileEntity.yCoord)
											.append(" ")
											.append(tileEntity.zCoord)
											.append("] -> ").append(emitter)
											.append(" [")
											.append(emitter.xCoord).append(" ")
											.append(emitter.yCoord).append(" ")
											.append(emitter.zCoord)
											.append("])").toString());

							break;
						}
					}
				}

				energyPaths.add(energyPath);
			}
		}

		return energyPaths;
	}

	private List<EnergyTarget> getValidReceivers(TileEntity emitter,
			boolean reverse) {
		List<EnergyTarget> validReceivers = new LinkedList<EnergyTarget>();

		for (LCDirection direction : directions) {
			TileEntity target = getNeighbor(emitter, direction);
			if (target == null)
				continue;
			LCDirection inverseDirection = direction.getInverse();

			if (((reverse) || (!(emitter instanceof IEnergyEmitter)) || (!((IEnergyEmitter) emitter)
					.emitsEnergyTo(target, direction)))
					&& ((!reverse) || (!(emitter instanceof IEnergyAcceptor)) || (!((IEnergyAcceptor) emitter)
							.acceptEnergyFrom(target, direction)))) {
				continue;
			}
			if (((reverse) || (!(target instanceof IEnergyAcceptor)) || (!((IEnergyAcceptor) target)
					.acceptEnergyFrom(emitter, inverseDirection)))
					&& ((!reverse) || (!(target instanceof IEnergyEmitter)) || (!((IEnergyEmitter) target)
							.emitsEnergyTo(emitter, inverseDirection)))) {
				continue;
			}
			validReceivers.add(new EnergyTarget(target, inverseDirection));
		}

		return validReceivers;
	}

	static {
		directions = LCDirection.values();

		apiDemandsErrorCooldown = 0;
		apiEmitErrorCooldown = 0;
	}

	private static class PostPonedAddCallback implements ITickCallback {
		private final TileEntity te;

		public PostPonedAddCallback(TileEntity te) {
			this.te = te;
		}

		public void tickCallback(World world) {
			if ((world.blockExists(this.te.xCoord, this.te.yCoord,
					this.te.zCoord)) && (!this.te.isInvalid()))
				EnergyNet.getForWorld(world).addTileEntity(this.te);
			else
				CBCMod.log.info("EnergyNet.addTileEntity: " + this.te
						+ " unloaded, aborting");
		}
	}

	static class EnergyTarget {
		TileEntity tileEntity;
		LCDirection direction;

		EnergyTarget(TileEntity tileEntity, LCDirection direction) {
			this.tileEntity = tileEntity;
			this.direction = direction;
		}
	}

	static class EnergyBlockLink {
		LCDirection direction;
		double loss;

		EnergyBlockLink(LCDirection direction, double loss) {
			this.direction = direction;
			this.loss = loss;
		}
	}

	static class EnergyPath {
		TileEntity target = null;
		LCDirection targetDirection;
		Set<IEnergyConductor> conductors = new HashSet<IEnergyConductor>();

		int minX = 2147483647;
		int minY = 2147483647;
		int minZ = 2147483647;
		int maxX = -2147483648;
		int maxY = -2147483648;
		int maxZ = -2147483648;

		double loss = 0.0D;
		int minInsulationEnergyAbsorption = 2147483647;
		int minInsulationBreakdownEnergy = 2147483647;
		int minConductorBreakdownEnergy = 2147483647;
		long totalEnergyConducted = 0L;
	}

	public static class EventHandler {
		public EventHandler() {
			MinecraftForge.EVENT_BUS.register(this);
		}

		@ForgeSubscribe
		public void onEnergyTileLoad(EnergyTileLoadEvent event) {
			EnergyNet.getForWorld(event.world).addTileEntity(
					(TileEntity) event.energyTile);
		}

		@ForgeSubscribe
		public void onEnergyTileUnload(EnergyTileUnLoadEvent event) {
			EnergyNet.getForWorld(event.world).removeTileEntity(
					(TileEntity) event.energyTile);
		}

		@ForgeSubscribe
		public void onEnergyTileSource(EnergyTileSourceEvent event) {
			event.amount = EnergyNet.getForWorld(event.world).emitEnergyFrom(
					(IEnergySource) event.energyTile, event.amount);
		}
	}

	public static void addSingleTickCallback(World world,
			ITickCallback tickCallback) {
		WorldData worldData = WorldData.get(world);

		worldData.singleTickCallbacks.add(tickCallback);
	}
}