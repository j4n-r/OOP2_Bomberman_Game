package mvc.view;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private int mapWidth;
    private int mapHeight;
    private int cellSize;

    public GameFrame(int mapWidth, int mapHeight, int cellSize) {
        super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.cellSize = cellSize;

        this.setTitle("Bomberman");
        this.setSize(mapWidth * cellSize, mapWidth * cellSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

    }
}
