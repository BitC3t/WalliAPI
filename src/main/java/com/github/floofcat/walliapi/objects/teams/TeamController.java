package com.github.floofcat.walliapi.objects.teams;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;

import java.util.HashSet;
import java.util.Set;

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
}
