package dev.nikipro50.tabscroller.mixin;

import dev.nikipro50.tabscroller.storage.LocalStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;

@Mixin(PlayerListHud.class)
public class TabMixin {
    @Final
    @Shadow
    private static Comparator<PlayerListEntry> ENTRY_ORDERING;

    @Inject(
            method = "collectPlayerEntries",
            at = @At("RETURN"),
            cancellable = true
    )
    private void onCollectPlayerEntries(CallbackInfoReturnable<List<PlayerListEntry>> cir) {
        List<PlayerListEntry> fullList = MinecraftClient.getInstance()
                .getNetworkHandler()
                .getPlayerList()
                .stream()
                .sorted(ENTRY_ORDERING)
                .toList();

        if (fullList.size() <= 80) {
            LocalStorage.TAB_PAGE = 0;
            cir.setReturnValue(fullList.stream().limit(80L).toList());
            return;
        }

        int toSubtract = LocalStorage.TAB_PAGE * 20;

        if (fullList.size() - toSubtract <= 80) {
            while (fullList.size() - toSubtract <= 60 && LocalStorage.TAB_PAGE > 0) {
                LocalStorage.TAB_PAGE--;
                toSubtract = LocalStorage.TAB_PAGE * 20;
            }
        }

        int end = Math.min(80 + toSubtract, fullList.size());
        cir.setReturnValue(fullList.subList(toSubtract, end));
    }
}
