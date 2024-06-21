package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import mvc.view.GameFrame;

public class Bomb extends Cell {

    private boolean ticking;
    private int yCoordinate;
    private int timer;
    private int xCoordinate;
    private Cell[][] gameField;

    public Bomb(final int xCoordinate, int yCoordinate, int timer, Cell[][] gameField) {
        super(xCoordinate, yCoordinate);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.timer = timer;
        this.gameField = gameField;
        this.startTimer();
    }

    private void startTimer() {
        Timer bombTimer = new Timer();
        bombTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                explode();
            }

        }, timer);
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
        }

        // Remove the bomb from the game field
        gameField[xPos][yPos] = new Cell(xPos, yPos);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (ticking) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
