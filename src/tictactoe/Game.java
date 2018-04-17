/*
Game

17/04/2018
Ricky Kearney - 14141647
Piotr Kurzynoga - 14143097

This class shows the game window and manages the tiles in the board.
The tiles in the board are generated to make the tic tac toe game.
The player monitor class is implemeneted to manage who can make a play.
If a user clicks a tile on their go the web service is called to ensure the tile can be clicked.
Once the tile has not been clicked the tile is updated to this players XO.
When the game is finished the user is told who won and is stopped from playing.

 */
package tictactoe;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static tictactoe.GameMenu.gameCheck;

public class Game {

    private JFrame frame;
    private JButton exitBn; // exit button
    private JButton player; // shows the player information
    private JButton menuBn; // menu button
    private PlayerMonitor monitor; // player monitor watchdog
    List<JButton> buttonList; // board tiles
    private final int gid; // game ID
    private final int uid; // user ID

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Game window = new Game();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // sets game and user ID before making the window
    public Game() {
        gid = Integer.valueOf(GameMenu.getGame()); // Game being played
        uid = LoginWindow.getUser(); // currently logged in user
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300); // size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 3, 20, 20)); // set the grid layout
        exitBn = new JButton("Exit");
        player = new JButton("");
        menuBn = new JButton("Menu");

        // Start player monitor to see who's turn it is.
        monitor = new PlayerMonitor(Integer.valueOf(gameCheck), uid, this);
        monitor.start();

        buttonList = new ArrayList<JButton>(); // generate board tiles
        int[] x = new int[3]; // board is 3x3
        int[] y = new int[3];

        for (int i = 0; i < 3; i++) { // run through in X 
            for (int j = 0; j < 3; j++) { // run through in Y
                JButton list = new JButton();
                x[i] = i;
                y[j] = j;
                buttonList.add(list); // add the buttons
                frame.getContentPane().add(list);
                list.setText(String.valueOf(i) + " " + String.valueOf(j)); // set the text in the tiles

                player.setOpaque(true);

                list.addActionListener(new ActionListener() { // add an action listener to each tile
                    public void actionPerformed(ActionEvent e) { // on tile click
                        if (monitor.canPlay() && !monitor.gameOver()) { // if the player monitor says we can play and game is not over
                            try {
                                String[] text = list.getText().split(" ");
                                String squareCheck = "";
                                String xCheck = list.getText();
                                TicTacToe game = new TicTacToe();
                                TTTWebService myLink = game.getProxy();
                                if (!(xCheck.equals("X")) && !(xCheck.equals("O"))) { // once the tile has not been clicked
                                    squareCheck = myLink.checkSquare(Integer.valueOf(text[0]), Integer.valueOf(text[1]), gid); // check the square with the web service
                                }
                                //Check if the square is not taken and if both players are connected to the game.
                                if (squareCheck.equals("0") && Integer.valueOf(myLink.getGameState(gid)) == 0) {
                                    myLink.takeSquare(Integer.valueOf(text[0]), Integer.valueOf(text[1]), gid, uid); // tell the web service we are taking that square
                                    if (GameMenu.getXO() == 1) { // check if we are X or O
                                        list.setText("X"); // set the correct text to the tile
                                    } else if (GameMenu.getXO() == 2) {
                                        list.setText("O");
                                    }
                                    list.repaint(); // update the list
                                    String win = myLink.checkWin(gid); // check if we won
                                    switch (win) {
                                        case "1": // player 1 won
                                            System.out.println("Player 1 wins");
                                            myLink.setGameState(gid, 1); // update the game state on web service
                                            break;
                                        case "2": // player 2 won
                                            System.out.println("Player 2 wins");
                                            myLink.setGameState(gid, 2); // update the game state on web service
                                            break;
                                        case "3": // draw
                                            System.out.println("Players drew");
                                            myLink.setGameState(gid, 3); // update the game state on web service
                                            break;
                                        default:
                                            break;
                                    }
                                } else { // Square is taken
                                    System.out.println("Square Taken");
                                }

                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
        }

        // add exit button and action listener
        frame.getContentPane().add(exitBn);
        exitBn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBnActionPerformed(evt);
            }
        });
        frame.getContentPane().add(player); // player info area

        // add menu button and action listener
        frame.getContentPane().add(menuBn);
        menuBn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBnActionPerformed(evt);
            }
        });
    }

    // Terminate the player monitor thread and close the application on exit button press.
    private void exitBnActionPerformed(java.awt.event.ActionEvent evt) {
        monitor.terminate(); // stop the thread
        System.exit(0); // exit
    }

    // Terminate the player monitor thread and show menu on menu button press
    private void menuBnActionPerformed(java.awt.event.ActionEvent evt) {
        monitor.terminate(); // stop the thread
        this.frame.setVisible(false); // close this window
        GameMenu games = new GameMenu(); // open game menu window
        games.getFrame().setVisible(true);
    }

    // update the board based on the other player's play
    public void updateBoard(int x, int y) {
        String XO = "";
        if (GameMenu.getXO() == 1) { // check if we are X or O
            XO = ("O");
        } else if (GameMenu.getXO() == 2) {
            XO = ("X");
        }

        switch (x) { // first find the X location of the tile
            case 0:
                buttonList.get(y).setText(XO); // update the correct tile
                break;
            case 1:
                buttonList.get(x + y + 2).setText(XO); // update the correct tile
                break;
            case 2:
                buttonList.get(x + y + 4).setText(XO); // update the correct tile
                break;
            default:
                break;
        }
    }

    // update the player info area with new data
    public void setPlayer(String text) {
        switch (text) {
            case "Your go":
                player.setText(text); // set text
                player.setBackground(Color.GREEN); // set color
                break;
            case "P1 Wins":
                player.setText(text);
                player.setBackground(Color.BLUE);
                break;
            case "P2 Wins":
                player.setText(text);
                player.setBackground(Color.YELLOW);
                break;
            case "Draw":
                player.setText(text);
                player.setBackground(Color.PINK);
                break;
            default:
                player.setBackground(Color.RED);
                player.setText(text);
                break;
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}
