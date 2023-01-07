package com.github.floofcat.walliapi.objects;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.config.FileManager;
import com.github.floofcat.walliapi.objects.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class WalliPlayer {

    private UUID uuid;
    private Player player;
    private Team team;
    private int score = 0;

    public WalliPlayer(UUID uuid, Team team, int score) {
        this.uuid = uuid;
        this.team = team;
        this.score = score;

        if(Bukkit.getPlayer(uuid) != null) {
            this.player = Bukkit.getPlayer(uuid);
        } else {
            this.player = null;
        }
    }

    public int getScore() {
        return this.score;
    }

    public void addScore(int a) {
        this.score += a;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Team getTeam() {
        return this.team;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public boolean isValid() {
        if(this.player != null) {
            return true;
        }

        return false;
    }

    public void changeTeam(Team team) {
        this.team = team;

        if(!this.isValid()) {
            return;
        }

        WalliAPI.getInstance().getTeamRegistry().switchTeam(this.player, team);
    }

    // Run only in onJoinEvents.
    public void apiSetup() {
        this.player = Bukkit.getPlayer(this.uuid);
    }

    // Run only in onLeaveEvents.
    public void apiDeregister() {
        this.player = null;
    }
}
