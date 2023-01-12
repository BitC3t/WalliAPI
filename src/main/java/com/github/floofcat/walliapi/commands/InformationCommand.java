package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class InformationCommand {

    private final WalliAPI plugin;

    public InformationCommand(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(String[] args, Player player) {
        String playerName = args[1];

        Player p = Bukkit.getPlayer(playerName);
        WalliPlayer wp = this.plugin.getWalliPlayer(p);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m                        "));
        player.sendMessage("Player: " + wp.getPlayer().getName());
        player.sendMessage("UUID: " + wp.getUUID());
        player.sendMessage("Score: " + wp.getScore());
        player.sendMessage("Team.Name: " + wp.getTeam().getShortName());
        player.sendMessage("Team.Color: " + ChatColor.translateAlternateColorCodes('&',
                wp.getTeam().getTeamColor() + "🛡"));
        player.sendMessage("Spectating: " + wp.isSpectator());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m                        "));
    }
}
