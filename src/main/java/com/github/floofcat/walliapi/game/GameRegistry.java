package com.github.floofcat.walliapi.game;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.config.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GameRegistry {

    private Set<Game> registeredGames = new HashSet<>();
    private HashMap<String, UUID> gameHash = new HashMap<>();
    private final WalliAPI plugin;
    private FileManager gameManager;

    public GameRegistry(WalliAPI plugin) {
        this.plugin = plugin;
    }

    public void init() throws IOException {
        this.gameManager = new FileManager("games.yml", this.plugin);
        gameManager.createFile();

        this.readGames();
    }

    public void registerGame(Game game) throws IOException {
        this.registeredGames.add(game);

        // Check if Game is pre-registered
        UUID uuid = null;
        for(String gameIdentifier : gameHash.keySet()) {
            if(game.getGameIdentifier().equals(gameIdentifier)) {
                uuid = gameHash.get(game.getGameIdentifier());
                break;
            }
        }

        // Not registered
        if(uuid == null) {
            YamlConfiguration config = this.gameManager.getConfig();

            uuid = UUID.randomUUID();
            config.set(game.getGameIdentifier() + ".uuid", uuid);

            this.gameManager.save(config);
        }

        game.setUUID(uuid);
    }

    public boolean isRegistered(Game game) {
        return this.registeredGames.contains(game);
    }

    public void readGames() {
        for(String gameIdentifier : this.gameManager.getConfig().getKeys(false)) {
            UUID uuid = UUID.fromString(gameIdentifier + ".uuid");

            this.gameHash.put(gameIdentifier, uuid);
        }
    }
}
