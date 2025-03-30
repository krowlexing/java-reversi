package fun.krowlexing.reversi.server.entities;

import java.sql.Timestamp;

public class Stats {
    private int id;
    private int playerId;
    private int fieldWidth;
    private int fieldHeight;
    private int timeUsed;
    private int maxTime;
    private int pairsChecked;
    private Timestamp playedAt;

    public Stats(int id, int playerId, int width, int height, int timeUsed, int maxTime, int pairsChecked, Timestamp playedAt) {
        this.id = id;
        this.playerId = playerId;
        this.fieldWidth = width;
        this.fieldHeight = height;
        this.timeUsed = timeUsed;
        this.maxTime = maxTime;
        this.pairsChecked = pairsChecked;
        this.playedAt = playedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }


    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getPairsChecked() {
        return pairsChecked;
    }

    public void setPairsChecked(int pairsChecked) {
        this.pairsChecked = pairsChecked;
    }

    public Timestamp getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Timestamp playedAt) {
        this.playedAt = playedAt;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth = fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight = fieldHeight;
    }
}
