package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Cell extends JButton {
    int xCoordinate;
    int yCoordinate;
    public BufferedImage  img, bomb1, bomb2;


    public Cell(int xCoordinate, int yCoordinate) {
        super();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.setImage();
        this.setBorder(null);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void setImage() {
        try {
            img = ImageIO.read(getClass().getResource("/resources/playerSprites/grass01.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
