package tictactoe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
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

import javax.swing.SwingConstants;

public class ListGames {

    public static String gameCheck = "0";
    public static int XO = 0;

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
                    ListGames window = new ListGames();
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
    public ListGames() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0, 2, 50, 50));
        panel.setLayout(new GridLayout(0, 1, 0, 0));

        //Initilize TTT Service
        TicTacToe game = new TicTacToe();
        TTTWebService myLink = game.getProxy();

        //Create Game Starts Here
        JButton createButton = new JButton("Create Game");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    gameCheck = myLink.newGame(LoginWindow.getUser());
                    XO = 1;
                } catch (Exception ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }

                frame.setVisible(false);
                Game game1 = new Game();
                game1.getFrame().setVisible(true);
            }
        });
        frame.getContentPane().add(createButton);

        //Join Game Starts Here
        JButton joinButton = new JButton("No Games Available");
        if (!myLink.showOpenGames().equals("ERROR-NOGAMES")) {
            joinButton.setText("Join Game");
            joinButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().setLayout(new GridLayout(0, 1, 50, 50));
                    panel.setLayout(new GridLayout(0, 1, 0, 0));
                    frame.getContentPane().remove(leaderBoardButton);
                    frame.getContentPane().remove(userStatsButton);
                    int counter = 0;
                    //Join Game
                    frame.getContentPane().remove(createButton);
                    frame.getContentPane().remove(joinButton);

                    List<String> userList = new ArrayList<String>();
                    try {
                        userList = Arrays.asList(myLink.showOpenGames().split(",|\\\n"));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    while (counter < userList.size()) {
                        JButton list = new JButton("<html>Game Created by: " + userList.get(counter + 1) + "<br>Game ID: " + userList.get(counter) + " <br>Creation Time: " + userList.get(counter + 2) + " </html>");
                        //gameCheck=Integer.valueOf(userList.get(counter-1));
                        panel.add(list);
                        counter += 3;
                        list.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                frame.setVisible(false);
                                String[] text = list.getText().split(" ");
                                gameCheck = text[5];
                                try {
                                    myLink.joinGame(LoginWindow.getUser(), Integer.valueOf(gameCheck));
                                    XO = 2;
                                } catch (Exception e1) {
                                    // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                                Game game = new Game();
                                game.getFrame().setVisible(true);
                            }
                        });
                    }

                    JScrollPane scrollPane = new JScrollPane(panel);
                    frame.add(scrollPane);
                    frame.getContentPane().revalidate();
                    frame.getContentPane().repaint();
                }
            });
        }
        frame.getContentPane().add(joinButton);

        // user stats button
        userStatsButton = new JButton("User Stats");
        userStatsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                UserStats userStats = new UserStats();
                userStats.setVisible(true);
            }
        });
        frame.getContentPane().add(userStatsButton);

        // LeaderBoard button
        leaderBoardButton = new JButton("LeaderBoard");
        leaderBoardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                LeaderBoard leaderBoard = new LeaderBoard();
                leaderBoard.setVisible(true);
            }
        });
        frame.getContentPane().add(leaderBoardButton);
    }

    public static String getGame() {
        return gameCheck;
    }

    public JFrame getFrame() {
        return frame;
    }

    public static int getXO() {
        return XO;
    }

}
