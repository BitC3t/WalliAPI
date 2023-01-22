package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;
import com.github.floofcat.walliapi.utils.GlowUtils;
import com.nametagedit.plugin.NametagEdit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ToggleGlowExecutor implements CommandExecutor {
    private List<Player> toggleGlowedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("toggle-glow")) {
            if(!(sender instanceof Player)) {
                return true;
            }

            Player player = (Player) sender;
            this.execute(player);

        }
        return true;
    }

    private void execute(Player player) {
        WalliPlayer wp = WalliAPI.getInstance().getWalliPlayer(player);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7[&6" + '\u270E' + "&7] &fThe glow effect has been &btoggled."));

        if(this.toggleGlowedPlayers.contains(player)) {
            for(WalliPlayer w : WalliAPI.getInstance().getTeamController().getPlayers()) {
                if(!w.isValid()) {
                    continue;
                }

                if(w.isSpectator()) {
                    continue;
                }

                GlowUtils.setGlow(w);
            }

            this.toggleGlowedPlayers.remove(player);
            return;
        }

        this.toggleGlowedPlayers.add(player);
        for(WalliPlayer w : WalliAPI.getInstance().getTeamController().getPlayers()) {
            if(!w.isValid()) {
                continue;
            }

            if(w.isSpectator()) {
                continue;
            }

            GlowUtils.stopGlowtoPlayer(w, wp.getPlayer());

            NametagEdit.getApi().clearNametag(w.getPlayer());
            NametagEdit.getApi().reloadNametag(w.getPlayer());
            NametagEdit.getApi().setNametag(w.getPlayer(), w.getTeam().getTeamColor(), "");
        }
    }
}
