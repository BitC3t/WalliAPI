package com.github.floofcat.walliapi.game.timer;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    private int countTicks;
    private int currentTicks;
    private String title;
    private BossBar bar;

    public GameTimer(int numberTicks, String title) {
        this.currentTicks = numberTicks;
        this.countTicks = numberTicks;
        this.title = title;

        this.bar = Bukkit.createBossBar(title, BarColor.BLUE, BarStyle.SOLID);
        this.bar.setVisible(true);
    }

    @Override
    public void run() {
        if(this.currentTicks == 0) {
            this.cancel();

            this.bar.setVisible(false);
            this.bar.removeAll();

            return;
        }

        this.update();
    }

    public void update() {
        this.currentTicks -= 1;

        if(this.currentTicks == 0) {
            return;
        }

        this.bar.setTitle(title + " - " + this.secondsToString(this.currentTicks / 20));
        this.bar.setProgress((float) this.currentTicks / this.countTicks);
    }

    public void addPlayer(Player player) {
        this.bar.addPlayer(player);
    }

    public int getTimeLeft() {
        return this.currentTicks;
    }

    private String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}
