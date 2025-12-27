package dev.nikipro50.tabscroller.handler;

import dev.nikipro50.tabscroller.config.manager.ConfigManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class CloseClientHandler {
    public static void register() {
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> ConfigManager.save());
    }
}
