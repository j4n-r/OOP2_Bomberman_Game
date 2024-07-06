package mvc.model;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.IOException;

public class BreakableCell extends Cell {

    public BreakableCell(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
        this.setBackground(Color.GRAY);
        this.setBorder(null);
    }
    public void setImage() {
        try {
            img = ImageIO.read(getClass().getResource("/resources/playerSprites/floor01.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
