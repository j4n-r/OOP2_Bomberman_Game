
package mvc.controller;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import mvc.model.BreakableCell;
import mvc.model.Cell;
import mvc.model.Player;
import mvc.model.UnbreakableCell;
import mvc.view.GameFrame;
import mvc.view.GamePanel;

public class GameController implements Runnable {
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private final int mapHeight = 15;
    private final int mapWidth = 15;
    private final int cellSize = 48;
    private Cell[][] gameField;
    private Thread gameThread;
    private int FPS = 60; // Assuming a default FPS value
    private PlayerController playerController;
    private Player player1;
    private Player player2;

    public GameController() {
        gameFrame = new GameFrame(mapWidth, mapHeight, cellSize);
        gamePanel = new GamePanel(mapWidth, mapHeight, cellSize);
        gameField = new Cell[mapHeight][mapWidth];

        initializeGameField();
        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);
        // Debugging focus
        playerController = new PlayerController(player1, player2, this);
        gamePanel.addKeyListener(playerController);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow(); // Ensure the game panel has focus to receive key events
        // p
        gamePanel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("GamePanel gained focus");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("GamePanel lost focus");
            }
        });
        startGameThread();
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void initializeGameField() {
        // 2D array to set cell type hardcoded for now because later we could load maps
        // from a csv or txt file
        // feel free to set a better layout, this is just symmetrical
        char[][] layout = {
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
                { '#', 'P', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', 'V', '#' },
                { '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#' },
                { '#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#' },
                { '#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#' },
                { '#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#' },
                { '#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#' },
                { '#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#' },
                { '#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#' },
                { '#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#' },
                { '#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#' },
                { '#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#' },
                { '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#' },
                { '#', 'V', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', 'P', '#' },
                { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
        };

        // loop to populate the cellGrid based on the layout
        int playerNumber = 1;
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                switch (layout[i][j]) {
                    case 'P': // cell where a player starts
                        Player player = new Player(i, j, playerNumber);
                        if (playerNumber == 1) {
                            player1 = player;
                        }
                        if (playerNumber == 2) {
                            player2 = player;
                        }
                        playerNumber++;
                        // set cell size, maybe pass this in the constructor
                        player.setPreferredSize(new Dimension(cellSize, cellSize));
                        gameField[i][j] = player;
                        break;
                    case '#': // unbreakableCell
                        Cell unbreakableCell = new UnbreakableCell(i, j);
                        unbreakableCell.setPreferredSize(new Dimension(cellSize, cellSize));
                        gameField[i][j] = unbreakableCell;
                        break;
                    case 'V': // normal Cell
                        Cell voidCell = new Cell(i, j);
                        voidCell.setPreferredSize(new Dimension(cellSize, cellSize));
                        gameField[i][j] = voidCell;
                        break;
                    case 'X': // breakableCell
                        Cell breakableCell = new BreakableCell(i, j);
                        breakableCell.setPreferredSize(new Dimension(cellSize, cellSize));
                        gameField[i][j] = breakableCell;
                        break;
                }
                gamePanel.add(gameField[i][j]);
            }
        }
    }

    @Override
    public void run() {
        // Main game loop
        while (gameThread != null) {
            long startTime = System.nanoTime();

            // Update game state
            update();

            // Render game state
            render();

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            long sleepTime = (1000 / FPS) - duration;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        // TODO: Update game state logic
    }

    private void render() {
        // TODO: Render game state logic
        gamePanel.repaint();
    }

    public static void main(String[] args) {
        new GameController();
    }
}
