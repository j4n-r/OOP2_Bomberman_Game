package mvc.model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Cell {
    private int xPos;
    private int yPos;
    private int playerNumber;
    private String direction;
    private int health;
    private boolean hasAmmo;
    public BufferedImage player_upImg, player_downImg, player_leftImg, player_rightImg;


    public Player(int xPos, int yPos, int playerNumber) {
        super(xPos, yPos);
        this.xPos = xPos;
        this.direction = "UP";
        this.yPos = yPos;
        this.playerNumber = playerNumber;
        this.health = 1;
        this.hasAmmo = true;
        System.out.println("new Player created" + playerNumber + "xPos: " + xPos + "yPos: " + yPos);
        this.getPlayerImage();
        this.setBackground(Color.BLUE);
        try {
            if (playerNumber == 1) {
                player_upImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player01_up.png"));
                player_downImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player01_down.png"));
                player_leftImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player01_left.png"));
                player_rightImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player01_right.png"));
            } else {
                player_upImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player02_up.png"));
                player_downImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player02_down.png"));
                player_leftImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player02_left.png"));
                player_rightImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player02_right.png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getPlayerImage() {
        try {
            if (playerNumber == 1) {
                player_upImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player01_up.png"));
            } else {
                player_upImg = ImageIO.read(getClass().getResource("/resources/playerSprites/player02_up.png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
