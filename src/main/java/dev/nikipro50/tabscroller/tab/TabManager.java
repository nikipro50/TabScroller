package dev.nikipro50.tabscroller.tab;

import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.List;

public class TabManager {
    private int currentPage = 0;
    private final int playersPerPage = 80;
    private List<PlayerListEntry> cachedPlayers = new ArrayList<>();

    public void cachePlayers(List<PlayerListEntry> players) {
        this.cachedPlayers = new ArrayList<>(players);
        currentPage = 0;
    }

    public void nextPage() {
        int maxPage = (cachedPlayers.size() - 1) / playersPerPage;
        currentPage = Math.min(currentPage + 1, maxPage);
    }

    public void prevPage() {
        currentPage = Math.max(currentPage - 1, 0);
    }

    public List<PlayerListEntry> getCurrentPagePlayers() {
        int start = currentPage * playersPerPage;
        int end = Math.min(start + playersPerPage, cachedPlayers.size());
        return cachedPlayers.subList(start, end);
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
