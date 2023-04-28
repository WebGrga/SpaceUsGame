package main;

import java.io.Serializable;

public class Send implements Serializable {
    String playerColor;
    int worldXplayer;
    int worldYplayer;
    int taskPlayer;

    String direction;

    public Send(String color, int worldx, int worldy) {
        this.playerColor = color;
        this.worldXplayer = worldx;
        this.worldYplayer = worldy;

    }

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public int getWorldXplayer() {
        return worldXplayer;
    }

    public void setWorldXplayer(int worldXplayer) {
        this.worldXplayer = worldXplayer;
    }

    public int getWorldYplayer() {
        return worldYplayer;
    }

    public void setWorldYplayer(int worldYplayer) {
        this.worldYplayer = worldYplayer;
    }

    public int getTaskPlayer() {
        return taskPlayer;
    }

    public void setTaskPlayer(int taskPlayer) {
        this.taskPlayer = taskPlayer;
    }

    @Override
    public String toString() {
        return "Send [playerColor=" + playerColor + ", worldXplayer=" + worldXplayer + ", worldYplayer=" + worldYplayer
                + "]";
    }
}
