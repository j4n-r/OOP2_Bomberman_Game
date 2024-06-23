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
                        g.setColor(Color.BLACK);
                    } else if (cell instanceof BreakableCell) {
                        g.setColor(Color.GRAY);
                    } else if (cell instanceof Bomb) {
                        Bomb bomb = (Bomb) cell;
                        if (bomb.isTicking()) {
                            g.setColor(Color.ORANGE);
                        } else {
                            g.setColor(Color.BLACK);
                        }
                    } else if (cell instanceof Player) {
                        Player player = (Player) cell;
                        if (player.getPlayerNumber() == 1) {
                            g.setColor(Color.BLUE);
                        } else if (player.getPlayerNumber() == 2) {
                            g.setColor(Color.RED);
                        }
                    } else {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}