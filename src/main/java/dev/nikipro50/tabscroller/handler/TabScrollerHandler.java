package dev.nikipro50.tabscroller.handler;

import dev.nikipro50.tabscroller.config.manager.ConfigManager;
import dev.nikipro50.tabscroller.storage.LocalStorage;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

public class TabScrollerHandler {
    private static boolean scrollCallbackRegistered = false;

    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player == null || client.getWindow() == null) return;

            Window window = client.getWindow();
            long handle = window.getHandle();

            if (scrollCallbackRegistered) return;

            GLFW.glfwSetScrollCallback(handle, new GLFWScrollCallback() {
                @Override
                public void invoke(long window, double xOffset, double yOffset) {
                    if (!ConfigManager.CONFIG.scrollMouse) return;

                    boolean isTabPressed = client.options.playerListKey.isPressed();
                    if (!isTabPressed) return;

                    int total = client.getNetworkHandler().getPlayerList().size();
                    int maxPages = Math.max(1, (int) Math.ceil(Math.max(0, total - 80) / 20.0));

                    if (yOffset > 0) {
                        if (ConfigManager.CONFIG.wrapNavigation) {
                            LocalStorage.TAB_PAGE =
                                    (LocalStorage.TAB_PAGE - 1 + maxPages) % maxPages;
                            return;
                        }

                        if (LocalStorage.TAB_PAGE <= 0) return;
                        LocalStorage.TAB_PAGE--;
                        return;
                    }

                    if (yOffset < 0) {
                        if (ConfigManager.CONFIG.wrapNavigation) {
                            LocalStorage.TAB_PAGE =
                                    (LocalStorage.TAB_PAGE + 1) % maxPages;

                            return;
                        }

                        if (LocalStorage.TAB_PAGE >= maxPages - 1) return;
                        LocalStorage.TAB_PAGE++;
                        return;
                    }
                }
            });

            scrollCallbackRegistered = true;
        });
    }
}
