package com.github.floofcat.walliapi.game;

import com.github.floofcat.walliapi.WalliAPI;
import org.bukkit.block.data.type.Wall;

import java.io.IOException;
import java.util.UUID;

public class Game {

    private UUID uuid;
    private String gameIdentifier;
    private String gameName;
    private int gameDelay;
    private final WalliAPI plugin = WalliAPI.getInstance();

    public Game(String gameIdentifier, String gameName, int gameDelay) {
        this.gameIdentifier = gameIdentifier;
        this.gameName = gameName;
        this.gameDelay = gameDelay;

        try {
            this.plugin.getGameRegistry().registerGame(this);
        } catch (IOException e) {
            this.plugin.getLogger().info("[GAME] Failed to register Game : " + gameIdentifier);
        }
    }

    public int getGameDelay() {
        return gameDelay;
    }

    public String getGameIdentifier() {
        return gameIdentifier;
    }

    public String getGameName() {
        return gameName;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
