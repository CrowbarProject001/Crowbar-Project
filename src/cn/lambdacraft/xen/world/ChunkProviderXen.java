package cn.lambdacraft.xen.world;

import java.util.List;
import java.util.Random;

import cn.lambdacraft.xen.register.XENBlocks;
import cn.lambdacraft.xen.world.gen.structure.MapGenXenBridge;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

public class ChunkProviderXen implements IChunkProvider {
	private Random xenRNG;

	/** A NoiseGeneratorOctaves used in generating nether terrain */
	private NoiseGeneratorOctaves xenNoiseGen1;
	private NoiseGeneratorOctaves xenNoiseGen2;
	private NoiseGeneratorOctaves xenNoiseGen3;

	/** Is the world that the nether is getting generated. */
	private World worldObj;
	private double[] noiseField;
	public MapGenXenBridge genXenBridge = new MapGenXenBridge();

	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;

	private MapGenBase netherCaveGenerator = new MapGenCavesHell();

	private double[] netherrackExclusivityNoise = new double[256];

	{
		genXenBridge = (MapGenXenBridge) TerrainGen.getModdedMapGen(genXenBridge, EventType.CUSTOM);
		netherCaveGenerator = TerrainGen.getModdedMapGen(netherCaveGenerator, EventType.CUSTOM);
	}

	/**
	 * @param par1World
	 *            World实例
	 * @param par2
	 *            种子
	 */
	public ChunkProviderXen(World par1World, long par2) {
		this.worldObj = par1World;
		this.xenRNG = new Random(par2);

		this.xenNoiseGen1 = new NoiseGeneratorOctaves(this.xenRNG, 16);
		this.xenNoiseGen1 = new NoiseGeneratorOctaves(this.xenRNG, 16);
		this.xenNoiseGen1 = new NoiseGeneratorOctaves(this.xenRNG, 8);

		NoiseGeneratorOctaves[] noiseGens = { xenNoiseGen1, xenNoiseGen2, xenNoiseGen3, };
		noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.xenRNG, noiseGens);

		this.xenNoiseGen1 = noiseGens[0];
		this.xenNoiseGen1 = noiseGens[1];
		this.xenNoiseGen1 = noiseGens[2];

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#chunkExists(int, int)
	 */
	@Override
	public boolean chunkExists(int i, int j) {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#provideChunk(int, int)
	 */
	@Override
	public Chunk provideChunk(int i, int j) {
		// TODO Auto-generated method stub
		this.xenRNG.setSeed((long) i * 341873128712L + (long) j * 132897987541L);
		byte[] abyte = new byte[32768];
		this.generateXenTerrain(i, j, abyte);
		this.replaceBlocksForBiome(i, j, abyte);
		this.genXenBridge.generate(this, this.worldObj, i, j, abyte);
		Chunk chunk = new Chunk(this.worldObj, abyte, i, j);

		chunk.resetRelightChecks();
		return chunk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#loadChunk(int, int)
	 */
	@Override
	public Chunk loadChunk(int i, int j) {
		// TODO Auto-generated method stub
		return this.provideChunk(i, j);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.world.chunk.IChunkProvider#populate(net.minecraft.world
	 * .chunk.IChunkProvider, int, int)
	 */
	@Override
	public void populate(IChunkProvider ichunkprovider, int i, int j) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#saveChunks(boolean,
	 * net.minecraft.util.IProgressUpdate)
	 */
	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#unloadQueuedChunks()
	 */
	@Override
	public boolean unloadQueuedChunks() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#canSave()
	 */
	@Override
	public boolean canSave() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#makeString()
	 */
	@Override
	public String makeString() {
		// TODO Auto-generated method stub
		return "XenRandomLevelSource";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.world.chunk.IChunkProvider#getPossibleCreatures(net.minecraft
	 * .entity.EnumCreatureType, int, int, int)
	 */
	@Override
	public List getPossibleCreatures(EnumCreatureType enumcreaturetype, int i, int j, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.world.chunk.IChunkProvider#findClosestStructure(net.minecraft
	 * .world.World, java.lang.String, int, int, int)
	 */
	@Override
	public ChunkPosition findClosestStructure(World world, String s, int i, int j, int k) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#getLoadedChunkCount()
	 */
	@Override
	public int getLoadedChunkCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#recreateStructures(int,
	 * int)
	 */
	@Override
	public void recreateStructures(int i, int j) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.world.chunk.IChunkProvider#func_104112_b()
	 */
	@Override
	public void func_104112_b() {
		// TODO Auto-generated method stub

	}

	/**
	 * 为Biomes置换方块
	 * 
	 * @param par1
	 * @param par2
	 * @param par3ArrayOfByte
	 */
	public void replaceBlocksForBiome(int par1, int par2, byte[] par3ArrayOfByte) {
		return;
	}

