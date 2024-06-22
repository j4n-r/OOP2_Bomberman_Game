
package mvc.view;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private int mapWidth;
    private int mapHeight;
    private int cellSize;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GameFrame(int mapWidth, int mapHeight, int cellSize) {
        super();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.cellSize = cellSize;

        this.setTitle("Bomberman");
        this.setSize(mapWidth * cellSize, mapHeight * cellSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        this.add(mainPanel);
    }

    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}
