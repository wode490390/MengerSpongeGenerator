package cn.wode490390.nukkit.mengersponge;

public class MengerSpongeGeneratorSettings {

    private final int blockId;
    private final int blockMeta;

    public MengerSpongeGeneratorSettings(int blockId, int blockMeta) {
        this.blockId = blockId;
        this.blockMeta = blockMeta;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public int getBlockMeta() {
        return this.blockMeta;
    }
}
