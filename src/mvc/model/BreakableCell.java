package mvc.model;

import java.awt.Color;

public class BreakableCell extends Cell {

    public BreakableCell(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
        this.setBackground(Color.GRAY);
        this.setBorder(null);
    }
}
