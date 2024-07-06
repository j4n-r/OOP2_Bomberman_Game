package mvc.model;

import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class UnbreakableCell extends Cell {

    public UnbreakableCell(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
        Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.setBorder(blackBorder);
        // laod image (maybe also move this part into the constructor)
        this.setBackground(Color.BLACK);
    }
    public void setImage() {
        try {
            img = ImageIO.read(getClass().getResource("/resources/playerSprites/wall.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
