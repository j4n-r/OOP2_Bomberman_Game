
package mvc.view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import mvc.controller.GameController;
import mvc.model.Bomb;
import mvc.model.BreakableCell;
import mvc.model.Cell;
import mvc.model.Player;
import mvc.model.UnbreakableCell;

public class GamePanel extends JPanel {

    private int cellSize;
    private int mapHeight;
    private int mapWidth;
    private Cell[][] gameField;

    public GamePanel(int mapWidth, int mapHeight, int cellSize) {
        super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.cellSize = cellSize;

        this.setLayout(null);
    }

    public void setGameField(Cell[][] gameField) {
        this.gameField = gameField;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameField != null) {
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    Cell cell = gameField[i][j];
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
