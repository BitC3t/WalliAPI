package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatCommand {

    private final WalliAPI plugin;

    public ChatCommand(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(String[] args, Player player) {
        boolean bool = Boolean.valueOf(args[1]);

        this.plugin.setSplitChat(bool);
        player.sendMessage(ChatColor.RED + "You have set split chat to: " + bool);
    }
}
