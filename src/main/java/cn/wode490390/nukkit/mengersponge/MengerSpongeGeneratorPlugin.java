package cn.wode490390.nukkit.mengersponge;

import cn.nukkit.block.Block;
import cn.nukkit.level.GlobalBlockPalette;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.wode490390.nukkit.mengersponge.util.MetricsLite;

import java.util.NoSuchElementException;

public class MengerSpongeGeneratorPlugin extends PluginBase {

    private static MengerSpongeGeneratorPlugin INSTANCE;

    private MengerSpongeGeneratorSettings settings;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        try {
            new MetricsLite(this, 6985);
        } catch (Throwable ignore) {

        }

        this.saveDefaultConfig();
        Config config = this.getConfig();

        String node = "material";
        int material = Block.SPONGE;
        try {
            material = config.getInt(node, material);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }
        int meta = 0;
        node = "meta";
        try {
            meta = config.getInt(node, meta);
        } catch (Exception e) {
            this.logConfigException(node, e);
        }

        try {
            GlobalBlockPalette.getOrCreateRuntimeId(material, 0);
            try {
                GlobalBlockPalette.getOrCreateRuntimeId(material, meta);
            } catch (NoSuchElementException e) {
                meta = 0;
                this.getLogger().warning("Invalid block meta. Use the default value.");
            }
        } catch (NoSuchElementException e) {
            material = Block.SPONGE;
            meta = 0;
            this.getLogger().warning("Invalid block ID. Use the default value.");
        }

        this.settings = new MengerSpongeGeneratorSettings(material, meta);

        Generator.addGenerator(MengerSpongeGenerator.class, "default", Generator.TYPE_INFINITE);
        Generator.addGenerator(MengerSpongeGenerator.class, "normal", Generator.TYPE_INFINITE);
    }

    public MengerSpongeGeneratorSettings getSettings() {
        return this.settings;
    }

    private void logConfigException(String node, Throwable t) {
        this.getLogger().alert("An error occurred while reading the configuration '" + node + "'. Use the default value.", t);
    }

    public static MengerSpongeGeneratorPlugin getInstance() {
        return INSTANCE;
    }
}
