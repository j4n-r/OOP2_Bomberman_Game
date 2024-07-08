

package mvc.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import mvc.controller.GameController;

public class MenuPanel extends JPanel {

    public MenuPanel(GameController gameController) {
        setLayout(null);
        setBackground(new Color(103, 165, 94));

        JButton startButton = new JButton("Start new Game");
        startButton.setBackground(new Color(216, 155, 72));

        startButton.setBounds(260, 250, 200, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.startNewGame();
            }
        });
        add(startButton);

        JButton resumeButton = new JButton("Resume Game");
        resumeButton.setBackground(new Color(216, 155, 72));
        resumeButton.setBounds(260, 310, 200, 50);
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resumeGame();
            }
        });
        add(resumeButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(216, 155, 72));

        exitButton.setBounds(260, 370, 200, 50);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);

        JButton replayButton = new JButton("Replay Last Game");
        replayButton.setBackground(new Color(147, 147, 147));

        replayButton.setBounds(260, 450, 200, 50);
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.replayGame();
            }
        });
        add(replayButton);
    }
}