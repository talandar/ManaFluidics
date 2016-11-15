package derpatiel.manafluidics.world;

import derpatiel.manafluidics.registry.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGen implements IWorldGenerator{
	
	public static final int CLUSTER_CHANCES_TO_SPAWN = 2;
	public static final int CLUSTER_MIN_HEIGHT=0;
	public static final int CLUSTER_MAX_HEIGHT=25;

	
	private WorldGenerator gen_ores;
	
	public WorldGen(){

        gen_ores = new WorldGenMinable(ModBlocks.crystalOre.getDefaultState(),8);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

		switch (world.provider.getDimension()) {
		case 0: //Overworld
			runGenerator(gen_ores,world,random,chunkX,chunkZ,CLUSTER_CHANCES_TO_SPAWN,CLUSTER_MIN_HEIGHT,CLUSTER_MAX_HEIGHT);
			break;
		case -1: //Nether
			//noop
			break;
		case 1: //End
			//noop
			break;
		default:
			//do gen
		}
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
	    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
	        throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

	    int heightDiff = maxHeight - minHeight + 1;
	    for (int i = 0; i < chancesToSpawn; i ++) {
	        int x = chunk_X * 16 + rand.nextInt(16);
	        int y = minHeight + rand.nextInt(heightDiff);
	        int z = chunk_Z * 16 + rand.nextInt(16);
	        generator.generate(world, rand, new BlockPos(x, y, z));
	    }
	}

}
