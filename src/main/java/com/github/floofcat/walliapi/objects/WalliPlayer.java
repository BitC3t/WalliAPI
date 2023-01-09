package com.github.floofcat.walliapi.objects;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.config.FileManager;
import com.github.floofcat.walliapi.objects.teams.Team;
import com.github.floofcat.walliapi.spectator.SpectatorEvents;
import com.github.floofcat.walliapi.spectator.SpectatorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class WalliPlayer {

    private UUID uuid;
    private Player player;
    private Team team;
    private boolean isSpectator = false;
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

    public boolean isSpectator() {
        return this.isSpectator;
    }

    public void setSpectator(boolean spectator) {
        this.isSpectator = spectator;

        if(spectator == true) {
            for(ItemStack itemStack : player.getInventory()) {
                player.getLocation().getWorld().dropItemNaturally(player.getLocation(), itemStack);
            }

            player.getInventory().clear();

            SpectatorUtils.setSpectator(player);
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
