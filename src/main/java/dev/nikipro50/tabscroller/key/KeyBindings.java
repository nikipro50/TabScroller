package dev.nikipro50.tabscroller.key;

import dev.nikipro50.tabscroller.TabscrollerClient;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyBinding nextPage, prevPage;

    public static void register() {
        nextPage = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tabscroller.next",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                "key.categories.tabscroller"
        ));

        prevPage = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.tabscroller.prev",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "key.categories.tabscroller"
        ));
    }

    public static void tick() {
        if (nextPage.wasPressed()) {
            TabscrollerClient.tabManager.nextPage();
            return;
        }

        if (prevPage.wasPressed()) {
            TabscrollerClient.tabManager.prevPage();
            return;
        }
    }
}

