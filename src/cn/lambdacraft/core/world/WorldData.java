package cn.lambdacraft.core.world;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;

import cn.lambdacraft.core.energy.EnergyNet;
import cn.lambdacraft.core.energy.ITickCallback;
import net.minecraft.world.World;

public class WorldData {
	private static Map<World, WorldData> mapping = new WeakHashMap<World, WorldData>();
	
	  public Queue<ITickCallback> singleTickCallbacks = new ArrayDeque<ITickCallback>();
	  public Set<ITickCallback> continuousTickCallbacks = new HashSet<ITickCallback>();
	  public boolean continuousTickCallbacksInUse = false;
	  public List<ITickCallback> continuousTickCallbacksToAdd = new ArrayList<ITickCallback>();
	  public List<ITickCallback> continuousTickCallbacksToRemove = new ArrayList<ITickCallback>();

	public EnergyNet energyNet = new EnergyNet();
	
	public int ticksLeftToNetworkUpdate = 2;

	public static WorldData get(World world) {
		if (world == null)
			throw new IllegalArgumentException("world is null");

		WorldData rew =  (WorldData)mapping.get(world);

		if (rew == null) {
			rew = new WorldData();
			mapping.put(world, rew);
		}
		return rew;
	}

	public static void onWorldUnload(World world) {
		mapping.remove(world);
	}
}
