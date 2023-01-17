package com.github.floofcat.walliapi.events;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class WorldBorderEvents implements Listener {

    @EventHandler
    public void onBuild(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(isPlayerOutsideBorder(player)) {
            if(event.isBlockInHand()) {
                if(event.getAction().isRightClick()) {
                    event.getInteractionPoint().getBlock().setType(event.getMaterial());
                }
            }
        }
    }

    private boolean isPlayerOutsideBorder(Player player) {
        Location playerLoc = player.getLocation();
        WorldBorder wb = player.getWorld().getWorldBorder();

        if(wb.isInside(playerLoc)) {
            return false;
        }

        return true;
    }
}
