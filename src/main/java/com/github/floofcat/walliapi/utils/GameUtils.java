package com.github.floofcat.walliapi.utils;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameUtils {

    public static void broadcast(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    message));
        }
    }

    public static char getSwordIcon() {
        return '\u2694';
    }

    public static void sendActionBar(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(PlainTextComponentSerializer.plainText().deserialize(
                    ChatColor.translateAlternateColorCodes('&',message)));
        }
    }

    public static char getSkullIcon() {
        return '\u2620';
    }
}
