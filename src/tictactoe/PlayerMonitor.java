/*
Player Monitor

17/04/2018
Ricky Kearney - 14141647
Piotr Kurzynoga - 14143097

This class creates a thread which monitors the game in progress.
It tells which user to Go or wait and sets the winner once the game is finished
The thread checks with the webserive every 200ms who the last person to select a tile.
From this the thread is able to work out if its player 1 or player 2's go.
If the game is over the correct text is shown to the user and plays stopped.

 */
package tictactoe;

/**
 *
 * @author Ricky
 */
public class PlayerMonitor implements Runnable {

    private final int timeout = 200; // no point constantly pinging the web service (milliSec)
    private Thread t; // thread object
    private final int gid; // game ID
    private final int pid; // player ID
    private boolean play; // can the user play or not
    private boolean gameOver = false; // game over var
    private final Game gameInstance; // game being played object
    private boolean running = true; // used to terminate the thread

    PlayerMonitor(int gameID, int playerID, Game runningGame) {
        gid = gameID; // set the game ID
        pid = playerID; // set player ID
        gameInstance = runningGame; // current game being played
        System.out.println("Player Monitor Started (Gid = " + gid + " pid= " + pid + " )");
    }

    @Override
    public void run() {
        System.out.println("Player Monitor Running ");
        TicTacToe game = new TicTacToe();
        TTTWebService myLink = game.getProxy();
        int lastPlayer; // last person to make a play
        int win = 0;

        while (running) {
            try {
                String result = myLink.getBoard(gid); // get the moves for the game ID
                if (!result.equals("ERROR-NOMOVES")) { // once there are moves continue
                    int counter = 0;
                    String[] board = result.split(",|\\\n"); // split the resulted data
                    lastPlayer = Integer.parseInt(board[board.length - 3]); // get the user ID of the last player
                    win = Integer.parseInt(myLink.checkWin(gid)); // check if the game is finished
                    while (counter < board.length) { // while there are moves
                        if (Integer.valueOf(board[counter]) != pid) {
                            int x = Integer.valueOf(board[counter + 1]); // get coords of plays
                            int y = Integer.valueOf(board[counter + 2]);

                            gameInstance.updateBoard(x, y); // update the board with plays
                        }
                        counter += 3;
                    }
                } else { // if there are no moves on the board, set the initial conditions
                    if (GameMenu.getXO() == 2) { // P2 - O goes first!
                        lastPlayer = 0;
                    } else {
                        lastPlayer = pid;
                    }
                }
                if (win > 0) { // if the game is over
                    gameOver = true; // set the variable
                    switch (win) {
                        case 1: // Player 1 won
                            System.out.println("Game Over: P1 Wins");
                            gameInstance.setPlayer("Game Over: P1 Wins");
                            break;
                        case 2: // Player 2 won
                            System.out.println("Game Over: P2 Wins");
                            gameInstance.setPlayer("Game Over: P2 Wins");
                            break;
                        case 3: // Draw
                            System.out.println("Game Over: Draw");
                            gameInstance.setPlayer("Game Over: Draw");
                            break;
                        default:
                            break;
                    }
                } else if (pid != lastPlayer) { // if the last player was not this player
                    System.out.println("This player's go ");
                    play = true; // this player can play
                    gameInstance.setPlayer("Your go"); // tell the user
                } else { // otherwise stop the user from playing
                    System.out.println("Other players go ");
                    play = false; // stop this player from playing
                    gameInstance.setPlayer("Other Players go"); // tell the player
                }
                Thread.sleep(timeout); // sleep to reduce load on server
            } catch (InterruptedException e) { // catch any exception
                System.out.println("Thread interrupted.");
            }
        }
    }

    // start the thread
    public void start() {
        System.out.println("Starting ");
        if (t == null) { // make a thread if there is none
            t = new Thread(this); // make thread
            t.start(); // start thread
        }
    }

    // returns if the user can play or not
    public boolean canPlay() {
        return play;
    }

    // returns if the game is over
    public boolean gameOver() {
        return gameOver;
    }

    // used to termiate the thread
    public void terminate() {
        running = false;
    }
}
