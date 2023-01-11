package com.github.floofcat.walliapi.utils;

import com.github.floofcat.walliapi.objects.WalliPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.inventivetalent.glow.GlowAPI;

import java.util.ArrayList;
import java.util.List;

public class GlowUtils {

    public static void setGlow(WalliPlayer wp) {
        String colors = wp.getTeam().getTeamColor();
        char colorCode = colors.charAt(1);

        GlowAPI.Color color = GlowAPI.Color.valueOf(String.valueOf(colorCode));
        GlowAPI.setGlowing(wp.getPlayer(), color, Bukkit.getOnlinePlayers());
    }

    public static void stopGlow(WalliPlayer wp) {
        GlowAPI.setGlowing(wp.getPlayer(), null, Bukkit.getOnlinePlayers());
    }
}
