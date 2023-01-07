package com.github.floofcat.walliapi.game.timer;

import com.github.floofcat.walliapi.game.Game;
import com.github.floofcat.walliapi.game.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    private int countTicks;
    private int currentTicks;

    public GameTimer(int numberTicks) {
        this.currentTicks = numberTicks;
        this.countTicks = numberTicks;
    }

    @Override
    public void run() {
        if(this.currentTicks == 0) {
            this.cancel();

            return;
        }

        this.update();
    }

    public void update() {
        this.currentTicks -= 1;

        if(this.currentTicks == 0) {
            return;
        }
    }
}
