package com.github.floofcat.walliapi.objects.teams;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.config.FileManager;

import java.io.File;
import java.io.IOException;

public class TeamRegistry {

    private final WalliAPI plugin;

    public TeamRegistry(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void readTeams() throws IOException {
        FileManager teamManager = new FileManager("teams.yml", this.plugin);
        teamManager.createFile();

        for(String teamName : teamManager.getConfig().getKeys(false)) {
            String formattedName = teamManager.getConfig().getString("formatted-name");
            String teamColor = teamManager.getConfig().getString("team-color");
            String teamSymbol = teamManager.getConfig().getString("team-symbol");
            boolean isPlaying = teamManager.getConfig().getBoolean("is-playing");
            int scores = teamManager.getConfig().getInt("team-score");

            Team team = new Team(teamName, formattedName, teamColor, teamSymbol, isPlaying, scores);
            this.plugin.getTeamController().addTeam(team);
        }
    }
}
