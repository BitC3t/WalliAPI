package com.github.floofcat.walliapi.utils;

import com.github.floofcat.walliapi.objects.WalliPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.inventivetalent.glow.GlowAPI;

import java.util.ArrayList;
import java.util.List;

public class GlowUtils {

    public static void setGlow(WalliPlayer wp) {
        GlowAPI.Color color = null;

        if(wp.getTeam().getShortName().equals("Red")) {
            color = GlowAPI.Color.RED;
        } else if(wp.getTeam().getShortName().equals("Blue")) {
            color = GlowAPI.Color.BLUE;
        } else if(wp.getTeam().getShortName().equals("Orange")) {
            color = GlowAPI.Color.GOLD;
        } else {
            color = GlowAPI.Color.GREEN;
        }

        GlowAPI.setGlowing(wp.getPlayer(), color, Bukkit.getOnlinePlayers());
    }

    public static void stopGlow(WalliPlayer wp) {
        GlowAPI.setGlowing(wp.getPlayer(), null, Bukkit.getOnlinePlayers());
    }

    public static void stopGlowtoPlayer(WalliPlayer wp, Player hiddenPlayer) {
        GlowAPI.setGlowing(wp.getPlayer(), null, hiddenPlayer);
    }
}
