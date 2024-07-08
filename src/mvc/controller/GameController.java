package mvc.controller;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.SwingUtilities;

import mvc.dao.GameLogDaoImpl;
import mvc.model.*;
import mvc.view.*;

public class GameController implements Runnable {

    private final GameLogDaoImpl gameLogDaoImpl;
    private final GameController gameController;
    private GamePanel gamePanel;
    private final GameFrame gameFrame;
    private final int mapHeight = 15;
    private final int mapWidth = 15;
    private final int cellSize = 48;
    private Cell[][] gameField;
    private Thread gameThread;
    private final int FPS = 60; // Assuming a default FPS value
    private Player player1;
    private Player player2;
    public String gameStatus;
    private ReplayPanel replayPanel;
    private Cell[][] replayField;
    GameLog replayGameLog;
    GameLog gameLog;
    private int replayFieldCounter = 0; // Counter to iterate through the oldGameLog

    public GameController() {
        // need because inner classes cannot access this
        gameController = this;
        gameStatus = "STOPPED";
        gameFrame = new GameFrame(mapWidth, mapHeight, cellSize);

        MenuPanel menuPanel = new MenuPanel(this);
        gameFrame.addPanel(menuPanel, "Menu");

        replayPanel = new ReplayPanel(mapWidth, mapHeight, cellSize, this);
        replayField = new Cell[mapHeight][mapWidth];

        gameFrame.addPanel(replayPanel, "Replay");
        gameLogDaoImpl = new GameLogDaoImpl();

        gameFrame.showPanel("Menu");
        gameFrame.setVisible(true);
        replayGameLog = new GameLog();
        gameLog = new GameLog();
        startGameThread();


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

//        printGameField();
        // generates the  gameField based on the layout
        initializeGameField();

        gamePanel.setGameField(gameField);
        gameFrame.addPanel(gamePanel, "Game");
        // gameFrame.addPanel(menuPanel, "Menu");
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow(); // Ensure the game panel has focus to receive key events

        //debugging for focus
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
        System.out.println(gameLog.toString());
    }

    public void replayGame() {
        replayFieldCounter = 0;
        gameFrame.showPanel("Replay");
        replayPanel.requestFocusInWindow();
        replayGameLog = gameLogDaoImpl.getGameLog();
        replayField = new Cell[mapHeight][mapWidth];
        replayPanel.setGameField(replayField);
        // sleep so the gameField can load
        gameStatus = "REPLAY";
    }

    public void endGame(String winner, Boolean replay) {
        VictoryPanel victoryPanel = new VictoryPanel(this, winner);
        gameFrame.addPanel(victoryPanel, "Victory");
        gameFrame.showPanel("Victory");

        // flag to use the same method in a replay
        if (!replay) {
            gameLogDaoImpl.saveGameLog(gameLog);
        }
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
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', 'P', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', 'V', '#'},
                {'#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#'},
                {'#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#'},
                {'#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#'},
                {'#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#'},
                {'#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#'},
                {'#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#'},
                {'#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#'},
                {'#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#'},
                {'#', 'X', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'X', '#'},
                {'#', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', '#'},
                {'#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#', 'V', '#'},
                {'#', 'V', 'V', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'X', 'V', 'V', 'P', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        };

        int playerNumber = 1;
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
        PlayerController playerController = new PlayerController(player1, player2, this);
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
                // generates replayField based on the replayGameLog
                generateReplayFieldBasedOnLog();
                // repaints the replayPanel based on the replayField
                replayPanel.repaint();
                // sleep so the replay is not approximately as fast as the game was
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
                    endGame("Red", false);

                }
                if (player2.getHealth() == 0) {
                    gameStatus = "STOPPED";
                    endGame("Blue",false);
                    System.out.println("Player 1 Won the game");
                }

                // Update game state
                if (delta >= 1) {
                    update();
                    lastTime = now;
                }

                // Add current gameField to gameLog every 0.5 seconds
                if (logDelta >= 1) {
                    gameLog.add(currentGameFieldString());
                    lastLogTime = now;
                }

                // Render game state
                render();

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

