package com.github.floofcat.walliapi.objects.teams;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.config.FileManager;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.UUID;

public class TeamRegistry {

    private final WalliAPI plugin;
    private FileManager teamManager;
    private FileManager playerManager;

    public TeamRegistry(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        FileManager teamManager = new FileManager("teams.yml", this.plugin);
        teamManager.createFile();

        FileManager playerManager = new FileManager("players.yml", this.plugin);
        playerManager.createFile();

        this.readTeams();
        this.readPlayers();
    }

    public void readTeams() {
        for(String teamName : teamManager.getConfig().getKeys(false)) {
            String formattedName = teamManager.getConfig().getString(teamName + ".formatted-name");
            String teamColor = teamManager.getConfig().getString(teamName + ".team-color");
            String teamSymbol = teamManager.getConfig().getString(teamName + ".team-symbol");
            boolean isPlaying = teamManager.getConfig().getBoolean(teamName + ".is-playing");
            int scores = teamManager.getConfig().getInt(teamName + ".team-score");

            Team team = new Team(teamName, formattedName, teamColor, teamSymbol, isPlaying, scores);
            this.plugin.getTeamController().addTeam(team);

            this.plugin.getLogger().info("[T] Registered " + teamName + " from teams.yml.");
        }
    }

    public void readPlayers() {
        for(String playerUUID : playerManager.getConfig().getKeys(false)) {
            try {
               UUID uuid = UUID.fromString(playerUUID);
               int playerScore = playerManager.getConfig().getInt(uuid + ".player-score");
               String teamName = playerManager.getConfig().getString(uuid + ".player-team");
               String playerName = playerManager.getConfig().getString(uuid + ".player-name");

               Team team = this.plugin.getTeamController().getTeamFromName(teamName);
               WalliPlayer walliPlayer = new WalliPlayer(uuid, team, playerScore);

               this.plugin.getTeamController().addPlayer(walliPlayer);

               this.plugin.getLogger().info("[P] Registered " + playerName + " from players.yml.");

            } catch (Exception e) {
                this.plugin.getLogger().info("Failed to read the players.yml.");
            }
        }
    }

    // This is an assumption that this happens only when player is online.
    public void registerPlayer(UUID uuid, Team team) throws IOException {
        WalliPlayer walliPlayer = new WalliPlayer(uuid, team, 0);
        walliPlayer.apiSetup();

        YamlConfiguration config = this.playerManager.getConfig();

        config.set(uuid + ".player-score", 0);
        config.set(uuid + ".player-team", "Spectators");
        config.set(uuid + ".player-name", walliPlayer.getPlayer().getName());

        this.playerManager.save(config);
    }

    public Team getSpectatorTeam() {
        return this.plugin.getTeamController().getTeamFromName("Spectators");
    }
}
