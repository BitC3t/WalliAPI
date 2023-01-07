package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ComExecutor implements CommandExecutor {

    private final WalliAPI plugin;
    private List<String> commands = Arrays.asList("set-team");

    public ComExecutor(WalliAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("walliapi")) {
            if(args.length < 1) {
                sender.sendMessage(ChatColor.GOLD + "[" + "\u270e" + "] You need to have an argument for this." );
            }

            String cmd = args[0];
            Player player = (Player) sender;

            this.execute(cmd, args, player);

        }
        return true;
    }

    private void execute(String command, String[] args, Player player) {
        switch(command) {
            case "set-team":
                TeamCommand teamCommand = new TeamCommand(this.plugin);
                teamCommand.execute(player, args);
                break;

        }
    }
}
