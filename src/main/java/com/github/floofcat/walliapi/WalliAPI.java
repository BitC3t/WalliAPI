package com.github.floofcat.walliapi;

import com.github.floofcat.walliapi.objects.teams.TeamController;
import org.bukkit.plugin.java.JavaPlugin;

public final class WalliAPI extends JavaPlugin {

    private TeamController teamController = new TeamController(this);

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public TeamController getTeamController() {
        return this.teamController;
    }
}
