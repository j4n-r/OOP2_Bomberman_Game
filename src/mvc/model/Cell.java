package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell extends JButton {
    int xCoordinate;
    int yCoordinate;
    private Image sprite;

    public Cell(int xCoordinate, int yCoordinate) {
        super();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.setBackground(Color.GREEN);
        this.setBorder(null);
    }

    public void loadSprite(String path) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        this.sprite = icon.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sprite != null) {
            g.drawImage(sprite, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
