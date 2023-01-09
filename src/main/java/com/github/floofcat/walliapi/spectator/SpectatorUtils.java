package com.github.floofcat.walliapi.spectator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpectatorUtils {

    public static void setSpectator(Player player) {
        player.setInvisible(true);
        player.setHealth(20.0f);
        player.setFlying(true);
        player.setAllowFlight(true);
        player.setInvulnerable(true);

        // GUI for Player Teleport
        ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(10000);
        itemStack.setItemMeta(itemMeta);

        player.getInventory().addItem(itemStack);
    }

    public static void unsetSpectator(Player player) {
        player.setInvisible(false);
        player.setHealth(20.0f);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setInvulnerable(false);
    }
}
