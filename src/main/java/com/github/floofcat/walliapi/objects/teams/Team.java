package com.github.floofcat.walliapi.objects.teams;

public class Team {

    private String shortName;
    private String formattedName;
    private boolean isPlaying;
    private int finalScores;

    public Team(String shortName, String formattedName, boolean isPlaying, int finalScores) {
        this.shortName = shortName;
        this.formattedName = formattedName;
        this.isPlaying = isPlaying;
        this.finalScores = finalScores;
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
}
