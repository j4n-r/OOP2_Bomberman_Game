package mvc.model;

import java.awt.Color;

import javax.swing.JButton;

public class Cell extends JButton {
    int xCoordinate;
    int yCoordinate;

    public Cell(int xCoordinate, int yCoordinate) {
        super();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.setBackground(Color.GREEN);
        this.setBorder(null);
    }
}
