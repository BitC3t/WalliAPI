package com.github.floofcat.walliapi.events;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.objects.teams.Team;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatEvents implements Listener {

    private final WalliAPI plugin;

    public ChatEvents(WalliAPI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void chatEvents(AsyncChatEvent event) {
        Player player = event.getPlayer();

        WalliPlayer wp = WalliAPI.getInstance().getWalliPlayer(player);
        Team team = wp.getTeam();

        if(this.plugin.chatMuted) {
            event.setCancelled(true);
            return;
        }


        event.setCancelled(true);
        if(player.isOp()) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        team.getTeamColor() + "🛡 [ADMIN]" + player.getName() + "&f: " + PlainTextComponentSerializer
                                .plainText().serialize(event.message())));
            }
            return;
        }

        if(this.plugin.isSplitChat()) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                WalliPlayer wp1 = WalliAPI.getInstance().getWalliPlayer(p);

                if(p.isOp()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[TEAM - " +
                            team.getTeamColor() + team.getShortName() + "&7] " + team.getTeamColor() + "🛡 " + player.getName() + "&f: " + PlainTextComponentSerializer
                            .plainText().serialize(event.message())));
                    continue;
                }

                if(wp1.getTeam().equals(team)) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[TEAM - " +
                            team.getTeamColor() + team.getShortName() + "&7] " + team.getTeamColor() + "🛡 " + player.getName() + "&f: " + PlainTextComponentSerializer
                                    .plainText().serialize(event.message())));
                }
            }
        } else {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        team.getTeamColor() + "🛡 " + player.getName() + "&f: " + PlainTextComponentSerializer
                                .plainText().serialize(event.message())));
            }        }

    }
}
