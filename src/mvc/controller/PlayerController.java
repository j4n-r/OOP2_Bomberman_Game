
package mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import mvc.model.Player;

public class PlayerController implements KeyListener {
    Player player1;
    Player player2;
    private GameController gameController;

    public PlayerController(Player player1, Player player2, GameController gameController) {
        this.player1 = player1;
        this.player2 = player2;
        // Initialize player1Controls
        this.gameController = gameController;
    }

    public PlayerController() {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Handle key pressed event
        // Handle player 1 controls
        // TODO change the cell array to the new player position
        switch (keyCode) {
            case KeyEvent.VK_A:
                System.out.println("Player 1: Move left");
                break;
            case KeyEvent.VK_D:
                System.out.println("Player 1: Move right");
                break;
            case KeyEvent.VK_W:
                System.out.println("Player 1: Move up");
                break;
            case KeyEvent.VK_S:
                System.out.println("Player 1: Move down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Player 2: Move left");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Player 2: Move right");
                break;
            case KeyEvent.VK_UP:
                System.out.println("Player 2: Move up");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("Player 2: Move down");
                break;
        }
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
