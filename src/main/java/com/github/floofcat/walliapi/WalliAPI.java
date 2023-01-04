package com.github.floofcat.walliapi;

import com.github.floofcat.walliapi.events.RegistryEvents;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.objects.teams.TeamController;
import com.github.floofcat.walliapi.objects.teams.TeamRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class WalliAPI extends JavaPlugin {

    private TeamController teamController = new TeamController(this);
    private TeamRegistry teamRegistry = new TeamRegistry(this);

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Initialisation of Controllers
        try {
            this.teamRegistry.init();
        } catch (IOException e) {
            this.getLogger().info("[C] Failed to initialise controllers.");
        }

        // Registration of Events & Commands
        this.registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public TeamController getTeamController() {
        return this.teamController;
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

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new RegistryEvents(this), this);
    }
}