	/**
	 * 建造Xen的地形
	 * 
	 * @param par1
	 * @param par2
	 * @param par3ArrayOfByte
	 */
	public void generateXenTerrain(int par1, int par2, byte[] par3ArrayOfByte) {
		byte b0 = 4;
		byte b1 = 32;
		int k = b0 + 1;
		byte b2 = 17;
		int l = b0 + 1;
		this.noiseField = this.initializeNoiseField(this.noiseField, par1 * b0, 0, par2 * b0, k, b2, l);

		for (int i1 = 0; i1 < b0; ++i1) {
			for (int j1 = 0; j1 < b0; ++j1) {
				for (int k1 = 0; k1 < 16; ++k1) {
					double d0 = 0.125D;
					double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
					double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
					double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
					double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
					double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
					double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
					double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
					double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;

					for (int l1 = 0; l1 < 8; ++l1) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i2 = 0; i2 < 4; ++i2) {
							int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
							short short1 = 128;
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * d14;

							for (int k2 = 0; k2 < 4; ++k2) {
								int l2 = 0;

								if (k1 * 8 + l1 < b1) {
									l2 = XENBlocks.stone.blockID;
								}

								if (d15 > 0.0D) {
									l2 = XENBlocks.stone.blockID;
								}

								par3ArrayOfByte[j2] = (byte) l2;
								j2 += short1;
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}


	/**
	 * generates a subset of the level's terrain data. Takes 7 arguments: the
	 * [empty] noise array, the position, and the size.
	 */
	private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7) {
		ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Result.DENY)
			return event.noisefield;
		if (par1ArrayOfDouble == null) {
			par1ArrayOfDouble = new double[par5 * par6 * par7];
		}

		double d0 = 684.412D;
		double d1 = 2053.236D;
		this.noiseData4 = this.xenNoiseGen1.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0D, 0.0D, 1.0D);
		this.noiseData5 = this.xenNoiseGen2.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0D, 0.0D, 100.0D);
		this.noiseData1 = this.xenNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
		this.noiseData2 = this.xenNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
		this.noiseData3 = this.xenNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
		int k1 = 0;
		int l1 = 0;
		double[] adouble1 = new double[par6];
		int i2;

		for (i2 = 0; i2 < par6; ++i2) {
			adouble1[i2] = Math.cos((double) i2 * Math.PI * 6.0D / (double) par6) * 2.0D;
			double d2 = (double) i2;

			if (i2 > par6 / 2) {
				d2 = (double) (par6 - 1 - i2);
			}

			if (d2 < 4.0D) {
				d2 = 4.0D - d2;
				adouble1[i2] -= d2 * d2 * d2 * 10.0D;
			}
		}

		for (i2 = 0; i2 < par5; ++i2) {
			for (int j2 = 0; j2 < par7; ++j2) {
				double d3 = (this.noiseData4[l1] + 256.0D) / 512.0D;

				if (d3 > 1.0D) {
					d3 = 1.0D;
				}

				double d4 = 0.0D;
				double d5 = this.noiseData5[l1] / 8000.0D;

				if (d5 < 0.0D) {
					d5 = -d5;
				}

				d5 = d5 * 3.0D - 3.0D;

				if (d5 < 0.0D) {
					d5 /= 2.0D;

					if (d5 < -1.0D) {
						d5 = -1.0D;
					}

					d5 /= 1.4D;
					d5 /= 2.0D;
					d3 = 0.0D;
				} else {
					if (d5 > 1.0D) {
						d5 = 1.0D;
					}

					d5 /= 6.0D;
				}

				d3 += 0.5D;
				d5 = d5 * (double) par6 / 16.0D;
				++l1;

				for (int k2 = 0; k2 < par6; ++k2) {
					double d6 = 0.0D;
					double d7 = adouble1[k2];
					double d8 = this.noiseData2[k1] / 512.0D;
					double d9 = this.noiseData3[k1] / 512.0D;
					double d10 = (this.noiseData1[k1] / 10.0D + 1.0D) / 2.0D;

					if (d10 < 0.0D) {
						d6 = d8;
					} else if (d10 > 1.0D) {
						d6 = d9;
					} else {
						d6 = d8 + (d9 - d8) * d10;
					}

					d6 -= d7;
					double d11;

					if (k2 > par6 - 4) {
						d11 = (double) ((float) (k2 - (par6 - 4)) / 3.0F);
						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					if ((double) k2 < d4) {
						d11 = (d4 - (double) k2) / 4.0D;

						if (d11 < 0.0D) {
							d11 = 0.0D;
						}

						if (d11 > 1.0D) {
							d11 = 1.0D;
						}

						d6 = d6 * (1.0D - d11) + -10.0D * d11;
					}

					par1ArrayOfDouble[k1] = d6;
					++k1;
				}
			}
		}

		return par1ArrayOfDouble;
	}
}
