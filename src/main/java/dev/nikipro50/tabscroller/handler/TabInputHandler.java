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
                if (LocalStorage.TAB_PAGE == 0) return;
                if (!ConfigManager.CONFIG.resetTabOnRelease) return;

                LocalStorage.TAB_PAGE = 0;
                return;
            }
            int total = client.getNetworkHandler().getPlayerList().size();

            if (isLeftArrowDown) {
                if (leftArrowPressed) return;

                leftArrowPressed = true;
                if (LocalStorage.TAB_PAGE <= 0) return;

                LocalStorage.TAB_PAGE--;
            } else {
                leftArrowPressed = false;
            }

            if (isRightArrowDown) {
                if (rightArrowPressed) return;

                rightArrowPressed = true;
                if (80 + (LocalStorage.TAB_PAGE * 20) >= total) return;

                LocalStorage.TAB_PAGE++;
            } else {
                rightArrowPressed = false;
            }
        });
    }
}
