package dev.nikipro50.tabscroller.handler;

import dev.nikipro50.tabscroller.config.manager.ConfigManager;
import dev.nikipro50.tabscroller.storage.LocalStorage;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;

public class TabInputHandler {
    private static boolean leftArrowPressed = false, rightArrowPressed = false;

    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player == null && client.getWindow() == null && client.getNetworkHandler() == null) return;

            Window window = client.getWindow();
            long handle = window.getHandle();

            boolean isTabPressed = client.options.playerListKey.isPressed(),
                    isLeftArrowDown = InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_LEFT),
                    isRightArrowDown = InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_RIGHT);

            if (!isTabPressed) {
                leftArrowPressed = false;
                rightArrowPressed = false;

                if (LocalStorage.TAB_PAGE == 0) return;
                if (!ConfigManager.CONFIG.resetTabOnRelease) return;

                LocalStorage.TAB_PAGE = 0;
                return;
            }

            int total = client.getNetworkHandler().getPlayerList().size();
            int maxPages = Math.max(1, (int) Math.ceil(Math.max(0, total - 80) / 20.0));

            if (isLeftArrowDown) {
                if (leftArrowPressed) return;

                leftArrowPressed = true;
                if (ConfigManager.CONFIG.wrapNavigation) {
                    LocalStorage.TAB_PAGE =
                            (LocalStorage.TAB_PAGE - 1 + maxPages) % maxPages;
                    return;
                }

                if (LocalStorage.TAB_PAGE <= 0) return;
                LocalStorage.TAB_PAGE--;
            } else {
                leftArrowPressed = false;
            }

            if (isRightArrowDown) {
                if (rightArrowPressed) return;

                rightArrowPressed = true;
                if (ConfigManager.CONFIG.wrapNavigation) {
                    LocalStorage.TAB_PAGE =
                            (LocalStorage.TAB_PAGE + 1) % maxPages;

                    return;
                }

                if (LocalStorage.TAB_PAGE >= maxPages - 1) return;
                LocalStorage.TAB_PAGE++;
            } else {
                rightArrowPressed = false;
            }
        });
    }
}
