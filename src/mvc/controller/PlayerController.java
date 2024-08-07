
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
        if (gameController.getGameStatus() == "STOPPED") {
            return;
        }
        int keyCode = e.getKeyCode();
        // Handle key pressed event
        switch (keyCode) {
            case KeyEvent.VK_A:
                // Move player1 left
                if (player1.getDirection().equals("LEFT")) {
                    movePlayer(player1, 0, -1);
                } else {
                    player1.setDirection("LEFT");
                }
                break;
            case KeyEvent.VK_D:
                // Move player1 right
                if (player1.getDirection().equals("RIGHT")) {
                    movePlayer(player1, 0, 1);
                } else {
                    player1.setDirection("RIGHT");
                }
                break;
            case KeyEvent.VK_W:
                // Move player1 up
                if (player1.getDirection().equals("UP")) {
                    movePlayer(player1, -1, 0);
                } else {
                    player1.setDirection("UP");
                }
                break;
            case KeyEvent.VK_S:
                // Move player1 down
                if (player1.getDirection().equals("DOWN")) {
                    movePlayer(player1, 1, 0);
                } else {
                    player1.setDirection("DOWN");
                }
                break;
            case KeyEvent.VK_LEFT:
                // Move player2 left
                if (player2.getDirection().equals("LEFT")) {
                    movePlayer(player2, 0, -1);
                } else {
                    player2.setDirection("LEFT");
                }
                break;
            case KeyEvent.VK_RIGHT:
                // Move player2 right
                if (player2.getDirection().equals("RIGHT")) {
                    movePlayer(player2, 0, 1);
                } else {
                    player2.setDirection("RIGHT");
                }
                break;
            case KeyEvent.VK_UP:
                // Move player2 up
                if (player2.getDirection().equals("UP")) {
                    movePlayer(player2, -1, 0);
                } else {
                    player2.setDirection("UP");
                }
                break;
            case KeyEvent.VK_DOWN:
                // Move player2 down
                if (player2.getDirection().equals("DOWN")) {
                    movePlayer(player2, 1, 0);
                } else {
                    player2.setDirection("DOWN");
                }
                break;
            case KeyEvent.VK_SPACE:
               // Player 2: Drop bomb
                setBomb(player2, player2.getxPos(), player2.getyPos());
                break;
            case KeyEvent.VK_Q:
                // Player 1: Drop bomb
                setBomb(player1, player1.getxPos(), player1.getyPos());
                break;
            case KeyEvent.VK_ESCAPE:
                // Pause the game
                gameController.pauseGame();
                break;
        }

    }

    private void setBomb(Player player, int xPos, int yPos) {
        if (!player.hasAmmo()) {
            return;
        }
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
        // Bombs can only be placed on normal Cells
        // this structure is needed because everything is instantiated as a cell so we can"t check for it
        if (gameField[bombXPos][bombYPos] instanceof UnbreakableCell) {
            return;
        }
        if (gameField[bombXPos][bombYPos] instanceof Player) {
            return;
        }
        if (gameField[bombYPos][bombXPos] instanceof BreakableCell) {
            return;
        } else {
            player.setHasAmmo(false);
            // Place the bomb at the calculated position
            gameField[bombXPos][bombYPos] = new Bomb(bombXPos, bombYPos, 3000, this.gameField, player);

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
            // set old position to a new cell (grass)
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
