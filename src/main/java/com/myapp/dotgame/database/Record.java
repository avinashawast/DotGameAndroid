package com.myapp.dotgame.database;

public class Record {
    public Record() {}
    public Record(String name, int score) {
        this.playerName = name;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private String playerName;
    private int score;
}
