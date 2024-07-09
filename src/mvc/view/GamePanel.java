
package mvc.view;

import java.awt.Graphics;
import javax.swing.JPanel;
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
        if (gameField != null ) {
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    Cell cell = gameField[i][j];
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
                        switch (player.getDirection()) {
                            case "UP":
                                g.drawImage(player.player_upImg, j * cellSize, i * cellSize, cellSize, cellSize, null);
                                break; 
                            case "DOWN":
                                g.drawImage(player.player_downImg, j * cellSize, i * cellSize, cellSize, cellSize, null);
                                break;
                            case "LEFT":
                                g.drawImage(player.player_leftImg, j * cellSize, i * cellSize, cellSize, cellSize, null);
                                break;
                            case "RIGHT":
                                g.drawImage(player.player_rightImg, j * cellSize, i * cellSize, cellSize, cellSize, null);
                                break;
                        }
                    } else {
                        g.drawImage(cell.img, j * cellSize, i * cellSize, cellSize, cellSize, null);
                    }
                }
            }
        }
    }
}
