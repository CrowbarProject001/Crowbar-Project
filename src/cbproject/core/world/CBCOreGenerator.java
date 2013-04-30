package cbproject.core.world;

import java.util.Random;

import cbproject.core.register.CBCBlocks;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class CBCOreGenerator  implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId){
		case -1:
		    generateNether(world, random, chunkX * 16, chunkZ * 16);
		    break;
		case 0:
		    generateSurface(world, random, chunkX * 16, chunkZ * 16);
		    break;
		case 1:
		    generateEnd(world, random, chunkX * 16, chunkZ * 16);
		    break;
		}
	}

	private void generateEnd(World world, Random random, int i, int j) {
		
	}

	private void generateSurface(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		for(int k = 0; k < 10; k++) {
			int uraniumOreXCoord = i + random.nextInt(16);
			int uraniumOreYCoord = random.nextInt(35);
			int uraniumOreZCoord = j + random.nextInt(16);
			
			(new WorldGenMinable(CBCBlocks.blockUraniumOre.blockID, 3)).generate(world, random, uraniumOreXCoord, uraniumOreYCoord, uraniumOreZCoord);
		}
	}

	private void generateNether(World world, Random random, int i, int j) {
		// TODO Auto-generated method stub
		
	}

}
