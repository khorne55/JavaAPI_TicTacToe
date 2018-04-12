package tictactoe;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import static tictactoe.ListGames.gameCheck;

public class Game {

    private JFrame frame;
    private JButton exitBn;
    private JButton player;
    private JButton menuBn;
    private PlayerMonitor monitor;
    List<JButton> buttonList;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
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

    /**
     * Create the application.
     */
    public Game() {

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 3, 20, 20));
        exitBn = new JButton("Exit");
        player = new JButton("");
        menuBn = new JButton("Menu");

        // Start player monitor to see who's turn it is.
        monitor = new PlayerMonitor(Integer.valueOf(gameCheck), LoginWindow.getUser(), this);
        monitor.start();

        buttonList = new ArrayList<JButton>();
        int[] x = new int[3];
        int[] y = new int[3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton list = new JButton();
                x[i] = i;
                y[j] = j;
                buttonList.add(list);
                frame.getContentPane().add(list);
                list.setText(String.valueOf(i) + " " + String.valueOf(j));

                player.setOpaque(true);

                list.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (monitor.canPlay() && !monitor.gameOver()) {
                            try {
                                String[] text = list.getText().split(" ");
                                String squareCheck = "";
                                String xCheck = list.getText();
                                TicTacToe game = new TicTacToe();
                                TTTWebService myLink = game.getProxy();
                                if (!(xCheck.equals("X")) && !(xCheck.equals("O"))) {
                                    squareCheck = myLink.checkSquare(Integer.valueOf(text[0]), Integer.valueOf(text[1]), Integer.valueOf(ListGames.getGame()));
                                }
                                //Check if the square is not taken and if both players are connected to the game.
                                if (squareCheck.equals("0") && Integer.valueOf(myLink.getGameState(Integer.valueOf(ListGames.getGame()))) == 0) {
                                    myLink.takeSquare(Integer.valueOf(text[0]), Integer.valueOf(text[1]), Integer.valueOf(ListGames.getGame()), LoginWindow.getUser());
                                    if (ListGames.getXO() == 1) {
                                        list.setText("X");
                                    } else if (ListGames.getXO() == 2) {
                                        list.setText("O");
                                    }
                                    list.repaint();
                                    System.out.println("Win:" + myLink.checkWin(Integer.valueOf(ListGames.getGame())));
                                    String win = myLink.checkWin(Integer.valueOf(ListGames.getGame()));
                                    if (win.equals("1")) {
                                        System.out.println("Player 1 wins");
                                    } else if (win.equals("2")) {
                                        System.out.println("Player 2 wins");
                                    } else if (win.equals("3")) {
                                        System.out.println("Players drew");
                                    }
                                } else {
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

        frame.getContentPane().add(exitBn);
        exitBn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBnActionPerformed(evt);
            }
        });
        frame.getContentPane().add(player);
        frame.getContentPane().add(menuBn);
        menuBn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBnActionPerformed(evt);
            }
        });
    }

    private void exitBnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        monitor.terminate();
        System.exit(0);
    }

    private void menuBnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        monitor.terminate();
        this.frame.setVisible(false);
        ListGames games = new ListGames();
        games.getFrame().setVisible(true);
    }

    public void updateBoard(int x,int y) {
        String XO="";
        if (ListGames.getXO() == 1) {
            XO=("O");
        } else if (ListGames.getXO() == 2) {
            XO=("X");
        }
        
        if(x==0) {
            buttonList.get(y).setText(XO);
        } else if(x==1) {
            buttonList.get(x+y+2).setText(XO);
        } else if(x==2) {
            buttonList.get(x+y+4).setText(XO);
        }
    }
    
    public void setPlayer(String text) {
        switch (text) {
            case "Your go":
                player.setText(text);
                player.setBackground(Color.GREEN);
                break;
            case "Game Over: P1 Wins":
                player.setText(text);
                player.setBackground(Color.BLUE);
                break;
            case "Game Over: P2 Wins":
                player.setText(text);
                player.setBackground(Color.YELLOW);
                break;
            case "Game Over: Draw":
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