    private void generateReplayFieldBasedOnLog() {
        System.out.println(replayGameLog);
        String oneGameFieldString = replayGameLog.get(replayFieldCounter);
//        System.out.println("oldGameLogList:" + replayGameLog);
//        System.out.println("oneGameFieldString:" + oneGameFieldString);
        int charIndex = 0;
        char cellType;
        if (replayFieldCounter == replayGameLog.size() -1 ) {
            gameStatus = "STOPPED";
            // waits a bit  to maybe see who won
            try {
                gameThread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (oneGameFieldString.indexOf("1") != -1) {
                endGame("Red", true);
            } else {
                endGame("Blue", true);
            }
            return;
        }
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                // get the character at the current position of the current array of the replayGameLog
                cellType = oneGameFieldString.charAt(charIndex);
//                try {
//
//                } catch (StringIndexOutOfBoundsException e) {
//                    System.out.println("StringIndexOutOfBoundsException");
//                    break;
//                }
                // update the position of the character that will be parsed out of the current gameFieldString
                charIndex++;
//                System.out.println(cellType);
                switch (cellType) {
                    case '#': // unbreakableCell
                        replayField[i][j] = new UnbreakableCell(i, j);
                        break;
                    case 'B': // breakableCell
                        replayField[i][j] = new BreakableCell(i, j);
                        break;
                    case 'F': // Bomb
                        // Assuming default values for Bomb parameters
                        replayField[i][j] = new Bomb(i, j);
                    case 'O': // Bomb
                        // Assuming default values for Bomb parameters
                        replayField[i][j] = new Bomb(i, j);
                        break;
                    case '1': // Player 1
                        if (player1 == null ){
                            player1 =new Player(i, j, 1);
                        }
                        replayField[i][j] = player1;
                        break;
                    case '2': // Player 2\
                        if (player2 == null ){
                           player2 = new Player(i, j, 2);
                        }
                        replayField[i][j] = player2;
                        break;
                    default: // normal Cell
                        replayField[i][j] = new Cell(i, j);

                        break;
                }

//                System.out.println(charIndex);
//                System.out.println(oneGameFieldString + " gamefieldlenght:  " + oneGameFieldString.length());
//                System.out.println("iteration " + "i: " + i + "j: " + j);
//                System.out.println("Char at j: " + oneGameFieldString.charAt(j));
            }

        }
//        for (int i = 0; i < replayField.length; i++) {
//            for (int j = 0; j < replayField[0].length; j++) {
//                if (replayField[i][j] instanceof UnbreakableCell) {
//                    System.out.print("# ");
//                } else if (replayField[i][j] instanceof BreakableCell) {
//                    System.out.print("B ");
//                } else if (replayField[i][j] instanceof Bomb) {
//                    System.out.print("O ");
//                } else if (replayField[i][j] instanceof Player) {
//                    Player player = (Player) replayField[i][j];
//                    if (player == player1) {
//                        System.out.print("1 ");
//                    } else {
//                        System.out.print("2 ");
//                    }
//                } else {
//                    System.out.print("_ ");
//                }
//            }
//            System.out.println();
//        }
        System.out.println();
        replayFieldCounter++;
        System.out.println(replayFieldCounter);
    }

    private void update() {
        gamePanel.repaint();
    }

    private void render() {
    }

    private String currentGameFieldString() {
        // using stringbuilder becaus
        // e += creates new string objects everytime
        StringBuilder log = new StringBuilder();
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                if (gameField[i][j] instanceof UnbreakableCell) {
                    log.append('#');
                } else if (gameField[i][j] instanceof BreakableCell) {
                    log.append('B');
                } else if (gameField[i][j] instanceof Bomb ) {
                    if (((Bomb) gameField[i][j]).getPlayer() == player1) {
                        log.append('F');
                    }
                    if (((Bomb) gameField[i][j]).getPlayer() == player2) {
                        log.append('O');
                    }
                } else if (gameField[i][j] instanceof Player player) {
                    if (player == player1) {
                        log.append('1');
                    } else {
                        log.append('2');
                    }
                } else {
                    log.append('_');
                }
            }

        }
        System.out.println("added to gameLog " + log);
        return log.toString();

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



    public Cell[][] getGameField() {
        return gameField;
    }





    public String getGameStatus() {
        return gameStatus;
    }

}
