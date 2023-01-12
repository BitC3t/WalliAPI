package com.github.floofcat.walliapi.spectator;

import com.github.floofcat.walliapi.WalliAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpectatorUtils {

    private static List<Player> spectators = new ArrayList<>();

    public static void setSpectator(Player player) {

        player.setGameMode(GameMode.ADVENTURE);

        player.setFireTicks(0);
        player.setArrowsInBody(0);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setAllowFlight(true);
        player.setFlying(true);
        player.getInventory().clear();
        player.setCollidable(false);
        player.setFoodLevel(20);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(WalliAPI.getInstance(), player);
        }

        spectators.add(player);

        // GUI for Player Teleport
        ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(10000);
        itemStack.setItemMeta(itemMeta);

        player.getInventory().addItem(itemStack);
    }

    public static void unsetSpectator(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setFireTicks(0);
        player.setArrowsInBody(0);
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setCollidable(true);
        player.setInvisible(false);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showPlayer(WalliAPI.getInstance(), player);
        }

        spectators.remove(player);
    }

    public static List<Player> getSpectators() {
        return spectators;
    }
}
