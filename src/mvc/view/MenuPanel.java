
package mvc.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import mvc.controller.GameController;

public class MenuPanel extends JPanel {

    private GameController gameController;

    public MenuPanel(GameController gameController) {
        this.gameController = gameController;
        setLayout(null);

        JButton startButton = new JButton("Start new Game");
        startButton.setBounds(350, 250, 200, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.startNewGame();
            }
        });
        add(startButton);

        JButton resumeButton = new JButton("Resume Game");
        resumeButton.setBounds(350, 310, 200, 50);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resumeGame();
            }
        });
        add(resumeButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(350, 370, 200, 50);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);
    }
}
