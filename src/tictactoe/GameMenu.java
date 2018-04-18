/*
Game Menu

17/04/2018
Ricky Kearney - 14141647
Piotr Kurzynoga - 14143097

This class creates the game menu for a logged in user.
It allows the user to create a new game, join an existing game, 
view their user stats or view the leaderboard.

 */
package tictactoe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GameMenu {

    public static String gameCheck = "0"; // game ID
    public static int XO = 0; // X = 1, O = 2 

    private JFrame frame;
    private JButton leaderBoardButton;
    private JButton userStatsButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameMenu window = new GameMenu(); // create the window
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public GameMenu() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame(); // make a JFrame to hold the buttons
        JPanel panel = new JPanel();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 2, 50, 50)); // set grid size and padding
        panel.setLayout(new GridLayout(0, 1, 0, 0));

        //Initilize TTT Service
        TicTacToe game = new TicTacToe(); // get the webservice
        TTTWebService myLink = game.getProxy();

        //Create Game Button and assign Action listener
        JButton createButton = new JButton("Create Game"); // make the button
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    gameCheck = myLink.newGame(LoginWindow.getUser()); // try to make a new game with the logged in user
                    XO = 1;
                } catch (Exception ex) { // catch any error
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }

                frame.setVisible(false); // close menu
                Game game1 = new Game(); // open the game screen
                game1.getFrame().setVisible(true);
            }
        });
        frame.getContentPane().add(createButton); // add the button to the frame

        //Join Game button and action listener
        JButton joinButton = new JButton("No Games Available (Refresh)"); // set default state
        if (!myLink.showOpenGames().equals("ERROR-NOGAMES")) { // Games available
            joinButton.setText("Join Game"); // if there are games, set the new text
        }
        // if the button is pressed
        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!myLink.showOpenGames().equals("ERROR-NOGAMES")) { // if games are available
                    frame.getContentPane().setLayout(new GridLayout(0, 1, 50, 50));
                    panel.setLayout(new GridLayout(0, 1, 0, 0)); // set the new frame layout
                    frame.getContentPane().remove(leaderBoardButton); // remove unnessessery buttons
                    frame.getContentPane().remove(userStatsButton);
                    int counter = 0;
                    frame.getContentPane().remove(createButton);
                    frame.getContentPane().remove(joinButton);

                    List<String> userList = new ArrayList<String>(); // create an array of the open games
                    try {
                        userList = Arrays.asList(myLink.showOpenGames().split(",|\\\n")); // get the open games from web service
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    while (counter < userList.size()) { // while there are open games add them to the list
                        JButton list = new JButton("<html>Game Created by: " + userList.get(counter + 1) + "<br>Game ID: " + userList.get(counter) + " <br>Creation Time: " + userList.get(counter + 2) + " </html>");
                        //gameCheck=Integer.valueOf(userList.get(counter-1));
                        panel.add(list);
                        counter += 3;
                        list.addActionListener(new ActionListener() { // action listener for each open game to allow user to join
                            public void actionPerformed(ActionEvent e) {
                                String[] text = list.getText().split(" ");
                                gameCheck = text[5]; // set game ID
                                try {
                                    myLink.joinGame(LoginWindow.getUser(), Integer.valueOf(gameCheck)); // try to join the game
                                    XO = 2; // set this user to O
                                } catch (Exception e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                frame.setVisible(false); // close this window
                                Game game = new Game(); // open the game window
                                game.getFrame().setVisible(true);
                            }
                        });
                    }

                    JScrollPane scrollPane = new JScrollPane(panel); // scrollable pannel for infinite open games
                    frame.add(scrollPane);
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();
                } else { // refresh list
                   frame.setVisible(false); // close this window
                   GameMenu games = new GameMenu(); // show menu
                   games.getFrame().setVisible(true);
                }
            }
        });

        frame.getContentPane().add(joinButton); // add the join button

        // user stats button and action listener
        userStatsButton = new JButton("User Stats"); // set the text
        userStatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false); // close this window 
                UserStats userStats = new UserStats(); // open the user stats window
                userStats.setVisible(true);
            }
        });
        frame.getContentPane().add(userStatsButton); // add the button

        // LeaderBoard button and action listener
        leaderBoardButton = new JButton("LeaderBoard");
        leaderBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false); // close this window
                LeaderBoard leaderBoard = new LeaderBoard(); // open the leaderboard window
                leaderBoard.setVisible(true);
            }
        });
        frame.getContentPane().add(leaderBoardButton); // add the button
    }
    // gets the game ID being played

    public static String getGame() {
        return gameCheck;
    }

    // gets the Frame object
    public JFrame getFrame() {
        return frame;
    }

    // gets weither user is X or O
    public static int getXO() {
        return XO;
    }

}
