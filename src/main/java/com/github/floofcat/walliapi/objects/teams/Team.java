package com.github.floofcat.walliapi.objects.teams;

import com.github.floofcat.walliapi.WalliAPI;
import com.github.floofcat.walliapi.objects.WalliPlayer;

import java.util.HashSet;
import java.util.Set;

public class Team {

    private String shortName;
    private String formattedName;
    private boolean isPlaying;
    private String teamColor;
    private String teamSymbol;
    private int finalScores;

    public Team(String shortName, String formattedName, String teamColor,
                String teamSymbol, boolean isPlaying, int finalScores) {
        this.shortName = shortName;
        this.formattedName = formattedName;
        this.isPlaying = isPlaying;
        this.teamColor = teamColor;
        this.teamSymbol = teamSymbol;
        this.finalScores = finalScores;
    }

    public Set<WalliPlayer> getOnlinePlayers() {
        Set<WalliPlayer> onlinePlayers = new HashSet<>();
        for(WalliPlayer walliPlayer : WalliAPI.getInstance().getTeamController().getPlayers()) {
            if(!walliPlayer.isValid()) {
                continue;
            }

            if(walliPlayer.getTeam().equals(this)) {
                onlinePlayers.add(walliPlayer);
            }
        }

        return onlinePlayers;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public int getFinalScores() {
        return this.finalScores;
    }

    public void addScore(int a) {
        this.finalScores += a;
    }

    public String getFormattedName() {
        return this.formattedName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getTeamColor() {
        return this.teamColor;
    }

    public String getTeamSymbol() {
        return this.teamSymbol;
    }
}
