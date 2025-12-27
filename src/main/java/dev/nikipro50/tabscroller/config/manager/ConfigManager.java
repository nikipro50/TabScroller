package dev.nikipro50.tabscroller.config.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.nikipro50.tabscroller.config.base.TabConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON =
            new GsonBuilder().setPrettyPrinting().create();

    private static final Path PATH =
            FabricLoader.getInstance().getConfigDir().resolve("tab_scroller.json");

    public static TabConfig CONFIG;

    public static void load() {
        try {
            if (!Files.exists(PATH)) {
                CONFIG = new TabConfig();
                save();
                return;
            }

            CONFIG = GSON.fromJson(Files.readString(PATH), TabConfig.class);
        } catch (IOException e) {
            CONFIG = new TabConfig();
        }
    }

    public static void save() {
        try {
            Files.createDirectories(PATH.getParent());
            Files.writeString(PATH, GSON.toJson(CONFIG));
        } catch (IOException ignored) {
        }
    }
}
