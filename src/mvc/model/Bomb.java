package mvc.model;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Cell {

    private boolean ticking;
    private int yCoordinate;
    private int timer;
    private int xCoordinate;
    private Cell[][] gameField;
    private Player player;

    public Bomb(final int xCoordinate, int yCoordinate, int timer, Cell[][] gameField, Player player) {
        super(xCoordinate, yCoordinate);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.timer = timer;
        this.gameField = gameField;
        this.player = player;
        this.startTimer();
    }

    private void startTimer() {
        Timer bombTimer = new Timer();
        TimerTask toggleTickingTask = new TimerTask() {
            @Override
            public void run() {
                ticking = !ticking;
            }
        };
        TimerTask explodeTask = new TimerTask() {
            @Override
            public void run() {
                bombTimer.cancel();
                explode();
            }
        };

        bombTimer.scheduleAtFixedRate(toggleTickingTask, 0, 500); // Toggle ticking every 0.5 seconds
        bombTimer.schedule(explodeTask, timer); // Schedule explosion after the timer duration
    }

    public boolean isTicking() {
        return ticking;
    }

    public void setTicking(boolean ticking) {
        this.ticking = ticking;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate2) {
        this.yCoordinate = yCoordinate2;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate2) {
        this.xCoordinate = xCoordinate2;
    }

    protected void explode() {

        int xPos = xCoordinate;
        int yPos = yCoordinate;

        // Array of directions to check (left, right, up, down)
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        for (int[] direction : directions) {
            int newX = xPos + direction[0];
            int newY = yPos + direction[1];

            if (newX >= 0 && newX < gameField.length && newY >= 0 && newY < gameField[0].length) {
                Cell cell = gameField[newX][newY];

                if (cell instanceof BreakableCell) {
                    gameField[newX][newY] = new Cell(newX, newY);
                } else if (cell instanceof Player) {
                    Player player = (Player) cell;
                    player.setHealth(player.getHealth() - 1);
                    ;
                }
            }
            player.setHasAmmo(true);
        }

        // Remove the bomb from the game field
        gameField[xPos][yPos] = new Cell(xPos, yPos);

    }
    public void setImage() {
        try {
            bomb1 = ImageIO.read(getClass().getResource("/resources/playerSprites/bomb_1.png"));
            bomb2 = ImageIO.read(getClass().getResource("/resources/playerSprites/bomb_2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Cell[][] getGameField() {
        return gameField;
    }

    public void setGameField(Cell[][] gameField) {
        this.gameField = gameField;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
