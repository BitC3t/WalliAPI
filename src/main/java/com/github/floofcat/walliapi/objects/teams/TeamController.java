package com.github.floofcat.walliapi.objects.teams;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TeamController {

    private Set<Team> teams = new HashSet<>();
    private Set<Team> playingTeams = new HashSet<>();
    private Set<WalliPlayer> players = new HashSet<>();
    private final WalliAPI plugin;

    public TeamController(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(WalliPlayer player) {
        this.players.add(player);
    }

    public void addTeam(Team team) {
        this.teams.add(team);

        if(team.isPlaying()) {
           playingTeams.add(team);
        }
    }

    public Set<Team> getTeams() {
        return this.teams;
    }

    public Set<WalliPlayer> getPlayers() {
        return this.players;
    }

    public Team getTeamFromName(String teamName) {
        Team returningTeam = null;
        for(Team team : this.teams) {
            if(team == null) {
                continue;
            }

            if(team.getShortName().equalsIgnoreCase(teamName)) {
                returningTeam = team;
                break;
            }
        }

        return returningTeam;
    }

    public boolean doesPlayerExist(Player player) {
        UUID uuid = player.getUniqueId();

        boolean returningBoolean = false;
        for(WalliPlayer walliPlayer : this.players) {
            if(walliPlayer == null) {
                continue;
            }

            if(!walliPlayer.isValid()) {
                continue;
            }

            if(walliPlayer.getUUID().equals(uuid)) {
                returningBoolean = true;
                break;
            }
        }

        return returningBoolean;
    }
}
