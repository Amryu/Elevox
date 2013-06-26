
package com.elevox.main;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.elevox.noise.NoiseGenerator;
import com.elevox.noise.SimplexNoiseGenerator;

public class MoonChunkGenerator extends ChunkGenerator {
    private NoiseGenerator generator;

    private NoiseGenerator getGenerator(World world) {
        if (generator == null) {
            generator = new SimplexNoiseGenerator(world);
        }

        return generator;
    }

    private int getHeight(World world, double x, double y, int variance) {
        NoiseGenerator gen = getGenerator(world);

        double result = gen.noise(x, y);
        result *= variance;
        return NoiseGenerator.floor(result);
    }
    
    public Block[][][] generate(World world, int cx, int cz) {
        Block[][][] result = new Block[Chunk.CHUNK_WIDTH][Chunk.CHUNK_HEIGHT][Chunk.CHUNK_DEPTH];

        for (int x = 0; x < Chunk.CHUNK_WIDTH; x++) {
            for (int z = 0; z < Chunk.CHUNK_DEPTH; z++) {
                int height = getHeight(world, (cx + x) * 0.01, (cz + z) * 0.01, 10) + 128;
                for (int y = 0; y < height; y++) {
                    result[x][y][z] = Block.GRASS;
                }
                for (int y = height; y >= height && y < Chunk.CHUNK_HEIGHT; y++) {
                    result[x][y][z] = Block.AIR;
                }
            }
        }

        return result;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator)new MoonCraterPopulator());
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        int x = random.nextInt(200) - 100;
        int z = random.nextInt(200) - 100;
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
}
