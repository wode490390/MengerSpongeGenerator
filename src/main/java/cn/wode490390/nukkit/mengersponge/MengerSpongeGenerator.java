package cn.wode490390.nukkit.mengersponge;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import java.util.Collections;
import java.util.Map;

public class MengerSpongeGenerator extends Generator {

    protected ChunkManager level;
    protected MengerSpongeGeneratorSettings settings;

    public MengerSpongeGenerator() {
        this(null);
    }

    public MengerSpongeGenerator(Map<String, Object> options) {
        this.settings = MengerSpongeGeneratorPlugin.getInstance().getSettings();
    }

    @Override
    public int getId() {
        return TYPE_INFINITE;
    }

    @Override
    public String getName() {
        return "normal";
    }

    @Override
    public ChunkManager getChunkManager() {
        return this.level;
    }

    @Override
    public Map<String, Object> getSettings() {
        return Collections.emptyMap();
    }

    @Override
    public void init(ChunkManager level, NukkitRandom random) {
        this.level = level;
    }

    @Override
    public void generateChunk(int chunkX, int chunkZ) {
        BaseFullChunk chunk = this.level.getChunk(chunkX, chunkZ);
        int baseX = chunkX << 4;
        int baseZ = chunkZ << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    if (isInSponge(baseX + x, y, baseZ + z)) {
                        chunk.setBlock(x, y, z, this.settings.getBlockId(), this.settings.getBlockMeta());
                    }
                }
                chunk.setBiome(x, z, EnumBiome.PLAINS.biome);
            }
        }
    }

    @Override
    public void populateChunk(int chunkX, int chunkZ) {

    }

    @Override
    public Vector3 getSpawn() {
        return new Vector3(0.5, 256, 0.5);
    }

    protected static boolean isInSponge(int x, int y, int z) {
        x = Math.abs(x);
        z = Math.abs(z);

        while (x != 0 || y != 0 || z != 0) {
            if (((x % 3 == 1) ? 1 : 0) + ((y % 3 == 1) ? 1 : 0) + ((z % 3 == 1) ? 1 : 0) >= 2) {
                return false;
            }

            x /= 3;
            y /= 3;
            z /= 3;
        }

        return true;
    }
}
