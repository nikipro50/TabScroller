package dev.nikipro50.tabscroller.mixin;

import dev.nikipro50.tabscroller.TabscrollerClient;
import dev.nikipro50.tabscroller.key.KeyBindings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        List<PlayerListEntry> players = client.player.networkHandler.getPlayerList().stream().toList();
        TabscrollerClient.tabManager.cachePlayers(players);

        KeyBindings.tick();
    }
}
