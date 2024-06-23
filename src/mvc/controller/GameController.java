package mvc.controller;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import mvc.model.Bomb;
import mvc.model.BreakableCell;
import mvc.model.Cell;
import mvc.model.Player;
import mvc.model.UnbreakableCell;
import mvc.view.*;

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
    public String gameStatus;
    private MenuPanel menuPanel;
    private VictoryPanel victoryPanel;
    private int playerNumber;
    private DatabaseController databaseController;
    private ReplayPanel replayPanel;
    private Cell[][] replayField;
    List<String> oldGameLog;
    private int replayFieldCounter = 0; // Counter to iterate through the oldGameLog

    public GameController() {
        gameStatus = "STOPPED";
        gameFrame = new GameFrame(mapWidth, mapHeight, cellSize);
        menuPanel = new MenuPanel(this);
        gameFrame.addPanel(menuPanel, "Menu");
        replayPanel  = new ReplayPanel(mapWidth, mapHeight, cellSize);
        replayField = new Cell[mapHeight][mapWidth];
        gameFrame.addPanel(replayPanel, "Replay");
        databaseController = new DatabaseController();
        startGameThread();
        gameFrame.showPanel("Menu");
        gameFrame.setVisible(true);
    }

    public void printGameField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if (gameField[i][j] instanceof UnbreakableCell) {
                    System.out.print("# ");
                } else if (gameField[i][j] instanceof BreakableCell) {
                    System.out.print("B ");
                } else if (gameField[i][j] instanceof Bomb) {
                    System.out.print("O ");
                } else if (gameField[i][j] instanceof Player) {
                    Player player = (Player) gameField[i][j];
                    if (player == player1) {
                        System.out.print("1 ");
                    } else {
                        System.out.print("2 ");
                    }
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void startNewGame() {
        // create new instance of the gamePanel, clearing the panel did not work could
        // be because it get referenced somewhere later
        gamePanel = new GamePanel(mapWidth, mapHeight, cellSize);
        gameField = new Cell[mapHeight][mapWidth];
        // menuPanel = new MenuPanel(this);
        // // Clear the game field completely
        // for (int i = 0; i < mapHeight; i++) {
        // for (int j = 0; j < mapWidth; j++) {
        // gameField[i][j] = null;
        // }
        // } // Reset players to null
        player1 = null;
        player2 = null;
        printGameField();
        initializeGameField();

        gamePanel.setGameField(gameField);
        gameFrame.addPanel(gamePanel, "Game");
        // gameFrame.addPanel(menuPanel, "Menu");
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow(); // Ensure the game panel has focus to receive key events
        //
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
        // Reinitialize the game field

        gameFrame.showPanel("Game");
        gamePanel.requestFocusInWindow(); // Ensure the game panel has focus to receive key events
        gameStatus = "RUNNING";
    }

    public void resumeGame() {
        gameFrame.showPanel("Game");
        gameStatus = "RUNNING";
        gamePanel.requestFocusInWindow(); // Ensure the game panel has focus to receive key events
    }

    public void pauseGame() {
        gameFrame.showPanel("Menu");
        gameStatus = "PAUSED";
        databaseController.printGameLog();
        System.out.println(databaseController.getGameLog().toString());

        databaseController.loadGameLogFromDatabase();
        System.out.println(databaseController.getOldGameLog().toString());

        //testing
        oldGameLog = databaseController.getOldGameLog();
        System.out.println(oldGameLog);
        replayField = new Cell[mapHeight][mapWidth];
        System.out.println(replayField);
        replayFieldCounter =0;

    }
    public void replayGame() {
        gameFrame.showPanel("Replay");
        oldGameLog = databaseController.getOldGameLog();
        replayField = new Cell[mapHeight][mapWidth];


        gameStatus = "REPLAY";


    }

    public void endGame(String winner) {
        victoryPanel = new VictoryPanel(this, winner);
        gameFrame.addPanel(victoryPanel, "Victory");
        gameFrame.showPanel("Victory");
        databaseController.storeGameLogInDatabase();
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

        playerNumber = 1;
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
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
            }
        }
        playerController = new PlayerController(player1, player2, this);
        gamePanel.addKeyListener(playerController);
        playerNumber = 1;

    }

    @Override
    public void run() {
        // Main game loop
        long lastTime = System.nanoTime();
        double nsPerUpdate = 1000000000.0 / FPS;
        long lastLogTime = System.nanoTime();
        double nsPerLog = 1000000000.0 * 0.5; // 0.5 seconds
        while (gameThread != null) {
            long now = System.nanoTime();
            double delta = (now - lastTime) / nsPerUpdate;
            double logDelta = (now - lastLogTime) / nsPerLog;

            if (gameStatus.equals("REPLAY")) {
                String log = oldGameLog.get(replayFieldCounter);
                System.out.println(oldGameLog);
                System.out.println(log );
                for (int i = replayFieldCounter; i < mapHeight; i++) {
                    for (int j = 0; j < mapWidth; j++) {
                        char cellType = log.charAt(j);
                        System.out.println(cellType);
                        switch (cellType) {
                            case '#': // unbreakableCell
                                replayField[i][j] = new UnbreakableCell(i, j);
                                break;
                            case 'B': // breakableCell
                                replayField[i][j] = new BreakableCell(i, j);

                                break;
                            case 'O': // Bomb
                                // Assuming default values for Bomb parameters
                                replayField[i][j] = new Bomb(i, j, 0, replayField, null);

                                break;
                            case '1': // Player 1
                                replayField[i][j] = new Player(i, j, 1);
                                break;
                            case '2': // Player 2
                                replayField[i][j] = new Player(i, j, 2);
                                break;
                            default: // normal Cell
                                replayField[i][j] = new Cell(i, j);
                                break;
                        }
                    }

                }
                for (int i = 0; i < replayField.length; i++) {
                    for (int j = 0; j < replayField [0].length; j++) {
                        if (replayField[i][j] instanceof UnbreakableCell) {
                            System.out.print("# ");
                        } else if (replayField[i][j] instanceof BreakableCell) {
                            System.out.print("B ");
                        } else if (replayField[i][j] instanceof Bomb) {
                            System.out.print("O ");
                        } else if (replayField[i][j] instanceof Player) {
                            Player player = (Player) replayField[i][j];
                            if (player == player1) {
                                System.out.print("1 ");
                            } else {
                                System.out.print("2 ");
                            }
                        } else {
                            System.out.print("_ ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                replayFieldCounter++;
                System.out.println(replayFieldCounter);
                System.out.println(Arrays.deepToString(replayField));

                    replayPanel.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (gameStatus.equals("RUNNING")) {

                if (player1.getHealth() == 0) {
                    gameStatus = "STOPPED";
                    System.out.println("Player 2 Won the game");
                    endGame("Player 2");

                }
                if (player2.getHealth() == 0) {
                    gameStatus = "STOPPED";
                    endGame("Player 1");
                    System.out.println("Player 1 Won the game");
                }

                // Update game state
                if (delta >= 1) {
                    update();
                    lastTime = now;
                }

                // Add current gameField to gameLog every 0.5 seconds
                if (logDelta >= 1) {
                    addCurrentGameFieldToLog();
                    lastLogTime = now;
                }

                // Render game state
                render();

            }

            if (gameStatus.equals("STOPPED")) {

            }
            if (gameStatus.equals("PAUSED")) {
            }
            long startTime = System.nanoTime();

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
        gamePanel.repaint();
    }

    private void render() {
    }

    private void addCurrentGameFieldToLog() {
        // using stringbuilder because += creates new string objects everytime
        StringBuilder gameLog = new StringBuilder();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if (gameField[i][j] instanceof UnbreakableCell) {
                    gameLog.append('#');
                } else if (gameField[i][j] instanceof BreakableCell) {
                    gameLog.append('B');
                } else if (gameField[i][j] instanceof Bomb) {
                    gameLog.append('O');
                } else if (gameField[i][j] instanceof Player player) {
                    if (player == player1) {
                        gameLog.append('1');
                    } else {
                        gameLog.append('2');
                    }
                } else {
                    gameLog.append('_');
                }
            }

        }
        databaseController.getGameLog().add(gameLog.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameController();
            }
        });
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getCellSize() {
        return cellSize;
    }

    public Cell[][] getGameField() {
        return gameField;
    }

    public void setGameField(Cell[][] gameField) {
        this.gameField = gameField;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public void setGameThread(Thread gameThread) {
        this.gameThread = gameThread;
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int fPS) {
        FPS = fPS;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
