package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.objects.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeamCommand {

    private final WalliAPI plugin;

    public TeamCommand(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player, String[] args) {
        String playerName = args[1];
        String newTeam = args[2];

        Team team = this.plugin.getTeamController().getTeamFromName(newTeam);
        WalliPlayer walliPlayer = this.plugin.getWalliPlayer(Bukkit.getPlayer(playerName));

        Team oldTeam = walliPlayer.getTeam();

        if(team == null || walliPlayer == null) {
            return;
        }

        walliPlayer.changeTeam(team);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&6" + '\u270E' + "&7] &f" + playerName + " has been moved to " + team.getTeamColor() + newTeam + " from " + oldTeam.getTeamColor() + oldTeam.getShortName() + "."));
    }
}
