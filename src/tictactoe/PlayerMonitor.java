/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import static tictactoe.ListGames.gameCheck;

/**
 *
 * @author Ricky
 */
public class PlayerMonitor implements Runnable {

    private int timeout = 200; // no point constantly pinging the web service (milliSec)
    private Thread t;
    private int gid;
    private int pid;
    private boolean play;
    private boolean gameOver =  false;
    private Game gameInstance;
    private boolean running = true;

    PlayerMonitor(int gameID, int playerID, Game runningGame) {
        gid = gameID;
        pid = playerID;
        gameInstance = runningGame;
        System.out.println("Player Monitor Started (Gid = " + gid + " pid= " + pid + " )");
    }

    public void run() {
        System.out.println("Player Monitor Running ");
        TicTacToe game = new TicTacToe();
        TTTWebService myLink = game.getProxy();
        int lastPlayer;
        int win = 0;
        while (running) {
            try {

                String result = myLink.getBoard(gid);
                if (!result.equals("ERROR-NOMOVES")) {
                    String[] lines = result.split("\n");
                    String lastLine = lines[lines.length - 1];
                    String[] player2 = lastLine.split(",");
                    lastPlayer = Integer.parseInt(player2[0]);
                    win = Integer.parseInt(myLink.checkWin(gid));
                } else {
                    if (ListGames.getXO() == 2) { // O goes first!
                        lastPlayer = 0;
                    } else {
                        lastPlayer = pid;
                    }
                }
                if (win > 0) {
                    gameOver = true;
                    switch (win) {
                        case 1:
                            System.out.println("Game Over: P1 Wins");
                            gameInstance.setPlayer("Game Over: P1 Wins");
                            break;
                        case 2:
                            System.out.println("Game Over: P2 Wins");
                            gameInstance.setPlayer("Game Over: P2 Wins");
                            break;
                        case 3:
                            System.out.println("Game Over: Draw");
                            gameInstance.setPlayer("Game Over: Draw");
                            break;
                        default:
                            break;
                    }
                } else if (pid != lastPlayer) {
                    System.out.println("This player's go ");
                    play = true;
                    gameInstance.setPlayer("Your go");
                } else {
                    System.out.println("Other players go ");
                    play = false;
                    gameInstance.setPlayer("Other Players go");
                }
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }

    public void start() {
        System.out.println("Starting ");
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }

    public boolean canPlay() {
        return play;
    }
    public boolean gameOver() {
        return gameOver;
    }

    public void terminate() {
        running = false;
    }
}
