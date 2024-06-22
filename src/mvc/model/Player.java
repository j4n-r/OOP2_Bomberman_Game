package mvc.model;

import java.awt.Color;

public class Player extends Cell {
    private int xPos;
    private int yPos;
    private int playerNumber;
    private String direction;
    private int health;
    private boolean hasAmmo;

    public Player(int xPos, int yPos, int playerNumber) {
        super(xPos, yPos);
        this.xPos = xPos;
        this.direction = "UP";
        this.yPos = yPos;
        this.playerNumber = playerNumber;
        this.health = 1;
        this.hasAmmo = true;

        this.setBackground(Color.BLUE);
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isHasAmmo() {
        return hasAmmo;
    }

    public void setHasAmmo(boolean hasAmmo) {
        this.hasAmmo = hasAmmo;
    }

}
