package dev.nikipro50.tabscroller;

import dev.nikipro50.tabscroller.config.key.base.ConfigKeyRegister;
import dev.nikipro50.tabscroller.config.key.handler.ConfigKeyHandler;
import dev.nikipro50.tabscroller.config.manager.ConfigManager;
import dev.nikipro50.tabscroller.handler.CloseClientHandler;
import dev.nikipro50.tabscroller.handler.TabScrollerHandler;
import net.fabricmc.api.ClientModInitializer;
import dev.nikipro50.tabscroller.handler.TabInputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabscrollerClient implements ClientModInitializer {
    public static final Logger logger = LoggerFactory.getLogger("tabscroller");

    @Override
    public void onInitializeClient() {
        logger.info("TabScroller is loading...");

        ConfigManager.load();

        ConfigKeyRegister.register();

        TabScrollerHandler.register();
        ConfigKeyHandler.register();
        TabInputHandler.register();
        CloseClientHandler.register();

        logger.info("TabScroller loaded successfully.");
    }
}
