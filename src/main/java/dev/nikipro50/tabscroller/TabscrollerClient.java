package dev.nikipro50.tabscroller;

import dev.nikipro50.tabscroller.key.KeyBindings;
import dev.nikipro50.tabscroller.tab.TabManager;
import net.fabricmc.api.ClientModInitializer;

public class TabscrollerClient implements ClientModInitializer {
    public static TabManager tabManager;

    @Override
    public void onInitializeClient() {
        KeyBindings.register();
        tabManager = new TabManager();
    }
}
