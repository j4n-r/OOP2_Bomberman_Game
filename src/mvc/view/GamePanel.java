package mvc.view;

import java.awt.GridLayout;

import javax.swing.JPanel;

import mvc.controller.PlayerController;

public class GamePanel extends JPanel {

    private int cellSize;
    private int mapHeight;
    private int mapWidth;
    private PlayerController playerController;

    public GamePanel(int mapWidth, int mapHeight, int cellSize) {
        super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.cellSize = cellSize;

        this.setLayout(new GridLayout(mapHeight, mapWidth));

    }
}
