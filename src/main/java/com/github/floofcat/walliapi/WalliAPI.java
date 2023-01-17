package com.github.floofcat.walliapi;

import com.github.floofcat.walliapi.commands.ComExecutor;
import com.github.floofcat.walliapi.commands.ToggleGlowExecutor;
import com.github.floofcat.walliapi.events.ChatEvents;
import com.github.floofcat.walliapi.events.RegistryEvents;
import com.github.floofcat.walliapi.events.WorldBorderEvents;
import com.github.floofcat.walliapi.game.GameRegistry;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.objects.teams.TeamController;
import com.github.floofcat.walliapi.objects.teams.TeamRegistry;
import com.github.floofcat.walliapi.spectator.SpectatorEvents;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class WalliAPI extends JavaPlugin {

    private TeamController teamController = new TeamController(this);
    private TeamRegistry teamRegistry = new TeamRegistry(this);
    private GameRegistry gameRegistry = new GameRegistry(this);
    private static WalliAPI instance;
    private boolean splitChat = false;
    public static boolean chatMuted = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Initialisation of Controllers
        try {
            this.teamRegistry.init();
            this.gameRegistry.init();
        } catch (IOException e) {
            this.getLogger().info("[C] Failed to initialise controllers.");
        }

        // Registration of Events & Commands
        this.registerEvents();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
    }

    public static WalliAPI getInstance() {
        return instance;
    }

    public TeamController getTeamController() {
        return this.teamController;
    }

    public GameRegistry getGameRegistry() {
        return this.gameRegistry;
    }

    public TeamRegistry getTeamRegistry() {
        return this.teamRegistry;
    }

    public WalliPlayer getWalliPlayer(Player player) {
        WalliPlayer returning = null;
        for(WalliPlayer walliPlayer : this.teamController.getPlayers()) {
            if(walliPlayer == null) {
                continue;
            }

            if(walliPlayer.getUUID().equals(player.getUniqueId())) {
                returning = walliPlayer;
                break;
            }
        }

        return returning;
    }

    private void registerCommands() {
        this.getCommand("walliapi").setExecutor(new ComExecutor(this));
        this.getCommand("walliapi").setTabCompleter(new ComExecutor(this));
        this.getCommand("toggle-glow").setExecutor(new ToggleGlowExecutor());
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new RegistryEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new SpectatorEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new WorldBorderEvents(), this);
    }

    public void setSplitChat(boolean chat) {
        this.splitChat = chat;
    }

    public boolean isSplitChat() {
        return splitChat;
    }
}
