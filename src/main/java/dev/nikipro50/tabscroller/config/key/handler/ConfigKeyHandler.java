package dev.nikipro50.tabscroller.config.key.handler;

import dev.nikipro50.tabscroller.config.key.base.ConfigKeyRegister;
import dev.nikipro50.tabscroller.config.screen.ConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.Screen;

public class ConfigKeyHandler {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ConfigKeyRegister.openConfigKey.wasPressed()) {
                if (client.currentScreen != null) continue;

                Screen screen = ConfigScreen.create(client.currentScreen);
                client.setScreen(screen);
            }
        });
    }
}
