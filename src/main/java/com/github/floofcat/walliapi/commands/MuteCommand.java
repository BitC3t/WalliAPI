package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MuteCommand {

    private final WalliAPI plugin;

    public MuteCommand(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(String[] args, Player player) {
        this.plugin.chatMuted = !(this.plugin.chatMuted);

        player.sendMessage(ChatColor.RED + "Chat mute has been toggled : " + this.plugin.chatMuted);
    }
}
