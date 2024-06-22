
package mvc.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mvc.controller.GameController;

public class VictoryPanel extends JPanel {
    private JLabel victoryMessage;
    private JButton backButton;

    public VictoryPanel(GameController gameController, String winner) {
        setLayout(new BorderLayout());

        victoryMessage = new JLabel(winner + " Won the game!", JLabel.CENTER);
        victoryMessage.setFont(new Font("Serif", Font.BOLD, 32));
        add(victoryMessage, BorderLayout.CENTER);

        backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pauseGame();
            }
        });

        backButton = new JButton("Back to Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.pauseGame();
            }
        });
        add(backButton, BorderLayout.SOUTH);
        add(backButton, BorderLayout.SOUTH);
    }
}
