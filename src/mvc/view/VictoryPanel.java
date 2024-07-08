

package mvc.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mvc.controller.GameController;

public class VictoryPanel extends JPanel {

    public VictoryPanel(GameController gameController, String winner) {
        setLayout(new BorderLayout());
        if (winner.equals("Red")) {
            setBackground(new Color(255, 44, 44));
        } else {
            setBackground(new Color(28, 134, 238));
        }


        JLabel victoryMessage = new JLabel(winner + " Won the game!", JLabel.CENTER);
        victoryMessage.setFont(new Font("Serif", Font.BOLD, 32));
        add(victoryMessage, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(new Color(147, 147, 147));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pauseGame();
            }
        });
        add(backButton, BorderLayout.SOUTH);
    }
}