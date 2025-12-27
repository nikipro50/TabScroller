package dev.nikipro50.tabscroller.config.screen;

import dev.nikipro50.tabscroller.config.manager.ConfigManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ConfigScreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("TabScroller - Config"));
        builder.setSavingRunnable(ConfigManager::save);

        ConfigCategory general = builder.getOrCreateCategory(
                Text.literal("General")
        );
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(
                entryBuilder
                        .startBooleanToggle(
                                Text.literal("Reset Tab"),
                                ConfigManager.CONFIG.resetTabOnRelease
                        )
                        .setDefaultValue(true)
                        .setSaveConsumer(value -> ConfigManager.CONFIG.resetTabOnRelease = value)
                        .setTooltip(
                                Text.literal("If enabled, the TAB resets to the main page when the page is changed.")
                                        .formatted(Formatting.DARK_GREEN),
                                Text.literal("If disabled, the TAB page you exit from is saved.")
                                        .formatted(Formatting.RED)
                        )
                        .build()
        );

        return builder.build();
    }
}
