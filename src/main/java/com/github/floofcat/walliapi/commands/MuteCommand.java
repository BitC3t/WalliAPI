package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MuteCommand {

    private final WalliAPI plugin;

    public MuteCommand(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(String[] args, Player player) {

        try {
            String playerName = args[1];
            Player player1 = Bukkit.getPlayer(playerName);

            if(this.plugin.mutedPlayers.contains(player1)) {
                this.plugin.mutedPlayers.remove(player1);
            } else {
                this.plugin.mutedPlayers.add(player1);
            }

            player.sendMessage(ChatColor.RED + "You have muted the player : " + player1.getName());
        } catch (Exception e) {
            this.plugin.chatMuted = !(this.plugin.chatMuted);

            player.sendMessage(ChatColor.RED + "Chat mute has been toggled : " + this.plugin.chatMuted);
        }
    }
}
