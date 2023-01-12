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

    @EventHandler
    public void chatEvents(AsyncChatEvent event) {
        Player player = event.getPlayer();

        WalliPlayer wp = WalliAPI.getInstance().getWalliPlayer(player);
        Team team = wp.getTeam();

        event.setCancelled(true);
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    team.getTeamColor() + "🛡 " + player.getName() + "&f: " + PlainTextComponentSerializer
                            .plainText().serialize(event.message())));
        }
    }
}
