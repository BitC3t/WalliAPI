package com.github.floofcat.walliapi.commands;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComExecutor implements CommandExecutor, TabCompleter {

    private final WalliAPI plugin;
    private List<String> commands = Arrays.asList("set-team","set-spectator", "info");

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
            case "set-spectator":
                SpectatorCommand spectatorCommand = new SpectatorCommand(this.plugin);
                spectatorCommand.execute(args, player);
                break;
            case "info":
                InformationCommand informationCommand = new InformationCommand(this.plugin);
                informationCommand.execute(args, player);
                break;

        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();

        if(args.length == 1) {
            completions.clear();
            List<String> tempList = new ArrayList<>();

            for(String c : commands) {
                tempList.add(c);
            }

            StringUtil.copyPartialMatches(args[0], tempList, completions);
            Collections.sort(completions);

            return completions;
        } else if(args.length == 3) {
            completions.clear();
            List<String> tempList = new ArrayList<>();

            for(Team team : this.plugin.getTeamController().getTeams()) {
                tempList.add(team.getShortName());
            }

            StringUtil.copyPartialMatches(args[2], tempList, completions);
            Collections.sort(completions);
        } else if(args.length == 2) {
            completions.clear();
            List<String> tempList = new ArrayList<>();

            for(Player player : Bukkit.getOnlinePlayers()) {
                tempList.add(player.getName());
            }

            StringUtil.copyPartialMatches(args[1], tempList, completions);
            Collections.sort(completions);
        }
        return completions;
    }
}
