package com.github.floofcat.walliapi.objects.teams;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.config.FileManager;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import org.bukkit.block.data.type.Wall;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TeamRegistry {

    private final WalliAPI plugin;

    public TeamRegistry(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void readTeams() throws IOException {
        FileManager teamManager = new FileManager("teams.yml", this.plugin);
        teamManager.createFile();

        for(String teamName : teamManager.getConfig().getKeys(false)) {
            String formattedName = teamManager.getConfig().getString(teamName + ".formatted-name");
            String teamColor = teamManager.getConfig().getString(teamName + ".team-color");
            String teamSymbol = teamManager.getConfig().getString(teamName + ".team-symbol");
            boolean isPlaying = teamManager.getConfig().getBoolean(teamName + ".is-playing");
            int scores = teamManager.getConfig().getInt(teamName + ".team-score");

            Team team = new Team(teamName, formattedName, teamColor, teamSymbol, isPlaying, scores);
            this.plugin.getTeamController().addTeam(team);
        }
    }

    public void readPlayers() throws IOException {
        FileManager playerManager = new FileManager("players.yml", this.plugin);
        playerManager.createFile();

        for(String playerUUID : playerManager.getConfig().getKeys(false)) {
            try {
               UUID uuid = UUID.fromString(playerUUID);
               int playerScore = playerManager.getConfig().getInt(uuid + ".player-score");
               String teamName = playerManager.getConfig().getString(uuid + ".player-team");

               Team team = this.plugin.getTeamController().getTeamFromName(teamName);
               WalliPlayer walliPlayer = new WalliPlayer(uuid, team, playerScore);

               this.plugin.getTeamController().addPlayer(walliPlayer);

            } catch (Exception e) {
                this.plugin.getLogger().info("Failed to read the players.yml.");
            }
        }
    }
}
