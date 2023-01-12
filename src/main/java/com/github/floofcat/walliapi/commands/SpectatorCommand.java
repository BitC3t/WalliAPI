package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.spectator.SpectatorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SpectatorCommand {

    private final WalliAPI plugin;

    public SpectatorCommand(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(String[] args, Player player) {
        String playerName = args[1];
        Player p = Bukkit.getPlayer(playerName);

        WalliPlayer walliPlayer = this.plugin.getWalliPlayer(p);

        if(walliPlayer.isSpectator()) {
            walliPlayer.setSpectator(false);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&6" + '\u270E' + "&7] &f" + playerName + " has been &cremoved from being a spectator."));
        } else {
            walliPlayer.setSpectator(true);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&6" + '\u270E' + "&7] &f" + playerName + " has been &amade a spectator."));
        }


    }
}
