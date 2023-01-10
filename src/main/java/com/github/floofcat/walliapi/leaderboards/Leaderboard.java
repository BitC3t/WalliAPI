package com.github.floofcat.walliapi.leaderboards;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Leaderboard {

    private HashMap<WalliPlayer, FastBoard> boardMap = new HashMap<>();

    public void enable() {
        WalliAPI.getInstance().getTeamController().getPlayers().forEach(wp -> {
            if(wp.isValid()) {
                Player player = wp.getPlayer();
                FastBoard fb = new FastBoard(player);

                boardMap.put(wp, fb);
            }
        });
    }

    public HashMap<WalliPlayer, FastBoard> getBoardMap() {
        return this.boardMap;
    }

    public FastBoard addPlayer(WalliPlayer wp) {
        FastBoard fastBoard = new FastBoard(wp.getPlayer());
        boardMap.put(wp, fastBoard);

        return fastBoard;
    }
}
