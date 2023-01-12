package com.github.floofcat.walliapi.events;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.spectator.SpectatorUtils;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class RegistryEvents implements Listener {

    private final WalliAPI plugin;

    public RegistryEvents(WalliAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Hide Spectators to the player.
        for(Player p : SpectatorUtils.getSpectators()) {
            player.hidePlayer(this.plugin, p);
        }

        // Check if this player has a valid WalliPlayer Object.
        if(this.plugin.getTeamController().doesPlayerExist(player)) {
            // Update the WalliPlayer object with the BukkitPlayer
            WalliPlayer walliPlayer = this.plugin.getWalliPlayer(player); // (Should not be null)

            if(walliPlayer == null) {
                this.plugin.getLogger().info("[ERROR] WalliPlayer is null. (RegistryEvents.java)");
                return;
            }

            walliPlayer.apiSetup();
            this.plugin.getLogger().info("[WP] " + player.getName() + " is already registered to a WalliPlayer. Object reconnected!");

            event.joinMessage(PlainTextComponentSerializer.plainText()
                    .deserialize(ChatColor.translateAlternateColorCodes('&',
                            "&7[&a+&7] " + walliPlayer.getTeam().getTeamColor() + "🛡 " + walliPlayer.getPlayer().getName() + " &ahas joined the server.")));
            return;
        }

        // Make a new WalliPlayer Object and update config.
        try {
            this.plugin.getTeamRegistry().registerPlayer(player.getUniqueId(),
                    this.plugin.getTeamRegistry().getSpectatorTeam());
        } catch (IOException e) {
            this.plugin.getLogger().info("[WP] Failed to create a WalliPlayer object.");
        }

        this.plugin.getLogger().info("[WP] " + player.getName() + " has been connected to a new WalliPlayer object.");

        WalliPlayer walliPlayer = this.plugin.getWalliPlayer(player);
        event.joinMessage(PlainTextComponentSerializer.plainText()
                .deserialize(ChatColor.translateAlternateColorCodes('&',
                        "&7[&a+&7] " + walliPlayer.getTeam().getTeamColor() + "🛡 " + walliPlayer.getPlayer().getName() + " &ahas joined the server.")));
    }

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent event) {
        WalliPlayer walliPlayer = this.plugin.getWalliPlayer(event.getPlayer());

        event.quitMessage(PlainTextComponentSerializer.plainText()
                .deserialize(ChatColor.translateAlternateColorCodes('&',
                        "&7[&c-&7] " + walliPlayer.getTeam().getTeamColor() + "🛡 " + walliPlayer.getPlayer().getName() + " &chas left the server.")));

        walliPlayer.apiDeregister();

        this.plugin.getLogger().info("[WP] " + event.getPlayer().getName() + " has disconnected and hence deregistered.");
    }
}
