
package mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mvc.model.Bomb;
import mvc.model.BreakableCell;
import mvc.model.Cell;
import mvc.model.Player;
import mvc.model.UnbreakableCell;

public class PlayerController implements KeyListener {
    private Player player1;
    private Player player2;
    private Cell[][] gameField;
    private GameController gameController;

    public PlayerController(Player player1, Player player2, GameController gameController) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameController = gameController;
        gameField = gameController.getGameField();
    }

    public PlayerController() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Handle key pressed event
        switch (keyCode) {
            case KeyEvent.VK_A:
                System.out.println("Player 1: Move left");
                if (player1.getDirection().equals("LEFT")) {
                    movePlayer(player1, 0, -1);
                } else {
                    player1.setDirection("LEFT");
                }
                break;
            case KeyEvent.VK_D:
                System.out.println("Player 1: Move right");
                if (player1.getDirection().equals("RIGHT")) {
                    movePlayer(player1, 0, 1);
                } else {
                    player1.setDirection("RIGHT");
                }
                break;
            case KeyEvent.VK_W:
                System.out.println("Player 1: Move up");
                if (player1.getDirection().equals("UP")) {
                    movePlayer(player1, -1, 0);
                } else {
                    player1.setDirection("UP");
                }
                break;
            case KeyEvent.VK_S:
                System.out.println("Player 1: Move down");
                if (player1.getDirection().equals("DOWN")) {
                    movePlayer(player1, 1, 0);
                } else {
                    player1.setDirection("DOWN");
                }
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Player 2: Move left");
                if (player2.getDirection().equals("LEFT")) {
                    movePlayer(player2, 0, -1);
                } else {
                    player2.setDirection("LEFT");
                }
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Player 2: Move right");
                if (player2.getDirection().equals("RIGHT")) {
                    movePlayer(player2, 0, 1);
                } else {
                    player2.setDirection("RIGHT");
                }
                break;
            case KeyEvent.VK_UP:
                System.out.println("Player 2: Move up");
                if (player2.getDirection().equals("UP")) {
                    movePlayer(player2, -1, 0);
                } else {
                    player2.setDirection("UP");
                }
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Player 2: Move down");
                if (player2.getDirection().equals("DOWN")) {
                    movePlayer(player2, 1, 0);
                } else {
                    player2.setDirection("DOWN");
                }
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Player 2: Drop bomb");
                setBomb(player2, player2.getxPos(), player2.getyPos());
                break;
            case KeyEvent.VK_F:
                System.out.println("Player 1: Drop bomb");
                setBomb(player1, player1.getxPos(), player1.getyPos());
                break;
        }
        printGameField();
    }

    private void printGameField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if (gameField[i][j] instanceof UnbreakableCell) {
                    System.out.print("U ");
                } else if (gameField[i][j] instanceof BreakableCell) {
                    System.out.print("B ");
                } else if (gameField[i][j] instanceof Bomb) {
                    System.out.print("O ");
                } else if (gameField[i][j] instanceof Player) {
                    Player player = (Player) gameField[i][j];
                    if (player == player1) {
                        System.out.print("1 ");
                    } else {
                        System.out.print("2 ");
                    }
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void setBomb(Player player, int xPos, int yPos) {
        String playerDirection = player.getDirection();

        int bombXPos = xPos;
        int bombYPos = yPos;

        // Determine the bomb's position based on the player's direction
        switch (playerDirection) {
            case "LEFT":
                bombYPos -= 1;
                break;
            case "RIGHT":
                bombYPos += 1;
                break;
            case "UP":
                bombXPos -= 1;
                break;
            case "DOWN":
                bombXPos += 1;
                break;
        }
        if (gameField[bombXPos][bombYPos] instanceof UnbreakableCell) {
            return;
        }
        if (gameField[bombXPos][bombYPos] instanceof Player) {
            return;
        }
        if (gameField[bombYPos][bombXPos] instanceof BreakableCell) {
            return;
        } else {
            // Place the bomb at the calculated position
            gameField[bombXPos][bombYPos] = new Bomb(bombXPos, bombYPos, 3000, this.gameField);
        }

    }

    private void movePlayer(Player player, int xPosChange, int yPosChange) {
        int newXPos = player.getxPos() + xPosChange;
        int newYPos = player.getyPos() + yPosChange;
        if (newXPos < 0 || newXPos >= gameField.length || newYPos < 0 || newYPos >= gameField[0].length) {
            return;
        }
        if (gameField[newXPos][newYPos] instanceof UnbreakableCell) {
            return;
        }
        if (gameField[newXPos][newYPos] instanceof Player) {
            return;
        }
        if (gameField[newXPos][newYPos] instanceof Bomb) {
            return;
        }
        if (gameField[newXPos][newYPos] instanceof BreakableCell) {
            return;
        } else {
            // Update player's position on the game field
            gameField[player.getxPos()][player.getyPos()] = new Cell(player.getxPos(), player.getyPos());
            player.setxPos(newXPos);
            player.setyPos(newYPos);
            gameField[newXPos][newYPos] = player;
        }
        gameController.getGamePanel().repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key released event if necessary
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Key typed events are not useful for this type of key handling
    }
}
