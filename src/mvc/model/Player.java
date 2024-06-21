package mvc.model;

import java.awt.Color;

public class Player extends Cell {
    private int xPos;
    private int yPos;
    private int playerNumber;

    public Player(int xPos, int yPos, int playerNumber) {
        super(xPos, yPos);
        this.xPos = xPos;
        this.yPos = yPos;
        this.playerNumber = playerNumber;

        this.setBackground(Color.BLUE);
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

}
