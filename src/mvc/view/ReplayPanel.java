package mvc.view;

import mvc.model.*;

import javax.swing.*;
import java.awt.*;

public class ReplayPanel extends JPanel {

    private int cellSize;
    private int mapHeight;
    private int mapWidth;
    private Cell[][] replayField;

    public ReplayPanel(int mapWidth, int mapHeight, int cellSize) {
        super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.cellSize = cellSize;

        this.setLayout(null);
    }

    public void setGameField(Cell[][] replayField) {
        this.replayField = replayField;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("painting");
        if (replayField != null) {
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    Cell cell = replayField[i][j];
                    if (cell instanceof UnbreakableCell) {
                        g.drawImage(cell.img, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    } else if (cell instanceof BreakableCell) {
                        g.drawImage(cell.img, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    } else if (cell instanceof Bomb) {
                        Bomb bomb = (Bomb) cell;
                        if (bomb.isTicking()) {
                            g.drawImage(cell.bomb1, j * cellSize, i * cellSize, cellSize, cellSize, null);

                        } else {
                            g.drawImage(cell.bomb2, j * cellSize, i * cellSize, cellSize, cellSize, null);

                        }
                    } else if (cell instanceof Player) {
                        Player player = (Player) cell;
                                g.drawImage(player.player_downImg, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    } else {
                        g.drawImage(cell.img, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    }
//                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
//                    g.setColor(Color.BLACK);
//                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}