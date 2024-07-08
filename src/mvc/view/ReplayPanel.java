package mvc.view;

import mvc.controller.GameController;
import mvc.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ReplayPanel extends JPanel {

    private int cellSize;
    private int mapHeight;
    private int mapWidth;
    private Cell[][] replayField;

    public ReplayPanel(int mapWidth, int mapHeight, int cellSize, GameController gameController) {
        super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.cellSize = cellSize;

        this.setLayout(null);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    gameController.pauseGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void setGameField(Cell[][] replayField) {
        this.replayField = replayField;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("painting");
        if (replayField != null && replayField[0][0] != null) {
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    Cell cell = replayField[i][j];
                    if (cell instanceof UnbreakableCell) {
                        g.drawImage(cell.img, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    } else if (cell instanceof BreakableCell) {
                        g.drawImage(cell.img, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    } else if (cell instanceof Bomb) {
                            g.drawImage(cell.bomb1, j * cellSize, i * cellSize, cellSize, cellSize, null);

                    } else if (cell instanceof Player) {
                        Player player = (Player) cell;
                                g.drawImage(player.player_downImg, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    } else {
                        g.drawImage(cell.img, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    }

                }
            }
        }
    }
}