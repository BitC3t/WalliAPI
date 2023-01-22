package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.objects.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RandomizeCommand {

    private final WalliAPI plugin;

    public RandomizeCommand(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void execute(Player player) {

        int noTeams = WalliAPI.getInstance().getTeamController().getPlayingTeam().size();

        int countPlayers = 0;

        for(Player p1 : Bukkit.getOnlinePlayers()) {
            if(p1.isOp()) {
                continue;
            }

            countPlayers++;
        }

        int countPerTeam = (int) Math.ceil((double) countPlayers / noTeams);

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.isOp()) {
                continue;
            }

            WalliPlayer wp = WalliAPI.getInstance().getWalliPlayer(p);

            if(wp.getTeam().isPlaying()) {
                continue;
            }

            Team validTeam = this.getTeam(countPerTeam);

            if(validTeam == null) {
                continue;
            }

            Team oldTeam = wp.getTeam();
            wp.changeTeam(validTeam);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&7[&6" + '\u270E' + "&7] &f" + p.getName() + "'s team has been set to " + validTeam.getTeamColor() + validTeam.getShortName() + " &ffrom " + oldTeam.getTeamColor() + oldTeam.getShortName() + "."));
        }
    }

    private Team getTeam(int playersPerTeam) {
        Set<Team> teams = WalliAPI.getInstance().getTeamController().getPlayingTeam();
        List<Team> teamList = new ArrayList<>();
        for(Team team : teams) {
            teamList.add(team);
            System.out.println(team.getShortName());
        }

        Collections.shuffle(teamList);
        Team chosen = null;

        for(Team team : teamList) {
            if(team.getOnlinePlayers().size() >= playersPerTeam) {
                continue;
            }

            if(!team.isPlaying()) {
                continue;
            }

            chosen = team;
            break;
        }

        return chosen;

    }
}
