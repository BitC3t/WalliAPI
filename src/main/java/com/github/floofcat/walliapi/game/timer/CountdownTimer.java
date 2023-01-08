package com.github.floofcat.walliapi.game.timer;

import com.github.floofcat.walliapi.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTimer extends BukkitRunnable {

    private int countTicks;
    private int currentTicks;
    private boolean isCancelled = false;

    public CountdownTimer(int numberTicks) {
        this.currentTicks = numberTicks;
        this.countTicks = numberTicks;
    }

    @Override
    public void run() {
        if(this.currentTicks == 0) {
            this.cancel();
            this.isCancelled = true;

            return;
        }

        this.update();
    }

    public void update() {
        this.currentTicks -= 1;

        if(this.currentTicks == 0) {
            return;
        }

        if(this.currentTicks == 400) {
            GameUtils.broadcast("&7[&6" + GameUtils.getSwordIcon() + "&7] &fThe game is starting in &a20 &fseconds.");
        }

        if(this.currentTicks == 200) {
            GameUtils.broadcast("&7[&6" + GameUtils.getSwordIcon() + "&7] &fThe game is starting in &a10 &fseconds.");
        }

        if(this.currentTicks == 60) {
            GameUtils.broadcast("&7[&6" + GameUtils.getSwordIcon() + "&7] &fThe game is starting in &a3 &fseconds.");
        }

        if(this.currentTicks == 40) {
            GameUtils.broadcast("&7[&6" + GameUtils.getSwordIcon() + "&7] &fThe game is starting in &a2 &fseconds.");
        }

        if(this.currentTicks == 20) {
            GameUtils.broadcast("&7[&6" + GameUtils.getSwordIcon() + "&7] &fThe game is starting in &a1 &fseconds.");
        }
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
