package dev.nikipro50.tabscroller;

import net.fabricmc.api.ClientModInitializer;
import dev.nikipro50.tabscroller.handler.TabInputHandler;

public class TabscrollerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
	TabInputHandler.register();
    }
}
